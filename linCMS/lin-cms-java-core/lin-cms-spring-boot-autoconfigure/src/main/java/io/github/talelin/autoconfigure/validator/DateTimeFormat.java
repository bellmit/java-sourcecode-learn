package io.github.talelin.autoconfigure.validator;

import io.github.talelin.autoconfigure.validator.impl.DateTimeFormatValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.TYPE_PARAMETER;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 日期格式校验器
 * 对 String 类型进行校验，是否匹配给定的模式
 * 默认校验日期格式 yyyy-MM-dd HH:mm:ss
 *
 * @author pedro@TaleLin
 * @author Juzi@TaleLin
 */
@Documented
@Retention(RUNTIME)
@Target({FIELD, TYPE_USE, TYPE_PARAMETER})
@Constraint(validatedBy = DateTimeFormatValidator.class)
public @interface DateTimeFormat {

    String message() default "date pattern invalid";

    String pattern() default "yyyy-MM-dd HH:mm:ss";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
