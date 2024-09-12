package com.msl.validation;

import com.msl.anno.State;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class StateValidation implements ConstraintValidator<State, String> {

    /**
     *
     * @param value 将来要校验的数据
     * @param context
     * @return 如果返回false，则校验不通过，如果返回true，则校验通过
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // 提供校验规则
        if (value == null) {
            return false;
        }
        if ("已发布".equals(value) || "草稿".equals(value)) {
            return true;
        }
        return false;
    }
}
