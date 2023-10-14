package com.example.simpledashboard.validator;

import com.example.simpledashboard.annotation.PhoneNumber;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

// 언젠간 사용하게 예시로 일단 만들어놓음
public class PhoneNumberValidator implements ConstraintValidator<PhoneNumber, String> {

    private String regexp;
    @Override
    public void initialize(PhoneNumber constraintAnnotation) {
        this.regexp = constraintAnnotation.regexp();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return Pattern.matches(regexp, value);
    }
}
