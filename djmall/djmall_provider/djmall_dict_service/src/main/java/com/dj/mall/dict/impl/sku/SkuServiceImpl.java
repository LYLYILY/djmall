package com.dj.mall.dict.impl.sku;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dj.mall.common.util.DozerUtil;
import com.dj.mall.dict.api.sku.SkuService;
import com.dj.mall.dict.dto.attr.AttrValueDTO;
import com.dj.mall.dict.dto.sku.SkuDTO;
import com.dj.mall.dict.entity.sku.SkuEntity;
import com.dj.mall.dict.mapper.bo.attr.AttrValueBO;
import com.dj.mall.dict.mapper.bo.sku.SkuBO;
import com.dj.mall.dict.mapper.sku.SkuMapper;

import java.util.ArrayList;
import java.util.List;

@Service
public class SkuServiceImpl extends ServiceImpl<SkuMapper, SkuEntity> implements SkuService {

    /**
     * 通用sku展示
     *
     * @return
     * @throws Exception
     */
    @Override
    public List<SkuDTO> findSkuAll() throws Exception {
        List<SkuBO> skuList = super.baseMapper.findSkuAll();
        return DozerUtil.mapList(skuList, SkuDTO.class);
    }

    /**
     * sku关联资源
     */
    @Override
    public List<SkuDTO> showRelevance() throws Exception {
        List<SkuBO> skuList = super.baseMapper.showRelevance();
        return DozerUtil.mapList(skuList, SkuDTO.class);
    }

    /**
     * 关联属性保存
     *
     * @param ids
     * @param productType
     * @throws Exception
     */
    @Override
    public void add(Integer[] ids, String productType) throws Exception {
        //先删除
        QueryWrapper<SkuEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("product_type", productType);
        List<SkuEntity> list = super.list(queryWrapper);
        if (list.size() != 0) {
            List<Integer> attrIds = new ArrayList<>();
            list.forEach(a -> {
                attrIds.add(a.getId());
            });
            super.removeByIds(attrIds);
        }
        //新增
        List<SkuEntity> skuEntityList = new ArrayList<>();
        for (Integer id : ids) {
            SkuEntity skuEntity = new SkuEntity();
            skuEntity.setAttrId(id);
            skuEntity.setProductType(productType);
            skuEntityList.add(skuEntity);
        }
        super.saveBatch(skuEntityList);
    }

    /**
     * 根据商品类型查找属性值
     *
     * @param productType
     * @return
     */
    @Override
    public List<AttrValueDTO> findAttrValueByProductType(String productType) throws Exception {
        List<AttrValueBO> list = super.baseMapper.findAttrValueByProductType(productType);
        return DozerUtil.mapList(list, AttrValueDTO.class);
    }
}
