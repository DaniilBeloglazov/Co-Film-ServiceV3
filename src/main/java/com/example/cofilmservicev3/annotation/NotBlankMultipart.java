package com.example.cofilmservicev3.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = NotBlankMultipartValidator.class)
public @interface NotBlankMultipart {
    String message() default "{Image should be not blank or extension not present}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
