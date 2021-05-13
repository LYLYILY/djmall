package com.dj.mall.product.entity.address;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("djmall_user_receive_address")
public class ReceiveAddressEntity implements Serializable {

    /**
     *  收货地址id
     */
    @TableId(type = IdType.AUTO)
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
