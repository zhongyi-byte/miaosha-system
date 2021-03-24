package com.zhongyi.seckill.controller;


import com.zhongyi.seckill.entity.SkUser;
import com.zhongyi.seckill.result.Result;
import com.zhongyi.seckill.service.SkUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zhongyi
 * @since 2021-03-20
 */
@RestController
@RequestMapping("/sk-user")
public class SkUserController {

    @Autowired
    SkUserService userService;

    

    @RequestMapping("/info")
    
    public Result<SkUser> info(SkUser user){
        return Result.success(user);
    }
}
