package com.lavy.redbook.auth.alarm;

/**
 * @author <a href="lavyoung1325@outlook.com">lavy</a>
 * @date: 2025/7/12
 * @version: v1.0.0
 * @description: 告警接口
 */
public interface AlarmInterface {

    /**
     * 发送告警信息
     *
     * @param message 告警信息
     * @return 发送结果
     */
    boolean send(String message);
}
