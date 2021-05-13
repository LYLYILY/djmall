package com.dj.mall.product.mapper.product;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dj.mall.product.entity.product.ProductSkuEntity;
import com.dj.mall.product.mapper.bo.ProductSkuBO;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;


public interface ProductSkuMapper extends BaseMapper<ProductSkuEntity> {

    IPage<ProductSkuBO> findProductSkuAll(Page<Object> objectPage, @Param("product") ProductSkuBO productSkuBO) throws DataAccessException;

    ProductSkuBO findProductBySkuName(Integer id) throws DataAccessException;
}