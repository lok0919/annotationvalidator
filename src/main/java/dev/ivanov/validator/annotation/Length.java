package dev.ivanov.validator.annotation;

import dev.ivanov.validator.rule.LengthRule;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Target(FIELD)
@ValidateBy(LengthRule.class)
public @interface Length {
  String message() default "";

  int min() default 0;

  int max() default Integer.MAX_VALUE;
}
