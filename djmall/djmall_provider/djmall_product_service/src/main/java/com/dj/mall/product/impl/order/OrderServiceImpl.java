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
     * 提交订单
     *
     * @param orderDTO
     * @throws Exception
     */
    @Override
    public void addOrder(OrderDTO orderDTO) throws Exception {
        //1.新增父订单
        log.info("新增父订单-start");
        //根据地址id查询数据
        ReceiveAddressDTO address = receiveAddressService.findReceiveAddrById(orderDTO.getReceiveId());
        //新增省
        orderDTO.setReceiverProvince(address.getUserProvince());
        //新增市
        orderDTO.setReceiverCity(address.getUserCity());
        //新增县
        orderDTO.setReceiverCounty(address.getUserDistrict());
        //收货信息-地址明细
        orderDTO.setReceiverDetail(address.getAddress());
        //新增收货人
        orderDTO.setReceiverName(address.getAddressee());
        //新增手收货人机号
        orderDTO.setReceiverPhone(address.getPhone());
        //生成父订单号-s
        String s = "DJ" + System.currentTimeMillis() + PasswordSecurityUtil.generateRandom(3);
        orderDTO.setOrderNo(s);
        //生成创建时间
        orderDTO.setCreateTime(LocalDateTime.now());
        //生成支付时间
        orderDTO.setPayTime(LocalDateTime.now());
        super.save(DozerUtil.map(orderDTO, OrderEntity.class));
        log.info("新增父订单-end");
        //2.新增子订单

        List<Integer> ids = new ArrayList<>();
        for (ProductSkuDTO productSkuDTO:orderDTO.getList()) {
            if (productSkuDTO.getId() == null) {
                continue;
            }
            ids.add(productSkuDTO.getId());
        }
        List<OrderInfoEntity> orderInfo = new ArrayList<>();
        List<OrderDetailEntity> orderDetail = new ArrayList<>();
        //根据ids查询商品sku
        List<ProductSkuDTO> productSku = productSkuService.findProductSkuByIds(ids);
        List<ProductSkuDTO> list = new ArrayList<>();
        Map<Integer, List<ProductSkuDTO>> collect = productSku.stream().collect(Collectors.groupingBy(ProductSkuDTO::getProductId));
        for (Integer integer : collect.keySet()){
            //子订单号-s2
            List<ProductSkuDTO> list1= new ArrayList<>();
            String s2 = "DJ" + System.currentTimeMillis() + PasswordSecurityUtil.generateRandom(3);
            for (ProductSkuDTO p: productSku){
                if(integer.equals(p.getProductId())){
                    list1.add(p);
                }
            }
            OrderInfoEntity orderInfoEntity = new OrderInfoEntity();
            //新增子订单号
            orderInfoEntity.setOrderNo(s2);
            //新增父单号
            orderInfoEntity.setParentOrderNo(s);
            //新增收货人id
            orderInfoEntity.setBuyerId(orderDTO.getBuyerId());
            //新增商家id
            orderInfoEntity.setBusinessId(list1.get(0).getBusinessId());
            orderInfoEntity.setProductId(list1.get(0).getProductId());
            //新增支付类型
            orderInfoEntity.setPayType(orderDTO.getPayType());
            //新增订单状态
            orderInfoEntity.setOrderStatus(orderDTO.getOrderStatus());
            //新增订单总金额
            orderInfoEntity.setTotalMoney(new BigDecimal(20));
            //新增实付总金额
            orderInfoEntity.setTotalPayMoney(new BigDecimal(200));
            //新增总运费
            BigDecimal bigDecimal = orderDTO.getTotalFreight();
            log.info("运费" + bigDecimal);
            orderInfoEntity.setTotalFreight(bigDecimal);
            //新增总购买数量
            orderInfoEntity.setTotalBuyCount(20);
            //新增省
            orderInfoEntity.setReceiverProvince(address.getUserProvince());
            //新增市
            orderInfoEntity.setReceiverCity(address.getUserCity());
            //新增县
            orderInfoEntity.setReceiverCounty(address.getUserDistrict());
            //收货信息-地址明细
            orderInfoEntity.setReceiverDetail(address.getAddress());
            //收货人
            orderInfoEntity.setReceiverName(address.getAddressee());
            //新增手收货人机号
            orderInfoEntity.setReceiverPhone(address.getPhone());
            //生成创建时间
            orderInfoEntity.setCreateTime(LocalDateTime.now());
            //生成支付时间
            orderInfoEntity.setPayTime(LocalDateTime.now());
            orderInfo.add(orderInfoEntity);

            for (ProductSkuDTO productSkuDTO:productSku) {
                //减库存
                ProductSkuDTO productSkuEntity = new ProductSkuDTO();
                productSkuEntity.setId(productSkuDTO.getId());
                productSkuEntity.setSkuPrice(productSkuDTO.getSkuPrice());
                productSkuEntity.setSkuCount(productSkuDTO.getSkuCount() - orderDTO.getList().get(productSkuDTO.getId()).getBuyCount());
                list.add(productSkuEntity);
            }

            for (ProductSkuDTO productSkuDTO : list1){
                OrderDetailEntity orderDetailEntity = new OrderDetailEntity();
                //父订单号
                orderDetailEntity.setParentOrderNo(s);
                //子订单号
                orderDetailEntity.setChildOrderNo(s2);
                //买家ID
                orderDetailEntity.setBuyerId(orderDTO.getBuyerId());
                //商品ID
                orderDetailEntity.setProductId(productSkuDTO.getProductId());
                //商户ID
                orderDetailEntity.setBusinessId(productSkuDTO.getBusinessId());
                //SKUID
                orderDetailEntity.setSkuId(productSkuDTO.getId());
                //购买数量
                orderDetailEntity.setBuyCount(orderDTO.getList().get(productSkuDTO.getId()).getBuyCount());
                //SKU信息
                orderDetailEntity.setSkuInfo(productSkuDTO.getSkuName());
                //SKU价格
                orderDetailEntity.setSkuPrice(productSkuDTO.getSkuPrice());
                //SKU折扣
                orderDetailEntity.setSkuRate(productSkuDTO.getSkuRate());
                //支付金额
                orderDetailEntity.setPayMoney(orderDTO.getList().get(productSkuDTO.getId()).getTotalPayMoney());
                //运费
                orderDetailEntity.setFreightPrice(orderDTO.getList().get(productSkuDTO.getId()).getFreight());
                //创建时间
                orderDetailEntity.setCreateTime(LocalDateTime.now());
                orderDetail.add(orderDetailEntity);

            }

        }
        log.info("新增子订单-start");
        orderInfoService.addOrderInfo(DozerUtil.mapList(orderInfo, OrderInfoDTO.class));
        log.info("新增子订单-end");
        log.info("新增订单详情-start");
        orderDetailService.addDetail(DozerUtil.mapList(orderDetail, OrderDetailDTO.class));
        log.info("新增订单详情-end");
        //修改库存
        productSkuService.updateSkuCountByIds(list);
        //删除购物车
        shopCarService.delShopCarByIds(ids);

        JSONObject message1 = new JSONObject();
        message1.put("orderNo", s);
        // 发送消息 向普通队列
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setDelay(60000);
        Message message = new Message(message1.toJSONString().getBytes(), messageProperties);
        rabbitTemplate.convertAndSend("delay-exchange", "delay-queue", message);
    }

    /**
     * 主表展示
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
     * 去支付
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
     * 取消订单
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
        //修改主表订单状态
        UpdateWrapper updateWrapper = new UpdateWrapper();
        updateWrapper.eq("order_no", orderDTO.getOrderNo());
        updateWrapper.set("order_status", orderDTO.getOrderStatus());
        updateWrapper.set("update_time", LocalDateTime.now());
        super.update(updateWrapper);
        //修改子表状态为取消
        orderInfoService.updateOrderStatus2(orderDTO);
        //增加库存
        productSkuService.updateSkuCountByIds(list2);

    }

    /**
     * 主表订单详情
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
     * 根据订单号查找状态
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
