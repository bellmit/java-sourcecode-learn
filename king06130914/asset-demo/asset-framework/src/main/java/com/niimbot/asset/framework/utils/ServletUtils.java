package com.niimbot.asset.framework.utils;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.useragent.Platform;
import cn.hutool.http.useragent.UserAgentUtil;
import com.google.common.collect.ImmutableMap;
import com.niimbot.asset.framework.constant.SourceEnum;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

/**
 * 客户端工具类
 */
public class ServletUtils {

    /**
     * 获取String参数
     */
    public static String getParameter(String name) {
        return getRequest().getParameter(name);
    }

    /**
     * 获取String参数
     */
    public static String getParameter(String name, String defaultValue) {
        return Convert.toStr(getRequest().getParameter(name), defaultValue);
    }

    /**
     * 获取Integer参数
     */
    public static Integer getParameterToInt(String name) {
        return Convert.toInt(getRequest().getParameter(name));
    }

    /**
     * 获取Integer参数
     */
    public static Integer getParameterToInt(String name, Integer defaultValue) {
        return Convert.toInt(getRequest().getParameter(name), defaultValue);
    }

    /**
     * 获取request
     */
    public static HttpServletRequest getRequest() {
        return getRequestAttributes().getRequest();
    }

    /**
     * 获取response
     */
    public static HttpServletResponse getResponse() {
        return getRequestAttributes().getResponse();
    }

    /**
     * 获取session
     */
    public static HttpSession getSession() {
        return getRequest().getSession();
    }

    public static ServletRequestAttributes getRequestAttributes() {
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        return (ServletRequestAttributes) attributes;
    }

    /**
     * 将字符串渲染到客户端
     *
     * @param response 渲染对象
     * @param string   待渲染的字符串
     * @return null
     */
    public static String renderString(HttpServletResponse response, String string) {
        try {
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            response.getWriter().print(string);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 是否是Ajax异步请求
     *
     * @param request
     */
    public static boolean isAjaxRequest(HttpServletRequest request) {
        String accept = request.getHeader("accept");
        if ((accept != null) && accept.contains("application/json")) {
            return true;
        }

        String xRequestedWith = request.getHeader("X-Requested-With");
        if ((xRequestedWith != null) && xRequestedWith.contains("XMLHttpRequest")) {
            return true;
        }

        String uri = request.getRequestURI();
        if (StrUtil.equalsAnyIgnoreCase(uri, ".json", ".xml")) {
            return true;
        }

        String ajax = request.getParameter("__ajax");
        return StrUtil.equalsAnyIgnoreCase(ajax, "json", "xml");
    }

    /**
     * 获取客户端来源
     *
     * @return SourceEnum
     */
    public static SourceEnum getClientSource() {
        Platform platform = UserAgentUtil.parse(getRequest().getHeader("User-Agent"))
                .getPlatform();
        if (Platform.desktopPlatforms.stream().anyMatch(pl -> platform == pl)) {
            return SourceEnum.PC;
        } else if (platform.isAndroid()) {
            return SourceEnum.ANDROID;
        } else if (platform.isIos() || platform.isIPad() || platform.isIPhoneOrIPod()) {
            return SourceEnum.IOS;
        }
        return SourceEnum.UNKNOWN;
    }

    /**
     * 获取请求相关信息
     *
     * @return map
     */
    public static Map<String, String> getServerInfo() {
        return ImmutableMap.<String, String>builder()
                .put("protocol", getRequest().getScheme())
                .put("serverName", getRequest().getServerName())
                .put("port", StringUtils.toString(getRequest().getServerPort()))
                .put("uri", getRequest().getRequestURI())
                .build();
    }

    /**
     * 获取服务域名
     * subDomain -- 本地 --》local
     * -- customer 模块 customer
     * -- manage 模块 manage
     *
     * @return https://www.baidu.com
     */
    public static String getServerDomain(String subDomain) {
        Map<String, String> serverInfo = getServerInfo();
        if (StringUtils.equals(subDomain, "local")) {
            return StringUtils.concat(true, serverInfo.get("protocol"), "://",
                    serverInfo.get("serverName"), ":", serverInfo.get("port"));
        }
        return StringUtils.concat(true, serverInfo.get("protocol"), "://",
                serverInfo.get("serverName"), "/", subDomain);
    }
}
