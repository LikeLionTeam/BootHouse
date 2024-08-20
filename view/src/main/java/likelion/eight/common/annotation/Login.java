package likelion.eight.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
/**
 * 사용자 인증 정보를 주입받기 위한 어노테이션
 * @param required true = 회원, false = 비회원 (기본값: true)
 */
public @interface Login {
    boolean required() default true;
}
