package com.mms.mms_api.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.mms.mms_api.common.AppConstant;
import com.mms.mms_api.util.validator.PasswordValidator;

@Constraint(validatedBy = PasswordValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordMatch {
    int min() default AppConstant.PASSWORD_MIN;

    boolean ignoreEmpty() default false;

    String message() default "{user.password.required}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}