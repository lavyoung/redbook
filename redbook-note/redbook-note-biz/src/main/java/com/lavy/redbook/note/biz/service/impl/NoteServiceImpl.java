package com.lavy.redbook.note.biz.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lavy.redbook.note.biz.domain.dataobject.NoteDO;
import com.lavy.redbook.note.biz.domain.mapper.NoteDOMapper;
import com.lavy.redbook.note.biz.service.NoteService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author <a href="lavyoung1325@outlook.com">lavy</a>
 * @date: 2025/7/15
 * @version: v1.0.0
 * @description: 笔记服务实现类
 */
@Service
@Slf4j
public class NoteServiceImpl extends ServiceImpl<NoteDOMapper, NoteDO> implements NoteService {
}
