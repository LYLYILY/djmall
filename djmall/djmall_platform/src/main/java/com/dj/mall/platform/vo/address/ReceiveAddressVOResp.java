package com.dj.mall.platform.vo.address;

import lombok.Data;

@Data
public class ReceiveAddressVOResp {

    /**
     *  收货地址id
     */
    private Integer id;

    /**
     *  用户id
     */
    private Integer userId;

    /**
     *  用户名
     */
    private String addressee;

    /**
     *  手机号
     */
    private String phone;

    /**
     *  省份
     */
    private String userProvince;

    /**
     *  城市
     */
    private String userCity;

    /**
     *  县城
     */
    private String userDistrict;

    /**
     * 详细地址
     */
    private String address;
}
