package dev.ivanov.validator.annotation;

import dev.ivanov.validator.rule.NotEmptyRule;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Target(FIELD)
@ValidateBy(NotEmptyRule.class)
public @interface NotEmpty {
  String message() default "";
}
