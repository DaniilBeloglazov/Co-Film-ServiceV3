package com.example.cofilmservicev3.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class NullableNotBlankValidator implements ConstraintValidator<NullableNotBlank, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (Objects.isNull(value))
            return true;
        return value.trim().length() != 0;
    }
}
