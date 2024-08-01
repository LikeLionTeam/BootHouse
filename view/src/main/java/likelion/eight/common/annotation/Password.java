package likelion.eight.common.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import likelion.eight.common.validator.PasswordValidator;
import likelion.eight.common.validator.PhoneNumberValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Constraint(validatedBy = {PasswordValidator.class})
@Retention(RetentionPolicy.RUNTIME)
public @interface Password {

    String message() default "비밀번호는 최소 6자 이상이며 숫자, 영문자, 특수문자를 각각 1개 이상 포함해야 합니다.";
    String regexp() default "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W).{6,}$";

    Class<?>[] groups() default {}; // Default 그룹 명시
    Class<? extends Payload>[] payload() default {};
}

