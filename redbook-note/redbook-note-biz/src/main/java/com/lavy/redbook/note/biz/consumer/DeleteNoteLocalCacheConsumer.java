package com.lavy.redbook.note.biz.consumer;

import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

import com.lavy.redbook.note.biz.constant.MQConstants;
import com.lavy.redbook.note.biz.service.NoteService;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;

/**
 * @author <a href="lavyoung1325@outlook.com">lavy</a>
 * @date: 2025/7/19
 * @version: v1.0.0
 * @description: 监听删除笔记缓存mq消息
 */
@Component
@Slf4j
@RocketMQMessageListener(consumerGroup = "redbook-group",
        topic = MQConstants.TOPIC_DELETE_NOTE_LOCAL_CACHE,
        messageModel = MessageModel.BROADCASTING)
public class DeleteNoteLocalCacheConsumer implements RocketMQListener<String> {

    @Resource
    private NoteService noteService;

    @Override
    public void onMessage(String msg) {
        Long noteId = Long.valueOf(msg);
        log.info("## 消费者消费成功, noteId: {}", noteId);
        noteService.deleteNoteLocalCache(noteId);
    }
}
