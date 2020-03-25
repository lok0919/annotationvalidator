package dev.ivanov.validator.annotation;

import dev.ivanov.validator.rule.Rule;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Target(ANNOTATION_TYPE)
public @interface ValidateBy {
  Class<? extends Rule> value();
}
