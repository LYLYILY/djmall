package com.dj.mall.product.mapper.shop;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dj.mall.product.entity.shop.ShopCarEntity;
import com.dj.mall.product.mapper.bo.ShopCarBO;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import java.util.List;

public interface ShopCarMapper extends BaseMapper<ShopCarEntity> {

    List<ShopCarBO> show(@Param("userId") Integer userId, @Param("buyStatus") Integer buyStatus) throws DataAccessException;

    List<ShopCarBO> findShopByIds(List<Integer> id) throws DataAccessException;
}
