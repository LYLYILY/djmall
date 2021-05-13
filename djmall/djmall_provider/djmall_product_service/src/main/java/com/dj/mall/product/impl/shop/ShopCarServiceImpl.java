package com.dj.mall.product.impl.shop;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dj.mall.common.util.DozerUtil;
import com.dj.mall.product.api.product.ProductSkuService;
import com.dj.mall.product.api.shop.ShopCarService;
import com.dj.mall.product.dto.product.ProductSkuDTO;
import com.dj.mall.product.dto.shop.ShopCarDTO;
import com.dj.mall.product.entity.shop.ShopCarEntity;
import com.dj.mall.product.mapper.shop.ShopCarMapper;
import com.dj.mall.product.mapper.bo.ShopCarBO;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

@Service
public class ShopCarServiceImpl extends ServiceImpl<ShopCarMapper, ShopCarEntity> implements ShopCarService {

    @Autowired
    private ProductSkuService productSkuService;

    /**
     * 加入购物车
     * @param shopCarDTO
     * @throws Exception
     */
    @Override
    public void addShopCar(ShopCarDTO shopCarDTO) throws Exception {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("sku_id",shopCarDTO.getSkuId());
        queryWrapper.eq("user_id",shopCarDTO.getUserId());
        ShopCarEntity shopCar = super.getOne(queryWrapper);
        if(shopCar != null){
            shopCarDTO.setBuyNum(shopCarDTO.getBuyNum() + shopCar.getBuyNum());
            shopCarDTO.setId(shopCar.getId());
            super.updateById(DozerUtil.map(shopCarDTO, ShopCarEntity.class));
        }else {
            super.save(DozerUtil.map(shopCarDTO, ShopCarEntity.class));
        }
    }

    /**
     * 查看购物车
     */
    @Override
    public List<ShopCarDTO> show(Integer userId, Integer buyStatus) throws Exception {
        List<ShopCarBO> shopCarBOS = super.baseMapper.show(userId, buyStatus);
        return DozerUtil.mapList(shopCarBOS, ShopCarDTO.class);
    }

    /**
     * 查询数量
     *
     * @param shopCarDTO
     * @return
     * @throws Exception
     */
    @Override
    public ProductSkuDTO checkBuyNum(ShopCarDTO shopCarDTO) throws Exception {
        ProductSkuDTO product = productSkuService.findProductById(shopCarDTO.getSkuId());
        return product;
    }

    /**
     * 删除购物车
     *
     * @param ids
     */
    @Override
    public void delShopCar(Integer[] ids) throws Exception {
        List<Integer> id = new ArrayList<>();
        for (Integer idd : ids) {
            id.add(idd);
        }
        super.removeByIds(id);
    }

    /**
     * 查询购物车根据ids
     *
     * @param ids
     * @return
     */
    @Override
    public List<ShopCarDTO> findShopByIds(Integer[] ids) throws Exception {
        List<Integer> id = new ArrayList<>();
        for (Integer idd : ids) {
            id.add(idd);
        }
        List<ShopCarBO> list = super.baseMapper.findShopByIds(id);
        return DozerUtil.mapList(list, ShopCarDTO.class);
    }

    /**
     * 修改状态
     *
     * @param shopCarDTO
     * @throws Exception
     */
    @Override
    public void updateStatus(ShopCarDTO shopCarDTO) throws Exception {
        List<ShopCarEntity> list = new ArrayList<>();
        for (ShopCarDTO shopCar : shopCarDTO.getList()) {
            if (shopCar.getId() == null){
                continue;
            }
            ShopCarEntity shopCarEntity = new ShopCarEntity();
            shopCarEntity.setId(shopCar.getId());
            shopCarEntity.setBuyNum(shopCar.getBuyNum());
            shopCarEntity.setBuyStatus(2);
            list.add(shopCarEntity);
        }
        super.updateBatchById(list);
    }

    /**
     * 提交订单后删除购物车
     *
     * @param ids
     * @throws Exception
     */
    @Override
    public void delShopCarByIds(List<Integer> ids) throws Exception {
        QueryWrapper<ShopCarEntity> queryWrapper = new QueryWrapper();
        queryWrapper.in("sku_id", ids);
        queryWrapper.eq("buy_status", 2);
        super.remove(queryWrapper);
    }
}
