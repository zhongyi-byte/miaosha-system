package com.zhongyi.seckill.validator;
import com.zhongyi.seckill.validator.*;
import org.apache.commons.lang3.StringUtils;
import com.zhongyi.seckill.utils.*;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;




public class IsMobileValidator implements ConstraintValidator<IsMobile, String> {

    private boolean required = false;

    //初始化
    @Override
    public void initialize(IsMobile isMobile) {
        required = isMobile.required();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (required) {
            return MobileValidator.isMobile(value);
        } else {
            if (StringUtils.isEmpty(value)) {
                return true;
            } else {
                return MobileValidator.isMobile(value);
            }
        }
    }
}
