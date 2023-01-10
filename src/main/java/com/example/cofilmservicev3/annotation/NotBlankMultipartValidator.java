package com.example.cofilmservicev3.annotation;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class NotBlankMultipartValidator implements ConstraintValidator<NotBlankMultipart, MultipartFile> {
    @Override
    public boolean isValid(MultipartFile value, ConstraintValidatorContext context) {
        return Objects.isNull(value) || Objects.nonNull(value.getContentType());
    }
}
