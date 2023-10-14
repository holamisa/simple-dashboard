package com.example.simpledashboard.annotation;

import com.example.simpledashboard.validator.PhoneNumberValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// 언젠간 사용하게 예시로 일단 만들어놓음
// 직접 annotation 생성하여 PhoneNumber Validation 하기
@Constraint(validatedBy = {PhoneNumberValidator.class})
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PhoneNumber {

    String message() default "핸도폰 양식 000-0000-0000 입니다.";
    String regexp() default "^\\d{2,3}-\\d{3,4}-\\d{4}$";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
