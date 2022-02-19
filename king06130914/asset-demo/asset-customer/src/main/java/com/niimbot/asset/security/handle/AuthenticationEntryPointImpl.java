package com.niimbot.asset.security.handle;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.niimbot.asset.framework.core.enums.SystemResultCode;
import com.niimbot.asset.framework.utils.ResultUtils;
import com.niimbot.asset.framework.utils.ServletUtils;
import com.niimbot.jf.core.result.FailureResult;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

/**
 * 认证失败处理类 返回未授权
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint, Serializable {
    private static final long serialVersionUID = -8970718410437077606L;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e)
            throws IOException {
        FailureResult result = ResultUtils.parseFailure(SystemResultCode.TOKEN_EXPIRE, request, response, e);
        // 使用Jackson序列化
        ObjectMapper mapper=new ObjectMapper();
        ServletUtils.renderString(response, mapper.writeValueAsString(result));
    }
}
