package com.dj.mall.dict.mapper.sku;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dj.mall.dict.entity.sku.SkuEntity;
import com.dj.mall.dict.mapper.bo.attr.AttrValueBO;
import com.dj.mall.dict.mapper.bo.sku.SkuBO;
import org.springframework.dao.DataAccessException;

import javax.xml.crypto.Data;
import java.util.List;

public interface SkuMapper extends BaseMapper<SkuEntity> {

    /**
     * sku展示
     * @return
     */
    List<SkuBO> findSkuAll() throws DataAccessException;

    /**
     * sku关联资源
     * @return
     * @throws DataAccessException
     */
    List<SkuBO> showRelevance() throws DataAccessException;

    /**
     * 根据商品类型查找属性值
     * @param productType
     * @return
     * @throws DataAccessException
     */
    List<AttrValueBO> findAttrValueByProductType(String productType) throws DataAccessException;
}
