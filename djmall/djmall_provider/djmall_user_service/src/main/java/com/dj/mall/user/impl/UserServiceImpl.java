package com.dj.mall.user.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dj.mall.cmpt.api.EMailApi;
import com.dj.mall.common.constant.CacheKeyConsant;
import com.dj.mall.common.util.PasswordSecurityUtil;
import com.dj.mall.user.dto.MenuDTO;
import com.dj.mall.user.dto.UserTokenDTO;
import com.dj.mall.user.entity.UserRoleEntity;
import com.dj.mall.common.base.BusinessException;
import com.dj.mall.common.util.DozerUtil;
import com.dj.mall.user.api.UserService;
import com.dj.mall.user.dto.UserDTO;
import com.dj.mall.user.entity.UserEntity;
import com.dj.mall.user.mapper.UserMapper;
import com.dj.mall.user.mapper.bo.MenuBO;
import com.dj.mall.user.mapper.bo.UserBO;
import com.dj.mall.user.service.user.UserRoleService;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserEntity> implements UserService {

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 登录
     * @param userDTO
     * @return
     * @throws Exception
     */
    @Override
    public UserDTO findByUserNameAndUserPwd(UserDTO userDTO) throws BusinessException, Exception {
        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name", userDTO.getQueryName())
                .or().eq("user_phone", userDTO.getQueryName())
                .or().eq("user_email", userDTO.getQueryName());
        UserEntity userEntity = super.baseMapper.selectOne(queryWrapper);
        UpdateWrapper<UserEntity> updateWrapper = new UpdateWrapper();
        updateWrapper.set("last_login_time", LocalDateTime.now());
        updateWrapper.eq("id", userEntity.getId());
        super.update(updateWrapper);
        if (userEntity.getUserStatus().equals("CHONGZHI")) {
            throw new BusinessException(-4, "该账户已被重置");
        }
        if (userEntity == null) {
            throw new BusinessException("用户名输入错误");
        }
        if (!userEntity.getUserPwd().equals(userDTO.getUserPwd())) {
            throw new BusinessException("密码输入错误");
        }
        if (!userEntity.getUserStatus().equals("NORMAL")) {
            throw new BusinessException("该账户已被锁定.请联系管理员激活");
        }
        UserDTO userDTO1 = DozerUtil.map(userEntity, UserDTO.class);
        /*userDTO1.setResourceList(this.getLeft(userEntity.getId()));*/
        QueryWrapper<UserRoleEntity> queryWrapper1 = new QueryWrapper();
        queryWrapper1.eq("user_id", userDTO1.getId());
        UserRoleEntity roleIds = userRoleService.getOne(queryWrapper1);
        userDTO1.setRoleId(roleIds.getRoleId());
        return userDTO1;
    }

    /**
     * 注册
     * @param userDTO
     * @throws Exception
     */
    @Override
    public void add(UserDTO userDTO) throws Exception {
        if (!userDTO.getUserRank().equals("1")) {
            userDTO.setUserStatus("NONACTIVATE");
        }
        userDTO.setCreateTime(LocalDateTime.now());
        UserEntity userEntity = DozerUtil.map(userDTO, UserEntity.class);
        super.save(userEntity);
        UserRoleEntity userRoleEntity = new UserRoleEntity();
        userRoleEntity.setUserId(userEntity.getId());
        userRoleEntity.setRoleId(Integer.parseInt(userDTO.getUserRank()));
        userRoleService.save(userRoleEntity);

        if (userDTO.getUserStatus().equals("NONACTIVATE")) {
            JSONObject message = new JSONObject();
            message.put("email", userDTO.getUserEmail());
            message.put("userName", userEntity.getUserName());
            message.put("userId", userEntity.getId());
            rabbitTemplate.convertAndSend("direct", "directQueue1", message.toJSONString());
        }
    }

    /**
     * 用户注册-用户名查重
     * @param userName
     * @return
     * @throws Exception
     */
    @Override
    public boolean findUserName(String userName) throws Exception {
        QueryWrapper<UserEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("user_name", userName);
        UserEntity userEntity = super.getOne(wrapper);
        if (null != userEntity) {
            return false;
        }
        return true;
    }

    /**
     * 用户注册-邮箱查重
     * @param userEmail
     * @return
     * @throws Exception
     */
    @Override
    public boolean findUserEmail(String userEmail) throws Exception {
        QueryWrapper<UserEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("user_email", userEmail);
        UserEntity userEntity = super.getOne(wrapper);
        if (null != userEntity) {
            return false;
        }
        return true;
    }

    /**
     * 用户注册-手机号查重
     * @param userPhone
     * @return
     * @throws Exception
     */
    @Override
    public boolean findUserPhone(String userPhone) throws Exception {
        QueryWrapper<UserEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("user_phone", userPhone);
        UserEntity userEntity = super.getOne(wrapper);
        if (null != userEntity) {
            return false;
        }
        return true;
    }

    /**
     * 用户管理展示
     * @param userDTO
     * @return
     * @throws Exception
     */
    @Override
    public List<UserDTO> findUserAll(UserDTO userDTO) throws Exception {
        List<UserBO> userBOList = super.baseMapper.findUserAll(DozerUtil.map(userDTO, UserBO.class));
        return DozerUtil.mapList(userBOList, UserDTO.class);
    }

    /**
     * 用户授权
     * @param userDTO
     * @throws Exception
     */
    @Override
    public void insertUserRole(UserDTO userDTO) throws Exception {
        QueryWrapper<UserRoleEntity> queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_id", userDTO.getId());
        UserRoleEntity one = userRoleService.getOne(queryWrapper);
        /*if (null == one){
            userRoleService.save(UserRoleEntity.builder()
                    .userId(userDTO.getId())
                    .roleId(userDTO.getRoleId())
                    .build()
            );
        } else {*/
        if(one != null) {
            one.setRoleId(userDTO.getRoleId());
            userRoleService.update(one, queryWrapper);
        }

        /*}*/
    }

    /**
     * 根据用户id查找角色
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public Integer findRoleByUserId(Integer id) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_id", id);
        UserRoleEntity one = userRoleService.getOne(queryWrapper);
        return one.getRoleId();
    }

    /**
     * 修改用户名查重
     * @param userId
     * @return
     * @throws Exception
     */
    @Override
    public UserDTO findUserByUserId(Integer userId) {
        UserEntity userEntity = super.getById(userId);
        return DozerUtil.map(userEntity, UserDTO.class);
    }

    /**
     * 用户修改
     * @param userDTO
     * @throws Exception
     */
    @Override
    public void updateUserById(UserDTO userDTO) {
        super.updateById(DozerUtil.map(userDTO, UserEntity.class));
    }

    /**
     * 修改查重
     * @param userId
     * @param userName
     * @return
     */
    @Override
    public boolean findUserByName(Integer userId, String userName) {
        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name", userName);
        UserEntity userEntity = super.getOne(queryWrapper);
        if (null != userEntity && !userEntity.getId().equals(userId)) {
            return false;
        }
        return true;
    }

    /**
     * 修改查重
     * @param userId
     * @param userPhone
     * @return
     */
    @Override
    public boolean findUserByPhone(Integer userId, String userPhone) {
        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_phone", userPhone);
        UserEntity userEntity = super.getOne(queryWrapper);
        if (null != userEntity && !userEntity.getId().equals(userId)) {
            return false;
        }
        return true;
    }

    /**
     * 修改查重
     * @param userId
     * @param userEmail
     * @return
     */
    @Override
    public boolean findUserByEmail(Integer userId, String userEmail) {
        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_email", userEmail);
        UserEntity userEntity = super.getOne(queryWrapper);
        if (null != userEntity && !userEntity.getId().equals(userId)) {
            return false;
        }
        return true;
    }

    /**
     * 获取左侧菜单
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public List<MenuDTO> getLeft(Integer id) throws Exception {
        List<MenuBO> resourceBOS = baseMapper.getLeft(id);
        return DozerUtil.mapList(resourceBOS, MenuDTO.class);
    }

    /**
     * 获取盐
     * @param queryName
     * @return
     * @throws BusinessException
     */
    @Override
    public String findSaltByQueryName(String queryName) throws BusinessException {
        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_name", queryName)
                .or().eq("user_phone", queryName)
                .or().eq("user_email", queryName);
        UserEntity salt = super.getOne(queryWrapper);
        if (salt == null) {
            throw new BusinessException("此用户不存在");
        }
        return salt.getSalt();
    }

    /**
     * 激活用户
     *
     * @param userDTO
     * @throws Exception
     */
    @Override
    public void updateUserStatusById(UserDTO userDTO) throws BusinessException {
        QueryWrapper<UserEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("id", userDTO.getId());
        UserEntity userEntity = super.getOne(wrapper);
        if (userEntity.getUserStatus().equals("NORMAL")) {
            throw new BusinessException("已是激活状态");
        }
        userEntity.setUserStatus("NORMAL");
        super.saveOrUpdate(userEntity);
    }

    /**
     * 商城用户登录
     *
     * @param queryName
     * @param userPwd
     * @return
     */
    @Override
    public UserTokenDTO findByUserNameAndUserPwdToken(String queryName, String userPwd) throws Exception {
        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_name", queryName)
                .or().eq("user_phone", queryName)
                .or().eq("user_email", queryName);
        UserEntity userEntity = super.baseMapper.selectOne(queryWrapper);
        if (userEntity == null) {
            throw new BusinessException("用户名输入错误");
        }
        if (!userPwd.equals(userEntity.getUserPwd())) {
            throw new BusinessException("密码输入错误");
        }
        String token = UUID.randomUUID().toString().replace("-", "");
        redisTemplate.opsForValue().set(CacheKeyConsant.USER_TOKEN + token, DozerUtil.map(userEntity, UserDTO.class), 22 * 24 * 60 * 60);
        UserTokenDTO userTokenDTO = new UserTokenDTO();
        userTokenDTO.setToken(token);
        userTokenDTO.setNickName(userEntity.getNikeName());
        userTokenDTO.setQueryName(queryName);
        userTokenDTO.setUserId(userEntity.getId());
        return userTokenDTO;
    }

    /**
     * 邮箱激活
     *
     * @param userId
     * @throws Exception
     */
    @Override
    public void updateUserStatus1(Integer userId) throws Exception {
        UpdateWrapper<UserEntity> queryWrapper = new UpdateWrapper<>();
        queryWrapper.eq("id", userId);
        queryWrapper.set("user_status", "NORMAL");
        super.update(queryWrapper);
    }

    /**
     * 重置随机六位密码-发邮件
     *
     * @param id
     * @throws Exception
     */
    @Override
    public void updatePwdById(Integer id) throws Exception {
        UserEntity userEntity = super.getById(id);
        String newPwd = PasswordSecurityUtil.generateRandom(6);
        String newSalt = PasswordSecurityUtil.generateSalt();
        String overPwd = PasswordSecurityUtil.enCode32(PasswordSecurityUtil.enCode32(newPwd) + newSalt);

        UpdateWrapper<UserEntity> queryWrapper = new UpdateWrapper<>();
        queryWrapper.eq("id", id);
        queryWrapper.set("user_status", "CHONGZHI");
        queryWrapper.set("user_pwd", overPwd);
        super.update(queryWrapper);
        JSONObject message = new JSONObject();
        message.put("email", userEntity.getUserEmail());
        message.put("userPwd", newPwd);
        rabbitTemplate.convertAndSend("direct", "directQueue2", message.toJSONString());

    }

    /**
     * 修改密码
     *
     * @param userDTO
     */
    @Override
    public void restUserPwd(UserDTO userDTO) throws Exception {
        UpdateWrapper<UserEntity> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("user_name", userDTO.getQueryName())
                .or().eq("user_phone", userDTO.getUserPhone())
                .or().eq("user_email", userDTO.getUserEmail())
                .set("user_status", "NORMAL")
                .set("salt", userDTO.getSalt())
                .set("user_pwd", userDTO.getUserPwd());
        super.update(updateWrapper);
    }

    /**
     * 根据手机号修改密码
     * @param userDTO
     * @throws Exception
     */
    @Override
    public void updatePwdByPhone(UserDTO userDTO) throws Exception {
        UpdateWrapper<UserEntity> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("user_phone", userDTO.getUserPhone());
        updateWrapper.set("user_pwd", userDTO.getUserPwd()).set("salt", userDTO.getSalt());
        super.update(updateWrapper);
    }

    /**
     * 手机号登录
     *
     * @param userPhone
     * @throws Exception
     */
    @Override
    public UserTokenDTO phoneLogin(String userPhone) throws Exception {
        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_phone", userPhone);
        UserEntity userEntity = super.baseMapper.selectOne(queryWrapper);
        UpdateWrapper<UserEntity> updateWrapper = new UpdateWrapper();
        updateWrapper.set("last_login_time", LocalDateTime.now());
        updateWrapper.eq("id", userEntity.getId());
        super.update(updateWrapper);
        if (userEntity.getUserStatus().equals("CHONGZHI")) {
            throw new BusinessException(-4, "该账户已被重置");
        }
        if (userEntity == null) {
            throw new BusinessException("用户名输入错误");
        }
        if (!userEntity.getUserStatus().equals("NORMAL")) {
            throw new BusinessException("该账户已被锁定.请联系管理员激活");
        }
        String token = UUID.randomUUID().toString().replace("-", "");
        redisTemplate.opsForValue().set(CacheKeyConsant.USER_TOKEN + token, DozerUtil.map(userEntity, UserDTO.class), 22 * 24 * 60 * 60);
        UserTokenDTO userTokenDTO = new UserTokenDTO();
        userTokenDTO.setToken(token);
        userTokenDTO.setNickName(userEntity.getNikeName());
        userTokenDTO.setUserId(userEntity.getId());
        return userTokenDTO;
    }

    /**
     * 商城用户注册
     *
     * @param userDTO
     * @throws Exception
     */
    @Override
    public void addRegister(UserDTO userDTO) throws Exception {
        userDTO.setCreateTime(LocalDateTime.now());
        if("".equals(userDTO.getNikeName())){
            userDTO.setNikeName("DJ"+PasswordSecurityUtil.generateRandom(6));
        }
        UserEntity userEntity = DozerUtil.map(userDTO, UserEntity.class);
        super.save(userEntity);
        UserRoleEntity userRoleEntity = new UserRoleEntity();
        userRoleEntity.setUserId(userEntity.getId());
        userRoleEntity.setRoleId(Integer.parseInt(userDTO.getUserRank()));
        userRoleService.save(userRoleEntity);
    }

    /**
     * 用户修改-昵称是否与用户名重复
     *
     * @param nikeName
     * @param userId
     * @return
     */
    @Override
    public boolean findUserNikeName(String nikeName, Integer userId) throws Exception {
        QueryWrapper<UserEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("nike_name", nikeName);
        UserEntity nikeName1 = super.getOne(wrapper);
        UserEntity userName = super.getById(userId);
        if (!userName.equals(nikeName1)) {
            return true;
        }
        return false;
    }

    /**
     * 个人中心修改
     *
     * @param userDTO
     * @throws Exception
     */
    @Override
    public void update(UserDTO userDTO) throws Exception {
        super.updateById(DozerUtil.map(userDTO, UserEntity.class));
    }
}
