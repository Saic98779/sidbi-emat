package org.emat.config;

import org.emat.validation.SafeInputStringDeserializer;
import org.springframework.boot.jackson.autoconfigure.JsonMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tools.jackson.databind.module.SimpleModule;

/**
 * Registers {@link SafeInputStringDeserializer} as the default {@link String} deserializer for the
 * application JSON mapper.
 *
 * <p>Because the deserializer is registered for {@code String.class} on the mapper used by Spring
 * MVC message converters, it applies to every {@code String} field of every request DTO across the
 * application, including nested DTOs and {@code String} values inside collections. Fields that
 * declare their own {@code @JsonDeserialize(using = ...)} are handled by their own deserializer.
 *
 * <p>Invalid input ({@code <}, {@code >}, {@code /} or {@code \}) is rejected with HTTP 400, never
 * sanitized or modified.
 */
@Configuration
public class InputValidationConfig {

    @Bean
    public JsonMapperBuilderCustomizer safeInputJsonMapperBuilderCustomizer() {
        return builder -> builder.addModule(inputValidationModule());
    }

    private static SimpleModule inputValidationModule() {
        SimpleModule module = new SimpleModule("SafeInputValidation");
        module.addDeserializer(String.class, SafeInputStringDeserializer.INSTANCE);
        return module;
    }
}