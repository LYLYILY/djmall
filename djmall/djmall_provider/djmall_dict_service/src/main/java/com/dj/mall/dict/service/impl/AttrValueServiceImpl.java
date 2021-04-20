package com.dj.mall.dict.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dj.mall.dict.service.AttrValueService;
import com.dj.mall.dict.entity.AttrValueEntity;
import com.dj.mall.dict.mapper.AttrValueMapper;
import org.springframework.stereotype.Service;

@Service
public class AttrValueServiceImpl extends ServiceImpl<AttrValueMapper, AttrValueEntity> implements AttrValueService {
}
