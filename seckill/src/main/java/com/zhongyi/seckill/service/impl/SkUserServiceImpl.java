package com.zhongyi.seckill.service.impl;

import com.zhongyi.seckill.entity.SkUser;
import com.zhongyi.seckill.exception.GlobalException;
import com.zhongyi.seckill.mapper.SkUserMapper;
import com.zhongyi.seckill.result.CodeMsg;
import com.zhongyi.seckill.service.SkUserService;
import com.zhongyi.seckill.utils.MD5Util;
import com.zhongyi.seckill.utils.MobileValidator;
import com.zhongyi.seckill.vo.LoginVo;

import javax.servlet.http.HttpServletResponse;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhongyi
 * @since 2021-03-20
 */
@Service
public class SkUserServiceImpl extends ServiceImpl<SkUserMapper, SkUser> implements SkUserService {
    
    @Autowired
    SkUserMapper userMapper;

    public String doLogin(HttpServletResponse response, LoginVo loginVo) {
        if (loginVo == null) {
            throw new GlobalException(CodeMsg.SERVER_ERROR);
        }
        String mobile = loginVo.getMobile();
        if(!MobileValidator.isMobile(mobile)){
            throw new GlobalException(CodeMsg.MOBILE_ERROR);
        }
        String formPass = loginVo.getPassword();
        //判断手机号是否存在
        SkUser user = userMapper.selectById(mobile);
        if (user == null) {
            throw new GlobalException(CodeMsg.MOBILE_NOT_EXIST);
        }
        //验证密码
        String dbPass = user.getPassword();
        String saltDB = user.getSalt();
        String calcPass = MD5Util.formPassToDBPass(formPass, saltDB);
        if (!calcPass.equals(dbPass)) {
            throw new GlobalException(CodeMsg.PASSWORD_ERROR);
        }

        return "12312";
        //生成唯一id作为token
        // String token = UUIDUtil.uuid();
        // addCookie(response, token, user);
        // return token;
    }
}
