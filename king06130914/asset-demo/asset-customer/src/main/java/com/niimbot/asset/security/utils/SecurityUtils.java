package com.niimbot.asset.security.utils;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import cn.hutool.extra.spring.SpringUtil;
import com.niimbot.asset.framework.autoconfig.AssetConfig;
import com.niimbot.asset.framework.core.enums.SystemResultCode;
import com.niimbot.asset.framework.model.LoginUserDto;
import com.niimbot.jf.core.exception.category.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.regex.Pattern;

/**
 * created by chen.y on 2020/10/13 15:24
 */
@Slf4j
public class SecurityUtils {

    /**
     * 获取用户
     **/
    public static LoginUserDto getLoginUser() {
        try {
            return (LoginUserDto) getAuthentication().getPrincipal();
        } catch (Exception e) {
            log.error("获取用户信息异常", e);
            throw new BusinessException(SystemResultCode.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 获取Authentication
     */
    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * 生成BCryptPasswordEncoder密码
     *
     * @param password 密码
     * @return 加密字符串
     */
    public static String encryptPassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }

    /**
     * 判断密码是否相同
     *
     * @param rawPassword     真实密码
     * @param encodedPassword 加密后字符
     * @return 结果
     */
    public static boolean matchesPassword(String rawPassword, String encodedPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    /**
     * 解析前端RSA加密密码
     *
     * @param encodedPassword 加密后字符
     * @return 解密后密码
     */
    public static String decryptPassword(String encodedPassword) {
        AssetConfig assetConfig = SpringUtil.getBean(AssetConfig.class);
        try {
            RSA rsa = SecureUtil.rsa(assetConfig.getRsaPrivateKey(), null);
            byte[] decode = Base64.decode(encodedPassword);
            byte[] decrypt = rsa.decrypt(decode, KeyType.PrivateKey);
            return StrUtil.str(decrypt, CharsetUtil.CHARSET_UTF_8);
        } catch (Exception e) {
            log.info("解密失败！" + e.getMessage() + "原因：" + e.getCause());
            throw new BusinessException(SystemResultCode.USER_PASSWORD_DECRYPT_ERROR);
        }
    }

    /***
     *  rsa--base64加密密码
     *
     * @param password 原始密码
     * @return 加密后密码
     */
    public static String rsaEncode(String password) {
        AssetConfig assetConfig = SpringUtil.getBean(AssetConfig.class);
        RSA rsa = SecureUtil.rsa(null, assetConfig.getRsaPublicKey());
        byte[] encrypt = rsa.encrypt(password, CharsetUtil.UTF_8, KeyType.PublicKey);
        return Base64.encode(encrypt);
    }

    /**
     * 检查手机号格式是否正确
     *
     * @param phone 待检查手机号
     * @return result
     */
    public static boolean checkPhone(String phone) {
        String regex = "^(((13[0-9])|(14[579])|(15([0-3]|[5-9]))|(16[6])|(17[0135678])|(18[0-9])|(19[89]))\\d{8})$";
        if (phone.length() != 11) {
            return false;
        }
        Pattern p = Pattern.compile(regex);
        return p.matcher(phone).matches();
    }

    public static void main(String[] args) {

        String pub = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCmHeJDPH81ZAsC4uvUI9ZhxIIJHaxQ83caN8Oz3WyNk3O++ScFfTD9t8b5HpP/fRUdCiOUag32rKF4CDE2qkIzCpLVor1LZuwh8S4e6hc1EaofvP6TokFzhQFuAO0L+rtB1GnSI75M59BHTbt80Wmp2fSLvtoLYBq+/8twSnm4xQIDAQAB";
        String pri = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAKYd4kM8fzVkCwLi69Qj1mHEggkdrFDzdxo3w7PdbI2Tc775JwV9MP23xvkek/99FR0KI5RqDfasoXgIMTaqQjMKktWivUtm7CHxLh7qFzURqh+8/pOiQXOFAW4A7Qv6u0HUadIjvkzn0EdNu3zRaanZ9Iu+2gtgGr7/y3BKebjFAgMBAAECgYBBGpMz0MD2Vtgt8GuGv+jkdlSUaAnBzDmQj+xmUNCbh/+kCBfwAcmyllUoC4Drb8VXlUKuhwYj+DJsTaGkEARTRM8BQ6VGmXLDfTW3d8fiUocSRVhlSUMIp6VuiX3oTAV2pWgU5kX2lN1THsaT5iuvzDk3KW4nc9SZzVubnP6oHQJBANsxCwIPCuerIa0SFA0/qQXD6S1YO8hTAuqu0KOh/xUeTkRS2qWHEj1P0wQ1FhJzp8aN/SiOFEB/Qgm6uegXf+MCQQDCAyzJBXW+OKEkZI4NVmKHu/ILYd096ACHNJAKGE+bLDL5Pkh9lEgaZrMtaU+qQ4p+ZS1+NqrOmIgYOk38I/U3AkAD2zQHYD5f3wnjoprJtOIDYPP8QT4kAxndCSBQkKwNMhBMcWy5VswCWiIrQ8fUoUZFXwAiM4W1sMd3plpY8/jjAkEAwJmuSBFnheNp9VrtukfboKvv6WTJ4b8DZzXDGIJx5LXIFK7EAAbIXY0+qwI01+4c+sUumHFkt5us34BiEsEPmQJBAK853Epah2POL7aRdPgL9hKOfu894IK+e2N8LJgY+F5H+nQmWtfrqvE56D2eBLLdfvdJRvnkmuy82NdKv5WuQkQ=";

        RSA rsa = new RSA(pri, pub);

        byte[] encrypt = rsa.encrypt("jc123456", CharsetUtil.CHARSET_UTF_8, KeyType.PublicKey);

        String encode = Base64.encode(encrypt);
        System.out.println(encode);

        // =========================
        rsa = SecureUtil.rsa(pri, null);
        byte[] decode = Base64.decode("fvV8RuOQxIQ3Dy2D+55+yuWLi2wG+5JsFChrrt133rDQFBXCHiVC+J/HGag1f9gOnPK2B6iPpCD56cII46sXKMeTMkc2rByaaIOBK9kfX1RF/jcsaLIGXHki9AIffcOK3fiqi6aktCNowIOQsxXwMZAmHN/0c4STbuzIL9Nhzaw=");
        byte[] decrypt = rsa.decrypt(decode, KeyType.PrivateKey);
        System.out.println(StrUtil.str(decrypt, CharsetUtil.CHARSET_UTF_8));

    }
}
