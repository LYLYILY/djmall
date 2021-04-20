package com.dj.mall.admin.web.auth.user;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dj.mall.admin.vo.auth.user.UserVOReq;

import com.dj.mall.admin.vo.auth.user.UserVOResp;
import com.dj.mall.auth.api.resource.ResourceService;
import com.dj.mall.common.base.BusinessException;
import com.dj.mall.common.base.ResultModel;
import com.dj.mall.common.constant.UserConstant;
import com.dj.mall.common.util.DozerUtil;
import com.dj.mall.user.api.UserService;
import com.dj.mall.user.dto.UserDTO;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
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
        return new ResultModel().success();
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
    @PostMapping("updateStatus")
    public ResultModel<Object> updateStatus(UserVOReq userVOReq) throws Exception {
        userService.updateUserStatusById(DozerUtil.map(userVOReq, UserDTO.class));
        return new ResultModel<>().success();
    }
}
