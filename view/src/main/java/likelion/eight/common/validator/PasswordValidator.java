package likelion.eight.common.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import likelion.eight.common.annotation.Password;
import likelion.eight.common.annotation.PhoneNumber;

import java.util.regex.Pattern;

public class PasswordValidator implements ConstraintValidator<Password, String> {

    private String regexp;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        return Pattern.matches(regexp, value);
    }

    @Override
    public void initialize(Password constraintAnnotation) {
        regexp = constraintAnnotation.regexp();
    }

}
