package com.zhongyi.seckill.service;

import com.zhongyi.seckill.entity.SkUser;
import com.zhongyi.seckill.vo.LoginVo;

import javax.servlet.http.HttpServletResponse;

import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhongyi
 * @since 2021-03-20
 */
public interface SkUserService extends IService<SkUser> {

    String doLogin(HttpServletResponse response,LoginVo loginVo);


}
