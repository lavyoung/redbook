package com.lavy.redbook.note.biz.constant;

/**
 * @author <a href="lavyoung1325@outlook.com">lavy</a>
 * @date: 2025/7/17
 * @version: v1.0.0
 * @description: redis key 常量
 */
public class RedisKeyConstants {


    /**
     * 笔记详情 KEY 前缀
     */
    public static final String NOTE_DETAIL_KEY = "note:detail:";


    /**
     * 构建完整的笔记详情 KEY
     *
     * @param noteId 笔记 ID
     * @return 笔记详情 KEY
     */
    public static String buildNoteDetailKey(Long noteId) {
        return NOTE_DETAIL_KEY + noteId;
    }
}
