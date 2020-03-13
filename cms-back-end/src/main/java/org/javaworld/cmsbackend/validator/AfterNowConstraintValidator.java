package org.javaworld.cmsbackend.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.javaworld.cmsbackend.util.DateUtil;

public class AfterNowConstraintValidator implements ConstraintValidator<AfterNow, Long> {

	@Override
	public boolean isValid(Long value, ConstraintValidatorContext context) {
		if (value > DateUtil.getCurrentDateTimeAsTimeStamp()) {
			return true;
		}
		return false;
	}

}
