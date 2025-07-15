package com.lavy.redbook.note.biz.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lavy.redbook.note.biz.domain.dataobject.ChannelTopicDO;
import com.lavy.redbook.note.biz.domain.mapper.ChannelTopicDOMapper;
import com.lavy.redbook.note.biz.service.ChannelTopicService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author <a href="lavyoung1325@outlook.com">lavy</a>
 * @date: 2025/7/15
 * @version: v1.0.0
 * @description: 频道-话题关系服务实现类
 */
@Service
@Slf4j
public class ChannelTopicServiceImpl extends ServiceImpl<ChannelTopicDOMapper, ChannelTopicDO> implements
        ChannelTopicService {
}
