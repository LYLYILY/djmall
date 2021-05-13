package com.dj.mall.platform.web.user;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.exceptions.ClientException;
import com.dj.mall.common.base.BusinessException;
import com.dj.mall.common.base.ResultModel;
import com.dj.mall.common.constant.UserConstant;
import com.dj.mall.common.util.*;
import com.dj.mall.platform.vo.user.UserTokenVOReq;
import com.dj.mall.platform.vo.user.UserTokenVOResp;
import com.dj.mall.product.dto.product.ProductDTO;
import com.dj.mall.user.api.UserService;
import com.dj.mall.user.dto.UserDTO;
import com.dj.mall.user.dto.UserTokenDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@RestController
@RequestMapping("/userToken/")
public class UserTokenController {

    @Reference
    private UserService userService;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 商城登录
     * @param queryName
     * @param userPwd
     * @return
     * @throws Exception
     */
    @PostMapping("loginToken")
    public ResultModel findByUserNameAndUserPwdToken(String queryName, String userPwd) throws Exception {
        Assert.hasText(queryName, "用户名为空");
        Assert.hasText(userPwd, "密码为空");
        UserTokenDTO userTokenDTO = userService.findByUserNameAndUserPwdToken(queryName, userPwd);
        return new ResultModel().success(DozerUtil.map(userTokenDTO, UserTokenVOResp.class));
    }

    /**
     * 获取盐
     * @param queryName
     * @return
     * @throws BusinessException
     */
    @PostMapping("getSalt")
    public ResultModel getSalt(String queryName) throws BusinessException {
        String salt = userService.findSaltByQueryName(queryName);
        return new ResultModel().success(salt);
    }

    /**
     * 获取图形验证码
     * @param request
     * @param response
     */
    @RequestMapping("getVerifCode")
    public void getVerifCode(HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Expires", "-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("image/jpeg");
        try {
            String verifyCode = VerifyCodeUtil.generateTextCode(VerifyCodeUtil.TYPE_ALL_MIXED, 4, null);
            request.getSession().setAttribute("PHONE_LOGIN_CODE", verifyCode);
            //设置输出的内容的类型为JPEG图像
            BufferedImage bufferedImage = VerifyCodeUtil.generateImageCode(verifyCode, 90, 30, 3, true, Color.WHITE, Color.BLACK, null);
            //写给浏览器
            ImageIO.write(bufferedImage, "JPEG", response.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param userPhone 手机号
     * @param verifyCode 用户输入的图形验证码
     * @param checkVerifyCode 代比对的验证码
     * @return
     */
    @PostMapping("getCode")
    public ResultModel getCode(String userPhone, String verifyCode, @SessionAttribute("PHONE_LOGIN_CODE") String checkVerifyCode) throws ClientException {
        Assert.hasText(userPhone, "手机号不能为空");
        Assert.hasText(verifyCode, "图形验证码不能为空");
        //图形验证码有效性，忽略大小写
        Assert.state(verifyCode.equalsIgnoreCase(checkVerifyCode), "图形验证码不匹配");
        //验证短信验证码是否合规，同一个业务类型，同一手机号，一天x次
        HashOperations hashOperations = redisTemplate.opsForHash();
        String key = UserConstant.PHONE_SMS + LocalDate.now();
        String valueStr = (String) hashOperations.get(key, userPhone);
        String code = PasswordSecurityUtil.generateRandom(6);
        if (!StringUtils.hasText(valueStr)) {
            JSONObject obj = new JSONObject();
            obj.put("code", code);
            obj.put("timeout", LocalDateTime.now().plusSeconds(60l));
            obj.put("count", 1);
            hashOperations.put(key, userPhone, obj.toJSONString());
        }else {
            JSONObject obj = JSONObject.parseObject(valueStr);
            if (obj.getIntValue("count") < 3) {
                obj.put("code", code);
                obj.put("timeout", LocalDateTime.now().plusSeconds(60l));
                obj.put("count", obj.getIntValue("count") + 1);
                hashOperations.put(key, userPhone, obj.toJSONString());
            }else {
                throw new BusinessException("今日已达上限");
            }
        }
        //调用短信接口
        AliyunSendSms.sendSms(userPhone,code);
        return new ResultModel().success();
    }


    /**
     * 手机号登录
     * @param userPhone
     * @param smsCode
     * @return
     * @throws Exception
     */
    @PostMapping("phoneLogin")
    public ResultModel updatePwd(String smsCode, String userPhone) throws Exception {
        Assert.hasText("user_phone", "手机号不能为空");
        Assert.hasText(smsCode, "手机验证码不能为空");
        //短信验证码是否有效
        HashOperations hashOperations = redisTemplate.opsForHash();
        String key = UserConstant.PHONE_SMS + LocalDate.now();
        String valueStr = (String) hashOperations.get(key, userPhone);
        UserTokenDTO userDTO;
        if (StringUtils.hasText(valueStr)) {
            JSONObject obj = JSONObject.parseObject(valueStr);
            if (obj.getDate("timeout").getTime() > new Date().getTime()) {
                if (obj.getString("code").equals(smsCode)) {
                    //进行手机号登录
                     userDTO = userService.phoneLogin(userPhone);
                }else {
                    throw new BusinessException("验证码错误");
                }
            }else {
                throw new BusinessException("该验证码已失效");
            }
        }else {
            throw new BusinessException("请先发送验证码");
        }
        return new ResultModel().success(DozerUtil.map(userDTO, UserTokenVOResp.class));
    }

    /**
     *  注册
     */
    @RequestMapping("addRegister")
    public ResultModel addRegister(UserTokenVOReq userVOReq) throws Exception{
        userService.addRegister(DozerUtil.map(userVOReq, UserDTO.class));
        return new ResultModel().success();
    }

    /**
     * 用户注册-用户名查重
     */
    @RequestMapping("checkUserName")
    public boolean checkUsername(String userName) throws Exception {
        return userService.findUserName(userName);
    }


    /**
     * 用户注册-邮箱查重
     */
    @RequestMapping("checkUserEmail")
    public boolean checkUserEmail(String userEmail) throws Exception {
        return userService.findUserEmail(userEmail);
    }

    /**
     * 用户注册-手机号查重
     */
    @RequestMapping("checkUserPhone")
    public boolean checkUserPhone(String userPhone) throws Exception {
        return userService.findUserPhone(userPhone);
    }

    /**
     * 用户修改-昵称是否与用户名重复
     * @param nikeName
     * @param userId
     * @return
     * @throws Exception
     */
    @PostMapping("checkUserNikeName/{userId}")
    public boolean checkUserNikeName(String nikeName, @PathVariable Integer userId) throws Exception {
        return userService.findUserNikeName(nikeName, userId);
    }

    /**
     * 个人中心修改
     * @param userTokenVOReq
     * @param file
     * @param id
     * @return
     * @throws Exception
     */
    @PostMapping("update")
    public ResultModel update(UserTokenVOReq userTokenVOReq, MultipartFile file, Integer id) throws Exception {
        Assert.hasText(userTokenVOReq.getNikeName(), "昵称不能为空");
        Assert.hasText(userTokenVOReq.getUserSex(), "性别不能为空");
        Assert.hasText(userTokenVOReq.getUserEmail(), "邮箱不能为空");
        String firstName = UUID.randomUUID().toString().replace("-", "");
        if(!file.isEmpty()){
            String lastName = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
            QiNuiUtil.delFile(userTokenVOReq.getUserPortrait());
            userTokenVOReq.setUserPortrait("http://qs0q2d1mv.hn-bkt.clouddn.com/" + firstName + lastName);
            QiNuiUtil.uploadFile(file.getBytes(), firstName + lastName);
        }
        userTokenVOReq.setId(id);
        userService.update(DozerUtil.map(userTokenVOReq, UserDTO.class));
        return new ResultModel().success();
    }


}
