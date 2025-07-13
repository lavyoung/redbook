package com.lavy.redbook.kv.biz.domian.repository;

import java.util.UUID;

import org.springframework.data.cassandra.repository.CassandraRepository;

import com.lavy.redbook.kv.biz.domian.dataobject.NoteContentDO;

/**
 * @author <a href="lavyoung1325@outlook.com">lavy</a>
 * @date: 2025/7/13
 * @version: v1.0.0
 * @description: 笔记内容存储
 */
public interface NoteContentRepository extends CassandraRepository<NoteContentDO, UUID> {
}
