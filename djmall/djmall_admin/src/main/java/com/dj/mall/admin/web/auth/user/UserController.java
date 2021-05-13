package com.dj.mall.admin.web.auth.user;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.exceptions.ClientException;
import com.dj.mall.admin.vo.auth.user.UserVOReq;

import com.dj.mall.admin.vo.auth.user.UserVOResp;
import com.dj.mall.auth.api.resource.ResourceService;
import com.dj.mall.common.base.BusinessException;
import com.dj.mall.common.base.ResultModel;
import com.dj.mall.common.constant.UserConstant;
import com.dj.mall.common.util.AliyunSendSms;
import com.dj.mall.common.util.DozerUtil;
import com.dj.mall.common.util.PasswordSecurityUtil;
import com.dj.mall.common.util.VerifyCodeUtil;
import com.dj.mall.user.api.UserService;
import com.dj.mall.user.dto.UserDTO;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/user/")
public class UserController {

    @Reference
    private UserService userService;

    @Reference
    private ResourceService resourceService;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 登录
     * @param userVOReq
     * @param session
     * @return
     * @throws Exception
     */
    @RequestMapping("login")
    public ResultModel findByUserNameAndUserPwd(UserVOReq userVOReq, HttpSession session) throws Exception, BusinessException {
        Assert.hasText(userVOReq.getQueryName(), "用户名为空");
        Assert.hasText(userVOReq.getUserPwd(), "密码为空");
        UserDTO user = userService.findByUserNameAndUserPwd(DozerUtil.map(userVOReq, UserDTO.class));
        session.setAttribute(UserConstant.USER, user);
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(userVOReq.getQueryName(), userVOReq.getUserPwd());
        subject.login(token);
        return new ResultModel().success(user);
    }

    /**
     * 注册
     * @param userVOReq
     * @return
     * @throws Exception
     */
    @RequestMapping("add")
    public ResultModel<Object> add(UserVOReq userVOReq) throws Exception {
        Assert.notNull(userVOReq, "用户信息不能为空");
        Assert.hasText(userVOReq.getUserName(), "用户名不能为空");
        Assert.hasText(userVOReq.getUserPwd(), "密码不能为空");
        Assert.state(userVOReq.getUserPwd().equals(userVOReq.getConfirmPassword()), "两次密码不一致");
        Assert.state(!userVOReq.getNikeName().equals(userVOReq.getUserName()), "昵称和用户名不能一致");
        Assert.hasText(userVOReq.getUserEmail(), "邮箱不能为空");
        Assert.hasText(userVOReq.getUserPhone(), "手机号不能为空");
        Assert.notNull(userVOReq.getUserSex(), "性别不能为空");
        Assert.notNull(userVOReq.getUserRank(), "级别不能为空");
        Assert.hasText(userVOReq.getNikeName(), "昵称不能为空");
        userService.add(DozerUtil.map(userVOReq, UserDTO.class));
        return new ResultModel<>().success();
    }

    /**
     * 用户注册-用户名查重
     * @param userName
     * @return
     * @throws Exception
     */
    @RequestMapping("checkUserName")
    public boolean checkUsername(String userName) throws Exception {
        return userService.findUserName(userName);
    }

    /**
     * 用户注册-邮箱查重
     * @param userEmail
     * @return
     * @throws Exception
     */
    @RequestMapping("checkUserEmail")
    public boolean checkUserEmail(String userEmail) throws Exception {
        return userService.findUserEmail(userEmail);
    }

    /**
     * 用户注册-手机号查重
     * @param userPhone
     * @return
     * @throws Exception
     */
    @RequestMapping("checkUserPhone")
    public boolean checkUserPhone(String userPhone) throws Exception {
        return userService.findUserPhone(userPhone);
    }

    /**
     * 用户管理展示
     * @param userVOReq
     * @return
     * @throws Exception
     */
    @RequiresPermissions(UserConstant.USER_MANAGER)
    @PostMapping("show")
    public ResultModel<Object> showUser(UserVOReq userVOReq) throws Exception {
        List<UserDTO> userDTOList = userService.findUserAll(DozerUtil.map(userVOReq, UserDTO.class));
        return new ResultModel<>().success(DozerUtil.mapList(userDTOList, UserVOResp.class));
    }

    /**
     * 用户授权
     * @param userVOReq
     * @return
     * @throws Exception
     */
    @RequiresPermissions(UserConstant.USER_PERMISSION_BTN)
    @PostMapping("insertUserRole")
    public ResultModel insertUserRole(UserVOReq userVOReq) throws Exception {
        userService.insertUserRole(DozerUtil.map(userVOReq, UserDTO.class));
        return new ResultModel().success();
    }

    /**
     * 用户修改
     * @param userVOReq
     * @return
     */
    @RequiresPermissions(UserConstant.USER_UPDATE_BTN)
    @PostMapping("update")
    public ResultModel update(UserVOReq userVOReq) throws Exception {
        UserDTO userDTO = DozerUtil.map(userVOReq, UserDTO.class);
        userService.updateUserById(userDTO);
        return new ResultModel<>().success();
    }

    /**
     * 修改用户名查重
     * @param userId
     * @param userName
     * @return
     * @throws Exception
     */
    @PostMapping("checkUserName/{userId}")
    public boolean checkUserName(@PathVariable Integer userId, String userName) throws Exception {
        return userService.findUserByName(userId, userName);
    }

    /**
     * 修改手机号重复
     * @param userId
     * @param userPhone
     * @return
     * @throws Exception
     */
    @PostMapping("checkUserPhone/{userId}")
    public boolean checkUserPhone(@PathVariable Integer userId, String userPhone) throws Exception {
        return userService.findUserByPhone(userId, userPhone);
    }

    /**
     * 修改邮箱重复
     * @param userId
     * @param userEmail
     * @return
     * @throws Exception
     */
    @PostMapping("checkUserEmail/{userId}")
    public boolean checkUserEmail(@PathVariable Integer userId, String userEmail) throws Exception {
        return userService.findUserByEmail(userId, userEmail);
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
     * 激活
     * @param userVOReq
     * @return
     * @throws Exception
     */
    @GetMapping("updateStatus")
    public ResultModel<Object> updateStatus(UserVOReq userVOReq) throws BusinessException {
        userService.updateUserStatusById(DozerUtil.map(userVOReq, UserDTO.class));
        return new ResultModel<>().success();
    }

    /**
     * 重置随机六位密码-发邮件
     * @param userVOReq
     * @return
     * @throws Exception
     */
    @PostMapping("newPwd")
    public ResultModel newPwd(UserVOReq userVOReq) throws Exception {
        userService.updatePwdById(userVOReq.getId());
        return new ResultModel<>().success();
    }

    /**
     * 重置密码
     * @param userVOReq
     * @return
     * @throws Exception
     */
    @PostMapping("restUserPwd")
    public ResultModel restUserPwd(UserVOReq userVOReq) throws Exception {
        userService.restUserPwd(DozerUtil.map(userVOReq, UserDTO.class));
        return new ResultModel().success();
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
            request.getSession().setAttribute(UserConstant.SESSION_VERIFY_CODE, verifyCode);
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
    public ResultModel getCode(String userPhone, String verifyCode, @SessionAttribute("SESSION_VERIFY_CODE") String checkVerifyCode) throws ClientException {
        Assert.hasText(userPhone, "手机号不能为空");
        Assert.hasText(verifyCode, "图形验证码不能为空");
        //图形验证码有效性，忽略大小写
        Assert.state(verifyCode.equalsIgnoreCase(checkVerifyCode), "图形验证码不匹配");
        //验证短信验证码是否合规，同一个业务类型，同一手机号，一天x次
        HashOperations hashOperations = redisTemplate.opsForHash();
        String key = UserConstant.SMS_FORGRTPWD_PRE + LocalDate.now();
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
            if (obj.getIntValue("count") < 5) {
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

    @PostMapping("updatePwd")
    public ResultModel updatePwd(UserVOReq userVOReq, String smsCode, String confirmPassword) throws Exception {
        Assert.hasText("user_phone", "手机号不能为空");
        Assert.hasText(smsCode, "手机验证码不能为空");
        Assert.state(userVOReq.getUserPwd().equals(confirmPassword), "两次密码不一致");
        //短信验证码是否有效
        HashOperations hashOperations = redisTemplate.opsForHash();
        String key = UserConstant.SMS_FORGRTPWD_PRE + LocalDate.now();
        String valueStr = (String) hashOperations.get(key, userVOReq.getUserPhone());
        if (StringUtils.hasText(valueStr)) {
            JSONObject obj = JSONObject.parseObject(valueStr);
            if (obj.getDate("timeout").getTime() > new Date().getTime()) {
                if (obj.getString("code").equals(smsCode)) {
                    //进行密码修改
                    userService.updatePwdByPhone(DozerUtil.map(userVOReq, UserDTO.class));
                }else {
                    throw new BusinessException("验证码错误");
                }
            }else {
                throw new BusinessException("该验证码已失效");
            }
        }else {
            throw new BusinessException("请先发送验证码");
        }
        return new ResultModel().success();
    }

}
