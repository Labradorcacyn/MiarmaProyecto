package com.miarma.cynthia.exception.annotations;

import com.miarma.cynthia.exception.Validator.UniqueNameValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.METHOD,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueNameValidator.class)
@Documented
public @interface UniqueName {

    String message () default "{POI.route.unique}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}