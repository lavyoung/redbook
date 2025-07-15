package com.lavy.redbook.note.biz.domain.dataobject;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lavy.redbook.note.biz.enums.NoteStatusEnum;
import com.lavy.redbook.note.biz.enums.NoteTypeEnum;
import com.lavy.redbook.note.biz.enums.NoteVisibleEnum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author <a href="lavyoung1325@outlook.com">lavy</a>
 * @date: 2025/7/15
 * @version: v1.0.0
 * @description: 笔记
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_note")
public class NoteDO {
    @TableId
    private Long id;

    private String title;

    private Boolean isContentEmpty;

    private Long creatorId;

    private Long topicId;

    private String topicName;

    private Boolean isTop;

    private NoteTypeEnum type;

    private String imgUris;

    private String videoUri;

    private NoteVisibleEnum visible;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private NoteStatusEnum status;

}