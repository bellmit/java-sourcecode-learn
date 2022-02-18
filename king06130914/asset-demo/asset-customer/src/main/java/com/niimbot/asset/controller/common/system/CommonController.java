package com.niimbot.asset.controller.common.system;

import com.alibaba.fastjson.JSONObject;
import com.dtflys.forest.exceptions.ForestNetworkException;
import com.niimbot.asset.framework.constant.MqConstant;
import com.niimbot.asset.framework.constant.RedisConstant;
import com.niimbot.asset.framework.service.RedisService;
import com.niimbot.asset.framework.utils.RabbitMqUtils;
import com.niimbot.asset.framework.utils.RocketMqUtils;
import com.niimbot.asset.framework.utils.ValidationUtils;
import com.niimbot.asset.service.feign.SmsCodeFeignClient;
import com.niimbot.asset.service.http.TycHttpClient;
import com.niimbot.jf.core.component.annotation.ResultController;
import com.niimbot.system.CusEmployeeDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Map;

/**
 * 公共控制器【验证码、短信等】
 *
 * @author dk
 */
@Slf4j
@Api(tags = {"公共接口"})
@ResultController
@RequestMapping("api/common")
@Validated
@RequiredArgsConstructor
public class CommonController {

    private final SmsCodeFeignClient smsCodeFeignClient;

    private final RocketMqUtils rocketMqUtils;

    private final RabbitMqUtils rabbitMqUtils;

    private final RedisService redisService;

    private final TycHttpClient tycHttpClient;

    /**
     * 获取验证码
     *
     * @param mobile 手机号
     * @return 成功-true 失败-false
     */
    @GetMapping("/smsCode")
    @ApiOperation(value = "获取手机验证码", notes = "四位手机验证码")
    public Boolean getSmsVerifyCode(@NotBlank(message = "请输入手机号")
                                    @Size(min = 11, max = 11, message = "输入的手机号无效")
                                    @Pattern(regexp = ValidationUtils.MOBILE_REG,
                                            message = "输入的手机号无效") String mobile) {
        smsCodeFeignClient.sendSmsCode(mobile);
        return true;
    }

    /**
     * 邮箱获取验证码
     *
     * @param email 邮箱
     * @return 成功-true 失败-false
     */
    @GetMapping("/emailCode")
    @ApiOperation(value = "通过邮箱获取验证码【设置/修改密码，邮箱发送验证码】", notes = "四位验证码")
    public Boolean getEmailVerifyCode(@NotBlank(message = "请输入邮箱")
                                      @Pattern(regexp = com.niimbot.validate.ValidationUtils.EMAIL_REG, message = "邮箱格式不正确")
                                      @Size(max = 255, message = "邮箱不得超过255个字符") String email) {
        smsCodeFeignClient.sendEmailCode(email);
        return true;
    }

    /**
     * 测试rocketmq生成消息
     *
     */
    @GetMapping("/testProducter")
    public void testProducter() {
        CusEmployeeDto cusEmployeeDto = new CusEmployeeDto();
        cusEmployeeDto.setEmpName("xiaoli");
        cusEmployeeDto.setId(25L);
        cusEmployeeDto.setEmpNo("123456");
        rocketMqUtils.asyncSend(MqConstant.ASSET_TOPIC, MessageBuilder.withPayload(cusEmployeeDto).build());
    }

    /**
     * 测试rabbitmq生成消息
     *
     */
    @RequestMapping("/sendMsg")
    public String sendMsg() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("email", "2830493845@qq.com");
        jsonObject.put("timestamp", System.currentTimeMillis());
        String jsonString = jsonObject.toJSONString();
        rabbitMqUtils.send("test_queue", jsonString);
        return "success";
    }

    @ApiOperation(value = "获取企业信息")
    @GetMapping(value = "/companyInfo")
    public Object productScan(@ApiParam(name = "companyName", value = "公司名称")
                                  @RequestParam("companyName") String companyName) {
        try {
            // 查缓存
            Object cache = redisService.hGet(RedisConstant.COMPANY_INFO, companyName);
            // 缓存没有查询接口
            if (cache == null) {
                Map<String, Object> companyInfo = tycHttpClient.companyInfo(companyName);
                cache = companyInfo.get("result");
//                cache = JSON.parseObject(JSON.toJSONString(result), ProductDto.class);
//                // 写缓存
//                redisService.hSet(RedisConstant.COMPANY_INFO, companyName, cache, 7, TimeUnit.DAYS);
            }
            return cache;
        } catch (ForestNetworkException e) {
            log.error("获取企业信息失败", e);
        }
        return null;
    }
}
