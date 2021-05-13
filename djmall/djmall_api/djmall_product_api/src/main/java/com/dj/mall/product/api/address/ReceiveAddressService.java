package com.dj.mall.product.api.address;

import com.dj.mall.product.dto.address.ReceiveAddressDTO;

import java.util.List;

public interface ReceiveAddressService {

    /**
     * 收货地址展示
     * @param userId
     * @return
     */
    List<ReceiveAddressDTO> showReceiveAddressByUserId(Integer userId) throws Exception;

    /**
     * 新增收货地址
     * @param receiveAddressDTO
     * @throws Exception
     */
    void addAddress(ReceiveAddressDTO receiveAddressDTO) throws Exception;

    /**
     * 修改回显
     * @param id
     * @return
     * @throws Exception
     */
    ReceiveAddressDTO findReceiveAddrById(Integer id) throws Exception;

    /**
     * 修改收货地址
     * @param receiveAddressDTO
     * @throws Exception
     */
    void updateAddress(ReceiveAddressDTO receiveAddressDTO) throws Exception;

    /**
     * 收货地址删除
     * @param id
     */
    void delAddressByUserId(Integer id) throws Exception;

    /**
     * 根据userId查询收获地址
     * @param userId
     * @return
     * @throws Exception
     */
    List<ReceiveAddressDTO> findAddressByUserId(Integer userId) throws Exception;
}
