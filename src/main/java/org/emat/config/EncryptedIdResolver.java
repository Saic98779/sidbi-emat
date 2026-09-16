package org.emat.config;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.emat.util.PiiEncryptionService;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.HandlerMapping;

/**
 * Globally resolves {@code @PathVariable Long} parameters, transparently decrypting encrypted IDs
 * ({@code ENC:...}) before passing them to controller methods. Plain numeric values are rejected
 * when PII strict mode is enabled (default).
 *
 * <p>This resolver is registered in {@link WebConfig} for {@code @PathVariable Long} types, so all
 * controllers automatically support encrypted path variables without any per-controller changes.
 */
@Component
@RequiredArgsConstructor
public class EncryptedIdResolver implements HandlerMethodArgumentResolver {

    private final PiiEncryptionService encryptionService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(PathVariable.class)
                && parameter.getParameterType().equals(Long.class);
    }

    @Override
    public Object resolveArgument(
            MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory) {

        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        if (request == null) {
            return null;
        }

        String variableName = parameter.getParameterAnnotation(PathVariable.class).value();
        if (variableName.isEmpty()) {
            variableName = parameter.getParameterName();
        }

        Map<String, String> uriTemplateVars =
                (Map<String, String>)
                        request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        if (uriTemplateVars == null) {
            return null;
        }

        String value = uriTemplateVars.get(variableName);
        if (value == null || value.isEmpty()) {
            return null;
        }

        return encryptionService.decryptId(value);
    }
}
