package com.niimbot.asset.framework.utils;

import cn.hutool.core.util.ReUtil;
import com.niimbot.asset.framework.core.enums.SystemResultCode;
import com.niimbot.jf.core.exception.category.BusinessException;
import com.niimbot.jf.core.exception.error.details.ResultCode;
import java.util.Set;
import java.util.stream.Stream;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

public class ValidationUtils {
    public static final String MOBILE_EMAIL_REG = "^\\s*?(.+)@(.+?)\\s*$|^(((13[0-9])|(14[579])|(15([0-3]|[5-9]))|(16[6])|(17[0135678])|(18[0-9])|(19[589]))\\d{8})$";
    public static final String MOBILE_REG = "^(((13[0-9])|(14[579])|(15([0-3]|[5-9]))|(16[6])|(17[0135678])|(18[0-9])|(19[589]))\\d{8})$";
    public static final String PASSWORD_REG = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,20}$";
    public static final String EMP_NO_REG = "^[a-zA-Z0-9-_]{2,8}$";
    public static final String EMP_POSITION_REG = "^[a-zA-Z0-9一-龥、.\\-_()（）]{2,20}$";
    public static final String LETTER_NUM_CHAR = "^[a-zA-Z0-9、.\\-_()（）]*$";
    public static final String INTEGER_MULTIPLE_OF_TEN = "^[1-9]\\d*0$";
    private static final Validator VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();

    private ValidationUtils() {
    }

    public static void checkMobile(ResultCode resultCode, String... mobiles) {
        boolean allMatch = Stream.of(mobiles).allMatch((mobile) -> {
            return ReUtil.isMatch("^(((13[0-9])|(14[579])|(15([0-3]|[5-9]))|(16[6])|(17[0135678])|(18[0-9])|(19[589]))\\d{8})$", mobile);
        });
        if (!allMatch) {
            throw new BusinessException(resultCode, new String[0]);
        }
    }

    public static void checkPassword(ResultCode resultCode, String... passwordArr) {
        boolean allMatch = Stream.of(passwordArr).allMatch((password) -> {
            return ReUtil.isMatch("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,20}$", password);
        });
        if (!allMatch) {
            throw new BusinessException(resultCode, new String[0]);
        }
    }

    public static void checkEmpNo(ResultCode resultCode, String... empNos) {
        boolean allMatch = Stream.of(empNos).allMatch((empNo) -> {
            return ReUtil.isMatch("^[a-zA-Z0-9-_]{2,8}$", empNo);
        });
        if (!allMatch) {
            throw new BusinessException(resultCode, new String[0]);
        }
    }

    public static void checkEmpPosition(ResultCode resultCode, String... empPositions) {
        boolean allMatch = Stream.of(empPositions).allMatch((position) -> {
            return ReUtil.isMatch("^[a-zA-Z0-9一-龥、.\\-_()（）]{2,20}$", position);
        });
        if (!allMatch) {
            throw new BusinessException(resultCode, new String[0]);
        }
    }

    public static <T> void validateDto(T t, Class<?>... groups) {
        Set<ConstraintViolation<T>> validate = VALIDATOR.validate(t, groups);
        validate.stream().findFirst().ifPresent((constraint) -> {
            throw new BusinessException(SystemResultCode.PARAM_IS_INVALID, new String[]{constraint.getMessage()});
        });
    }
}
