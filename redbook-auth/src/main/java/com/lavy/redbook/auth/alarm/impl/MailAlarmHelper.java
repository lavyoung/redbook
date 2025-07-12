package com.lavy.redbook.auth.alarm.impl;

import com.lavy.redbook.auth.alarm.AlarmInterface;

import lombok.extern.slf4j.Slf4j;

/**
 * @author <a href="lavyoung1325@outlook.com">lavy</a>
 * @date: 2025/7/12
 * @version: v1.0.0
 * @description: 邮件报警
 */
@Slf4j
public class MailAlarmHelper implements AlarmInterface {


    @Override
    public boolean send(String message) {
        log.info("==> 邮件报警：{}", message);
        return false;
    }

}
