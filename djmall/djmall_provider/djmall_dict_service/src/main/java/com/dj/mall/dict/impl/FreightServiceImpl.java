package com.dj.mall.dict.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dj.mall.common.base.BusinessException;
import com.dj.mall.common.util.DozerUtil;
import com.dj.mall.dict.api.FreightService;
import com.dj.mall.dict.dto.FreightDTO;
import com.dj.mall.dict.entity.FreightEntity;
import com.dj.mall.dict.mapper.FreightMapper;
import com.dj.mall.dict.mapper.bo.FreightBO;

import java.util.List;

@Service
public class FreightServiceImpl extends ServiceImpl<FreightMapper, FreightEntity> implements FreightService {

    /**
     * 运费展示
     *
     * @return
     * @throws Exception
     */
    @Override
    public List<FreightDTO> freightShow() throws Exception {
        List<FreightBO> freightBOS = baseMapper.freightShow();
        return DozerUtil.mapList(freightBOS, FreightDTO.class);
    }

    /**
     * 运费新增
     *
     * @param freightDTO
     * @throws Exception
     */
    @Override
    public void add(FreightDTO freightDTO) throws Exception, BusinessException {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("logistics_company", freightDTO.getLogisticsCompany());
        FreightEntity freightEntity = super.getOne(queryWrapper);
        if(freightEntity != null){
            throw new BusinessException("已cunzai");
        }
        super.save(DozerUtil.map(freightDTO, FreightEntity.class));
    }

    /**
     * 根据id查找运费
     *
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public FreightDTO findFreightById(Integer id) throws Exception {
        FreightEntity freightEntity = super.getById(id);
        return DozerUtil.map(freightEntity, FreightDTO.class);
    }

    /**
     * 修改运费
     *
     * @param freightDTO
     * @throws Exception
     */
    @Override
    public void updateFreight(FreightDTO freightDTO) throws Exception {
        super.updateById(DozerUtil.map(freightDTO, FreightEntity.class));
    }
}
