package likelion.eight.common.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import likelion.eight.common.validator.PhoneNumberValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Constraint(validatedBy = {PhoneNumberValidator.class})
@Retention(RetentionPolicy.RUNTIME)
public @interface PhoneNumber {

    String message() default "핸드폰 번호 양식에 맞지 않습니다. ex) 010-****-****";
    String regexp() default "^\\d{3}-\\d{3,4}-\\d{4}$";

    Class<?>[] groups() default {}; // Default 그룹 명시
    Class<? extends Payload>[] payload() default {};
}

