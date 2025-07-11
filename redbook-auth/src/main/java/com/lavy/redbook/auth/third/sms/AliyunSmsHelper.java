package com.lavy.redbook.auth.third.sms;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.lavy.redbook.framework.common.util.JsonUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * @author lavyoung1325 <2034549297@qq.com>
 * Created on 2025-07-11
 */
@Slf4j
@Component
public class AliyunSmsHelper {

    @Autowired
    @Qualifier("aliyunSmsClient")
    private Client client;
    @Autowired
    private AliyunSmsConfig aliyunSmsConfig;

    /**
     * 发送短信
     *
     * @param phoneNumber 手机号
     * @param code 验证码
     */
    public void sendSms(String phoneNumber, String code) {
        Map<String, String> map = Map.of("code", code);
        SendSmsRequest sendSmsRequest = new SendSmsRequest()
                .setPhoneNumbers(phoneNumber)
                .setSignName(aliyunSmsConfig.getSignName())
                .setTemplateCode(aliyunSmsConfig.getTemplateCode())
                .setTemplateParam(JsonUtils.toJsonString(map));
        // 获取响应对象
        try {
            SendSmsResponse sendSmsResponse = client.sendSms(sendSmsRequest);
            log.info("发送验证码响应结果：{}", JsonUtils.toJsonString(sendSmsResponse));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
