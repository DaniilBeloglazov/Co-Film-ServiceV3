package com.example.cofilmservicev3.annotation;

import lombok.val;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraintvalidation.SupportedValidationTarget;
import javax.validation.constraintvalidation.ValidationTarget;

@SupportedValidationTarget(ValidationTarget.ANNOTATED_ELEMENT)
public class MultipartSizeArrayValidator implements ConstraintValidator<MultipartSize, MultipartFile[]> {
    private long min;
    private long max;
    @Override
    public void initialize(MultipartSize constraintAnnotation) {
        this.min = constraintAnnotation.min();
        this.max = constraintAnnotation.max();
    }
    @Override
    public boolean isValid(MultipartFile[] value, ConstraintValidatorContext context) {
        if (value == null)
            return true;
        for (MultipartFile file : value) {
            val size = file.getSize();
            if (size >= min && size <= max)
                return false;
        }
        return true;
    }
}
