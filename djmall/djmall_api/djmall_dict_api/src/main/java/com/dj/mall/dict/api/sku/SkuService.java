package com.dj.mall.dict.api.sku;

import com.dj.mall.dict.dto.attr.AttrValueDTO;
import com.dj.mall.dict.dto.sku.SkuDTO;

import java.util.List;

public interface SkuService {

    /**
     * 通用sku展示
     * @return
     * @throws Exception
     */
    List<SkuDTO> findSkuAll() throws Exception;

    /**
     * sku关联资源
     * @return
     */
    List<SkuDTO> showRelevance() throws Exception;

    /**
     * 关联属性保存
     * @param ids
     * @param productType
     * @throws Exception
     */
    void add(Integer[] ids, String productType) throws Exception;

    /**
     * 根据商品类型查找属性值
     * @param productType
     * @return
     */
    List<AttrValueDTO> findAttrValueByProductType(String productType) throws Exception;
}
