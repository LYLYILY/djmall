package com.dj.mall.platform.web.shop;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dj.mall.common.base.ResultModel;
import com.dj.mall.common.util.DozerUtil;
import com.dj.mall.platform.vo.shop.shop.ShopCarVOReq;
import com.dj.mall.platform.vo.shop.shop.ShopCarVOResp;
import com.dj.mall.product.api.shop.ShopCarService;
import com.dj.mall.product.dto.product.ProductSkuDTO;
import com.dj.mall.product.dto.shop.ShopCarDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/shop/")
public class ShopController {

    @Reference
    private ShopCarService shopCarService;

    /**
     * 添加到购物车
     * @param shopCarVOReq
     * @return
     * @throws Exception
     */
    @PostMapping("addShopCar")
    public ResultModel addShopCar(ShopCarVOReq shopCarVOReq) throws Exception {
        shopCarService.addShopCar(DozerUtil.map(shopCarVOReq, ShopCarDTO.class));
        return new ResultModel().success();
    }

    /**
     * 购物车展示
     * @param userId
     * @param buyStatus
     * @return
     * @throws Exception
     */
    @PostMapping("show")
    public ResultModel show(Integer userId, Integer buyStatus) throws Exception {
        List<ShopCarDTO> shopCarDTOList = shopCarService.show(userId, buyStatus);
        return new ResultModel().success(DozerUtil.mapList(shopCarDTOList, ShopCarVOResp.class));
    }

    /**
     * 查询数量
     * @param shopCarVOReq
     * @return
     * @throws Exception
     */
    @PostMapping("checkNum")
    public ResultModel checkNum(ShopCarVOReq shopCarVOReq) throws Exception {
        ProductSkuDTO product = shopCarService.checkBuyNum(DozerUtil.map(shopCarVOReq, ShopCarDTO.class));
        return new ResultModel<>().success(product);
    }

    /**
     *  删除购物车
     */
    @PostMapping("delShopCar")
    public ResultModel delShopCar(@RequestParam("ids[]") Integer[] ids) throws Exception {
        shopCarService.delShopCar(ids);
        return new ResultModel<>().success();
    }

    /**
     * 查询购物车根据ids
     * @param ids
     * @return
     * @throws Exception
     */
    @PostMapping("findShopByIds")
    public ResultModel findShopByIds(@RequestParam("ids[]") Integer[] ids) throws Exception {
        List<ShopCarDTO> price = shopCarService.findShopByIds(ids);
        return new ResultModel().success(price);
    }

    /**
     * 修改状态
     * @param shopCarVOReq
     * @return
     * @throws Exception
     */
    @PostMapping("updateStatus")
    public ResultModel updateStatus(ShopCarVOReq shopCarVOReq) throws Exception {
        shopCarService.updateStatus(DozerUtil.map(shopCarVOReq,ShopCarDTO.class));
        return new ResultModel<>().success();
    }

}
