package com.dj.mall.platform.web.user;


import com.alibaba.dubbo.config.annotation.Reference;
import com.dj.mall.common.constant.ProductConstant;
import com.dj.mall.common.util.PasswordSecurityUtil;
import com.dj.mall.dict.api.data.DictDataService;
import com.dj.mall.dict.dto.data.DictDataDTO;
import com.dj.mall.user.api.UserService;
import com.dj.mall.user.dto.UserDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/userToken/")
public class UserTokenPageController {

    @Reference
    private DictDataService dictDataService;

    @Reference
    private UserService userService;

    /**
     *  用户登陆
     */
    @RequestMapping("toLoginToken")
    public String toLoginToken(ModelMap map) throws Exception {
        map.put("salt", PasswordSecurityUtil.generateSalt());
        return "/user/login";
    }

    /**
     * 用户注册
     * @param map
     * @return
     * @throws Exception
     */
    @GetMapping("toAdd")
    public String toAdd(ModelMap map) throws Exception {
        List<DictDataDTO> sexList = dictDataService.findDictNameByParentCode("SEX");
        map.put("sexList", sexList);
        map.put("salt", PasswordSecurityUtil.generateSalt());
        return "/user/add";
    }

    /**
     * 个人中心
     * @param userId
     * @return
     * @throws Exception
     */
    @GetMapping("toPersonalInformation/{userId}")
    public String toPersonalInformation(@PathVariable Integer userId, ModelMap map) throws Exception {
        UserDTO userDTO = userService.findUserByUserId(userId);
        List<DictDataDTO> sexList = dictDataService.findDictNameByParentCode("SEX");
        map.put("sexList", sexList);
        map.put("user", userDTO);
        return "/user/information";
    }

    /**
     * 收货地址
     * @return
     * @throws Exception
     */
    @GetMapping("toShippingAddress")
    public String toShippingAddress() throws Exception {
        return "/user/shop_address";
    }
}
