package dev.ivanov.validator;

import dev.ivanov.validator.annotation.Length;
import dev.ivanov.validator.annotation.NotEmpty;
import dev.ivanov.validator.annotation.Valid;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.fail;

public class AnnotationValidatorTest {
  @Test
  public void given_nestedObject_on_validate_should_pass() {
    Entity entity = new Entity("1", "22", new InnerEntity("3"));
    AnnotationValidator annotationValidator = new AnnotationValidator();

    annotationValidator.validate(entity);
  }

  @Test
  public void given_nestedObject_on_validate_should_fail() {
    Entity entity = new Entity("1", "22", new InnerEntity(""));
    AnnotationValidator annotationValidator = new AnnotationValidator();

    try {
      annotationValidator.validate(entity);
      fail("Validation should be failed");
    } catch (NullPointerException e) {

    }
  }

  private class Entity {
    @Valid
    @NotEmpty
    private String firstString;

    @Valid
    @Length(min = 2)
    private String second;

    @Valid
    @NotEmpty
    private InnerEntity innerEntity;

    public Entity(String firstString, String second, InnerEntity innerEntity) {
      this.firstString = firstString;
      this.second = second;
      this.innerEntity = innerEntity;
    }
  }

  private class InnerEntity {
    @Valid
    @NotEmpty
    private String thrid;

    public InnerEntity(String thrid) {
      this.thrid = thrid;
    }
  }
}
