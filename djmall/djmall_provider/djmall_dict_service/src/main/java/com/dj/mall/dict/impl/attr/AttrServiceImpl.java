package com.dj.mall.dict.impl.attr;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dj.mall.common.base.BusinessException;
import com.dj.mall.common.util.DozerUtil;
import com.dj.mall.dict.api.attr.AttrService;
import com.dj.mall.dict.service.AttrValueService;
import com.dj.mall.dict.dto.attr.AttrDTO;
import com.dj.mall.dict.dto.attr.AttrValueDTO;
import com.dj.mall.dict.entity.attr.AttrEntity;
import com.dj.mall.dict.entity.attr.AttrValueEntity;
import com.dj.mall.dict.mapper.attr.AttrMapper;
import com.dj.mall.dict.mapper.bo.attr.AttrBO;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class AttrServiceImpl extends ServiceImpl<AttrMapper, AttrEntity> implements AttrService {

    @Autowired
    private AttrValueService attrValueService;

    /**
     * 商品属性维护展示
     *
     * @return
     * @throws Exception
     */
    @Override
    public List<AttrDTO> findAttrAll() throws Exception {
        List<AttrBO> list = super.baseMapper.findAttrAll();
        return DozerUtil.mapList(list, AttrDTO.class);
    }

    /**
     * 商品属性维护新增
     *
     * @param attrDTO
     * @throws Exception
     * @throws BusinessException
     */
    @Override
    public void add(AttrDTO attrDTO) throws Exception, BusinessException {
        AttrEntity attrEntity = DozerUtil.map(attrDTO, AttrEntity.class);
        super.save(attrEntity);
    }

    /**
     * 查询商品属性名是否重复
     *
     * @param attrName
     * @return
     * @throws Exception
     */
    @Override
    public boolean findAttrName(String attrName) throws Exception {
        QueryWrapper<AttrEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("attr_name", attrName);
        AttrEntity dictAttrEntity = super.getOne(wrapper);
        if (null != dictAttrEntity) {
            return false;
        }
        return true;
    }

    /**
     * 根据id查询属性
     * @param id
     * @return
     */
    @Override
    public AttrDTO findAttrById(Integer id) {
        AttrEntity attrEntities = super.getById(id);
        return DozerUtil.map(attrEntities, AttrDTO.class);
    }

    /**
     * 查询attr关联属性
     *
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public List<AttrValueDTO> findAttrValueById(Integer id) throws Exception {
        QueryWrapper<AttrValueEntity> Wrapper = new QueryWrapper<>();
        Wrapper.eq("attr_id", id);
        List<AttrValueEntity> attrEntities = attrValueService.list(Wrapper);
        return DozerUtil.mapList(attrEntities, AttrValueDTO.class);
    }

    /**
     * 新增商品属性值查重
     *
     * @param attrValue
     * @return
     */
    @Override
    public boolean findAttrValueName(String attrValue) throws Exception {
        QueryWrapper<AttrValueEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("attr_value", attrValue);
        AttrValueEntity attrValueEntity = attrValueService.getOne(wrapper);
        if (null != attrValueEntity) {
            return false;
        }
        return true;
    }

    /**
     * 商品属性新增
     *
     * @param attrValueDTO
     * @throws Exception
     */
    @Override
    public void insert(AttrValueDTO attrValueDTO) throws Exception {
        AttrValueEntity attrValueEntity = DozerUtil.map(attrValueDTO, AttrValueEntity.class);
        attrValueService.save(attrValueEntity);
    }

    /**
     * 删除attr关联属性
     *
     * @param id
     * @throws Exception
     */
    @Override
    public void delAttrValueById(Integer id) throws Exception {
        attrValueService.removeById(id);
    }
}
