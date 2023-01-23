package com.example.cofilmservicev3.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = {NullableNotBlankValidator.class})
@Target( {ElementType.PARAMETER, ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface NullableNotBlank {
    String message() default "Must not be blank.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
