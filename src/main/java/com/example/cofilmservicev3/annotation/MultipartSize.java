package com.example.cofilmservicev3.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = {MultipartSizeValidator.class, MultipartSizeArrayValidator.class})
@Target( {ElementType.PARAMETER, ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface MultipartSize {

    String message() default "{Invalid size of MultipartFile.}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    long min();

    long max();
}
