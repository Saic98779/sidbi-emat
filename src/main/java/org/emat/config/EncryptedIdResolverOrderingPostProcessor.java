package org.emat.config;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.mvc.method.annotation.PathVariableMethodArgumentResolver;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

/**
 * Ensures {@link EncryptedIdResolver} runs before Spring's default
 * {@link PathVariableMethodArgumentResolver} for {@code @PathVariable Long} parameters.
 *
 * <p>Spring appends custom resolvers (WebMvcConfigurer#addArgumentResolvers) AFTER all default
 * resolvers, so the default path-variable resolver would otherwise always win and the
 * encryption-aware resolver would never be invoked. This post-processor relocates the
 * {@link EncryptedIdResolver} to the front of the resolver list on the MVC handler adapter.
 */
@Component
public class EncryptedIdResolverOrderingPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName)
            throws BeansException {
        if (bean instanceof RequestMappingHandlerAdapter adapter) {
            List<HandlerMethodArgumentResolver> current =
                    new ArrayList<>(adapter.getArgumentResolvers());
            if (current.isEmpty()) {
                return bean;
            }
            int mineIndex = -1;
            for (int i = 0; i < current.size(); i++) {
                if (current.get(i) instanceof EncryptedIdResolver) {
                    mineIndex = i;
                    break;
                }
            }
            if (mineIndex < 0) {
                return bean;
            }
            HandlerMethodArgumentResolver mine = current.remove(mineIndex);
            int insertIndex = 0;
            for (int i = 0; i < current.size(); i++) {
                if (current.get(i) instanceof PathVariableMethodArgumentResolver) {
                    insertIndex = i;
                    break;
                }
            }
            current.add(insertIndex, mine);
            adapter.setArgumentResolvers(current);
        }
        return bean;
    }
}