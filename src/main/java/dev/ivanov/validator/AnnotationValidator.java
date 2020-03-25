package dev.ivanov.validator;

import dev.ivanov.validator.annotation.Valid;
import dev.ivanov.validator.annotation.ValidateBy;
import dev.ivanov.validator.api.EntityValidator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;

import org.apache.commons.lang3.ClassUtils;

import static org.apache.commons.lang3.reflect.FieldUtils.getAllFieldsList;

public class AnnotationValidator implements EntityValidator {

  @Override
  public void validate(Object entity) {
    processFields(entity, getAllFieldsList(entity.getClass()));
  }

  private void processFields(Object entity, List<Field> fields) {
    processFields(entity, getAllFieldsList(entity.getClass()), "");
  }

  private void processFields(Object entity, List<Field> fields, String contextPath) {
    for (Field f : fields) {
      f.setAccessible(true);

      if (!f.isAnnotationPresent(Valid.class)) continue;

      Object fieldValue = getValue(f, entity);

      if (fieldValue != null && !f.getType().isAssignableFrom(String.class) && !ClassUtils.isPrimitiveOrWrapper(f.getType()))
        processFields(fieldValue, getAllFieldsList(f.getType()), f.getName() + ".");
      else {
        Annotation[] annotations = f.getAnnotations();
        if (annotations.length == 0) continue;

        for (Annotation annotation : annotations) {
          if (annotation.annotationType().isAnnotationPresent(ValidateBy.class))
            doCheck(annotation, contextPath + f.getName(), fieldValue);
        }
      }
    }
  }

  private void doCheck(Annotation annotation, String fieldName, Object fieldValue) {
    if (annotation.annotationType().isAnnotationPresent(ValidateBy.class)) {
      final ValidateBy validateBy = annotation.annotationType().getAnnotation(ValidateBy.class);

      try {
        validateBy.value().newInstance().check(annotation, fieldName, fieldValue);
      } catch (InstantiationException e) {
        e.printStackTrace();
      } catch (IllegalAccessException e) {
        e.printStackTrace();
      }
    }

  }

  private Object getValue(Field field, Object entity) {
    try {
      return field.get(entity);
    } catch (IllegalAccessException e) {
      throw new IllegalStateException(e);
    }
  }
}