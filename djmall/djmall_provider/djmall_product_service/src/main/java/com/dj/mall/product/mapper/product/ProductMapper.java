package com.dj.mall.product.mapper.product;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dj.mall.product.entity.product.ProductEntity;
import com.dj.mall.product.mapper.bo.ProductBO;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

public interface ProductMapper extends BaseMapper<ProductEntity> {

    IPage<ProductBO> findProductAll(Page<Object> objectPage, @Param("product") ProductBO productBO);

    ProductBO findProductEs(Integer id) throws DataAccessException;
}
