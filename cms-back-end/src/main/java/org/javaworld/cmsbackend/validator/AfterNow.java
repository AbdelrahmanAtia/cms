package org.javaworld.cmsbackend.validator;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * The annotated element must be a number whose value must be higher than
 * current time stamp
 * <p>
 * Supported types are:
 * <ul>
 * <li>{@code Long}</li>
 * </ul>
 * 
 * @author Abdelrahman Attya
 */
@Constraint(validatedBy = AfterNowConstraintValidator.class)
@Retention(RUNTIME)
@Target(FIELD)
public @interface AfterNow {

	public String message() default "must be higher than current timestamp";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
