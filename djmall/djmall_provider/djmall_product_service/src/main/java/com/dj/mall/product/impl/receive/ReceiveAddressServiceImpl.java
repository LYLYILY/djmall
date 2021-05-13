package com.dj.mall.product.impl.receive;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dj.mall.common.util.DozerUtil;
import com.dj.mall.product.api.address.ReceiveAddressService;
import com.dj.mall.product.dto.address.ReceiveAddressDTO;
import com.dj.mall.product.entity.address.ReceiveAddressEntity;
import com.dj.mall.product.mapper.address.ReceiveAddressMapper;

import java.util.List;

@Service
public class ReceiveAddressServiceImpl extends ServiceImpl<ReceiveAddressMapper, ReceiveAddressEntity> implements ReceiveAddressService {

    /**
     * 收货地址展示
     *
     * @param userId
     * @return
     */
    @Override
    public List<ReceiveAddressDTO> showReceiveAddressByUserId(Integer userId) throws Exception {
        List<ReceiveAddressEntity> list = super.list();
        return DozerUtil.mapList(list, ReceiveAddressDTO.class);
    }

    /**
     * 新增收货地址
     *
     * @param receiveAddressDTO
     * @throws Exception
     */
    @Override
    public void addAddress(ReceiveAddressDTO receiveAddressDTO) throws Exception {
        super.save(DozerUtil.map(receiveAddressDTO, ReceiveAddressEntity.class));
    }

    /**
     * 修改回显
     *
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public ReceiveAddressDTO findReceiveAddrById(Integer id) throws Exception {
        ReceiveAddressEntity byId = super.getById(id);
        return DozerUtil.map(byId, ReceiveAddressDTO.class);
    }

    /**
     * 修改收货地址
     *
     * @param receiveAddressDTO
     * @throws Exception
     */
    @Override
    public void updateAddress(ReceiveAddressDTO receiveAddressDTO) throws Exception {
        super.updateById(DozerUtil.map(receiveAddressDTO, ReceiveAddressEntity.class));
    }

    /**
     * 收货地址删除
     *
     * @param id
     */
    @Override
    public void delAddressByUserId(Integer id) throws Exception {
        super.removeById(id);
    }

    /**
     * 根据userId查询收获地址
     * @param userId
     * @return
     * @throws Exception
     */
    @Override
    public List<ReceiveAddressDTO> findAddressByUserId(Integer userId) throws Exception {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_id", userId);
        List<ReceiveAddressEntity> list = super.list(queryWrapper);
        return DozerUtil.mapList(list, ReceiveAddressDTO.class);
    }
}
