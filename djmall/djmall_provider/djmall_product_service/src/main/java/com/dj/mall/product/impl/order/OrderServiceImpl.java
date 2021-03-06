package com.dj.mall.product.impl.order;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dj.mall.common.base.PageResult;
import com.dj.mall.common.util.DozerUtil;
import com.dj.mall.common.util.PasswordSecurityUtil;
import com.dj.mall.product.api.address.ReceiveAddressService;
import com.dj.mall.product.api.order.OrderDetailService;
import com.dj.mall.product.api.order.OrderInfoService;
import com.dj.mall.product.api.order.OrderService;
import com.dj.mall.product.api.product.ProductSkuService;
import com.dj.mall.product.api.shop.ShopCarService;
import com.dj.mall.product.dto.address.ReceiveAddressDTO;
import com.dj.mall.product.dto.order.OrderDTO;
import com.dj.mall.product.dto.order.OrderDetailDTO;
import com.dj.mall.product.dto.order.OrderInfoDTO;
import com.dj.mall.product.dto.product.ProductSkuDTO;
import com.dj.mall.product.entity.order.OrderDetailEntity;
import com.dj.mall.product.entity.order.OrderEntity;
import com.dj.mall.product.entity.order.OrderInfoEntity;
import com.dj.mall.product.mapper.order.OrderMapper;
import com.dj.mall.product.mapper.bo.OrderBO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderServiceImpl extends ServiceImpl<OrderMapper, OrderEntity> implements OrderService {

    @Reference
    private ReceiveAddressService receiveAddressService;

    @Reference
    private ProductSkuService productSkuService;

    @Reference
    private OrderInfoService orderInfoService;

    @Reference
    private OrderDetailService orderDetailService;

    @Reference
    private ShopCarService shopCarService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * ????????????
     *
     * @param orderDTO
     * @throws Exception
     */
    @Override
    public void addOrder(OrderDTO orderDTO) throws Exception {
        //1.???????????????
        log.info("???????????????-start");
        //????????????id????????????
        ReceiveAddressDTO address = receiveAddressService.findReceiveAddrById(orderDTO.getReceiveId());
        //?????????
        orderDTO.setReceiverProvince(address.getUserProvince());
        //?????????
        orderDTO.setReceiverCity(address.getUserCity());
        //?????????
        orderDTO.setReceiverCounty(address.getUserDistrict());
        //????????????-????????????
        orderDTO.setReceiverDetail(address.getAddress());
        //???????????????
        orderDTO.setReceiverName(address.getAddressee());
        //????????????????????????
        orderDTO.setReceiverPhone(address.getPhone());
        //??????????????????-s
        String s = "DJ" + System.currentTimeMillis() + PasswordSecurityUtil.generateRandom(3);
        orderDTO.setOrderNo(s);
        //??????????????????
        orderDTO.setCreateTime(LocalDateTime.now());
        //??????????????????
        orderDTO.setPayTime(LocalDateTime.now());
        super.save(DozerUtil.map(orderDTO, OrderEntity.class));
        log.info("???????????????-end");
        //2.???????????????

        List<Integer> ids = new ArrayList<>();
        for (ProductSkuDTO productSkuDTO:orderDTO.getList()) {
            if (productSkuDTO.getId() == null) {
                continue;
            }
            ids.add(productSkuDTO.getId());
        }
        List<OrderInfoEntity> orderInfo = new ArrayList<>();
        List<OrderDetailEntity> orderDetail = new ArrayList<>();
        //??????ids????????????sku
        List<ProductSkuDTO> productSku = productSkuService.findProductSkuByIds(ids);
        List<ProductSkuDTO> list = new ArrayList<>();
        Map<Integer, List<ProductSkuDTO>> collect = productSku.stream().collect(Collectors.groupingBy(ProductSkuDTO::getProductId));
        for (Integer integer : collect.keySet()){
            //????????????-s2
            List<ProductSkuDTO> list1= new ArrayList<>();
            String s2 = "DJ" + System.currentTimeMillis() + PasswordSecurityUtil.generateRandom(3);
            for (ProductSkuDTO p: productSku){
                if(integer.equals(p.getProductId())){
                    list1.add(p);
                }
            }
            OrderInfoEntity orderInfoEntity = new OrderInfoEntity();
            //??????????????????
            orderInfoEntity.setOrderNo(s2);
            //???????????????
            orderInfoEntity.setParentOrderNo(s);
            //???????????????id
            orderInfoEntity.setBuyerId(orderDTO.getBuyerId());
            //????????????id
            orderInfoEntity.setBusinessId(list1.get(0).getBusinessId());
            orderInfoEntity.setProductId(list1.get(0).getProductId());
            //??????????????????
            orderInfoEntity.setPayType(orderDTO.getPayType());
            //??????????????????
            orderInfoEntity.setOrderStatus(orderDTO.getOrderStatus());
            //?????????????????????
            orderInfoEntity.setTotalMoney(new BigDecimal(20));
            //?????????????????????
            orderInfoEntity.setTotalPayMoney(new BigDecimal(200));
            //???????????????
            BigDecimal bigDecimal = orderDTO.getTotalFreight();
            log.info("??????" + bigDecimal);
            orderInfoEntity.setTotalFreight(bigDecimal);
            //?????????????????????
            orderInfoEntity.setTotalBuyCount(20);
            //?????????
            orderInfoEntity.setReceiverProvince(address.getUserProvince());
            //?????????
            orderInfoEntity.setReceiverCity(address.getUserCity());
            //?????????
            orderInfoEntity.setReceiverCounty(address.getUserDistrict());
            //????????????-????????????
            orderInfoEntity.setReceiverDetail(address.getAddress());
            //?????????
            orderInfoEntity.setReceiverName(address.getAddressee());
            //????????????????????????
            orderInfoEntity.setReceiverPhone(address.getPhone());
            //??????????????????
            orderInfoEntity.setCreateTime(LocalDateTime.now());
            //??????????????????
            orderInfoEntity.setPayTime(LocalDateTime.now());
            orderInfo.add(orderInfoEntity);

            for (ProductSkuDTO productSkuDTO:productSku) {
                //?????????
                ProductSkuDTO productSkuEntity = new ProductSkuDTO();
                productSkuEntity.setId(productSkuDTO.getId());
                productSkuEntity.setSkuPrice(productSkuDTO.getSkuPrice());
                productSkuEntity.setSkuCount(productSkuDTO.getSkuCount() - orderDTO.getList().get(productSkuDTO.getId()).getBuyCount());
                list.add(productSkuEntity);
            }

            for (ProductSkuDTO productSkuDTO : list1){
                OrderDetailEntity orderDetailEntity = new OrderDetailEntity();
                //????????????
                orderDetailEntity.setParentOrderNo(s);
                //????????????
                orderDetailEntity.setChildOrderNo(s2);
                //??????ID
                orderDetailEntity.setBuyerId(orderDTO.getBuyerId());
                //??????ID
                orderDetailEntity.setProductId(productSkuDTO.getProductId());
                //??????ID
                orderDetailEntity.setBusinessId(productSkuDTO.getBusinessId());
                //SKUID
                orderDetailEntity.setSkuId(productSkuDTO.getId());
                //????????????
                orderDetailEntity.setBuyCount(orderDTO.getList().get(productSkuDTO.getId()).getBuyCount());
                //SKU??????
                orderDetailEntity.setSkuInfo(productSkuDTO.getSkuName());
                //SKU??????
                orderDetailEntity.setSkuPrice(productSkuDTO.getSkuPrice());
                //SKU??????
                orderDetailEntity.setSkuRate(productSkuDTO.getSkuRate());
                //????????????
                orderDetailEntity.setPayMoney(orderDTO.getList().get(productSkuDTO.getId()).getTotalPayMoney());
                //??????
                orderDetailEntity.setFreightPrice(orderDTO.getList().get(productSkuDTO.getId()).getFreight());
                //????????????
                orderDetailEntity.setCreateTime(LocalDateTime.now());
                orderDetail.add(orderDetailEntity);

            }

        }
        log.info("???????????????-start");
        orderInfoService.addOrderInfo(DozerUtil.mapList(orderInfo, OrderInfoDTO.class));
        log.info("???????????????-end");
        log.info("??????????????????-start");
        orderDetailService.addDetail(DozerUtil.mapList(orderDetail, OrderDetailDTO.class));
        log.info("??????????????????-end");
        //????????????
        productSkuService.updateSkuCountByIds(list);
        //???????????????
        shopCarService.delShopCarByIds(ids);

        JSONObject message1 = new JSONObject();
        message1.put("orderNo", s);
        // ???????????? ???????????????
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setDelay(60000);
        Message message = new Message(message1.toJSONString().getBytes(), messageProperties);
        rabbitTemplate.convertAndSend("delay-exchange", "delay-queue", message);
    }

    /**
     * ????????????
     *
     * @param pageNo
     * @param orderStatus
     * @param userId
     * @return
     */
    @Override
    public PageResult list(Integer pageNo, String orderStatus, Integer userId) throws Exception {
        IPage<OrderBO> iPage = super.baseMapper.findOrderAll(new Page<>(pageNo, 5),orderStatus,userId);
        return PageResult.pageInfo(iPage.getCurrent(),iPage.getPages(), iPage.getRecords());
    }

    /**
     * ?????????
     *
     * @param orderNo
     */
    @Override
    public void updateOrderStatus(String orderNo) throws Exception {
        UpdateWrapper updateWrapper = new UpdateWrapper();
        updateWrapper.eq("order_no", orderNo);
        updateWrapper.set("order_status", "ORDER_WAIT_RECE");
        updateWrapper.set("update_time", LocalDateTime.now());
        orderInfoService.updateOrderStatus(orderNo);
        super.update(updateWrapper);
    }

    /**
     * ????????????
     *
     * @param orderDTO
     * @throws Exception
     */
    @Override
    public void quxiao(OrderDTO orderDTO) throws Exception {
        List<OrderDetailDTO> list= orderDetailService.findAll(orderDTO);
        List<Integer> ids = list.stream().map(OrderDetailDTO::getSkuId).collect(Collectors.toList());
        List<ProductSkuDTO> productSku = productSkuService.findProductSkuByIds(ids);
        List<ProductSkuDTO> list2 = new ArrayList<>();
        for (ProductSkuDTO productSkuDTO:productSku) {
            for (OrderDetailDTO orderDetailEntity : list) {
                ProductSkuDTO productSkuEntity = new ProductSkuDTO();
                productSkuEntity.setId(productSkuDTO.getId());
                productSkuEntity.setSkuPrice(productSkuDTO.getSkuPrice());
                productSkuEntity.setSkuCount(productSkuDTO.getSkuCount() + orderDetailEntity.getBuyCount());
                list2.add(productSkuEntity);
            }
        }
        //????????????????????????
        UpdateWrapper updateWrapper = new UpdateWrapper();
        updateWrapper.eq("order_no", orderDTO.getOrderNo());
        updateWrapper.set("order_status", orderDTO.getOrderStatus());
        updateWrapper.set("update_time", LocalDateTime.now());
        super.update(updateWrapper);
        //???????????????????????????
        orderInfoService.updateOrderStatus2(orderDTO);
        //????????????
        productSkuService.updateSkuCountByIds(list2);

    }

    /**
     * ??????????????????
     *
     * @param orderNo
     * @return
     */
    @Override
    public OrderDTO showDetails2(String orderNo) throws Exception {
        OrderBO orderBO = super.baseMapper.showDetails2(orderNo);
        return DozerUtil.map(orderBO, OrderDTO.class);
    }

    /**
     * ???????????????????????????
     *
     * @param orderNo
     * @return
     */
    @Override
    public OrderDTO findStatusByOrderNo(String orderNo) throws Exception {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("order_no", orderNo);
        OrderEntity one = super.getOne(queryWrapper);
        return DozerUtil.map(one, OrderDTO.class);
    }

    @Override
    public void wancheng(LocalDate time) throws Exception {
        String status = "ORDER_COMPLETED";
        super.baseMapper.updateStatus(time, status);
        orderInfoService.updateStatus(time, status);
    }
}
