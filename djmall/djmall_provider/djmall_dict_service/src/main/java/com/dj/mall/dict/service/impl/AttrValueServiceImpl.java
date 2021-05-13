package com.dj.mall.dict.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dj.mall.dict.service.AttrValueService;
import com.dj.mall.dict.entity.attr.AttrValueEntity;
import com.dj.mall.dict.mapper.attr.AttrValueMapper;
import org.springframework.stereotype.Service;

@Service
public class AttrValueServiceImpl extends ServiceImpl<AttrValueMapper, AttrValueEntity> implements AttrValueService {
}
