package com.mms.mms_api.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.mms.mms_api.common.AppConstant;

@Constraint(validatedBy = PasswordValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Password {
    int min() default AppConstant.PASSWORD_MIN;

    String message() default "{user.password.required}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}