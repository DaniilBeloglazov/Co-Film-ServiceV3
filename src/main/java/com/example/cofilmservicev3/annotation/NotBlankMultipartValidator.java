package com.example.cofilmservicev3.annotation;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class NotBlankMultipartValidator implements ConstraintValidator<NotBlankMultipart, MultipartFile> {
    @Override
    public boolean isValid(MultipartFile value, ConstraintValidatorContext context) {
        if (Objects.isNull(value))
            return true;
        return value.getContentType() != null;
    }
}
