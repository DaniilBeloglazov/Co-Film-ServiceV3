package com.example.cofilmservicev3.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {MultipartSizeValidator.class, ArrayMultipartSizeValidator.class})
public @interface MultipartSize {

    String message() default "{Invalid size of MultipartFile.}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    long min();

    long max();
}
