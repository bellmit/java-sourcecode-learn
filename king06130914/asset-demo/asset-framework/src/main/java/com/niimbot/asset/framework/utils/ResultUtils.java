package com.niimbot.asset.framework.utils;

import cn.hutool.core.util.ObjectUtil;
import com.niimbot.jf.core.exception.error.details.ResultCode;
import com.niimbot.jf.core.result.FailureResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

public class ResultUtils {

    private ResultUtils() {

    }

    /**
     * 构建错误消息返回体
     */
    public static FailureResult parseFailure(ResultCode rc,
                                             HttpServletRequest request,
                                             HttpServletResponse response) {
        return parseFailure(rc, request, response, null);
    }

    /**
     * 构建错误消息返回体
     */
    public static FailureResult parseFailure(ResultCode rc,
                                             HttpServletRequest request,
                                             HttpServletResponse response,
                                             Exception e) {
        FailureResult result = new FailureResult();
        result.setCode(rc.getCode());
        result.setMessage(rc.getMessage());
        result.setError("");
        result.setPath(request.getRequestURI());
        if (ObjectUtil.isNotNull(e)) {
            result.setException(e.getClass().getName());
        } else {
            result.setException("");
        }
        result.setTimestamp(new Date());
        response.setStatus(rc.getHttpStatus().value());
        return result;
    }
}
