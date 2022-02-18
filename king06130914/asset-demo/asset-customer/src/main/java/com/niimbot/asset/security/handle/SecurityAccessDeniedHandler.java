package com.niimbot.asset.security.handle;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.niimbot.asset.framework.core.enums.SystemResultCode;
import com.niimbot.asset.framework.utils.ResultUtils;
import com.niimbot.asset.framework.utils.ServletUtils;
import com.niimbot.jf.core.result.FailureResult;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * created by chen.y on 2020/11/16 19:45
 */
@Service
public class SecurityAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
        FailureResult result = ResultUtils.parseFailure(SystemResultCode.PERMISSION_NO_ACCESS, request, response, e);
        // 使用Jackson序列化
        ObjectMapper mapper=new ObjectMapper();
        ServletUtils.renderString(response, mapper.writeValueAsString(result));
    }
}
