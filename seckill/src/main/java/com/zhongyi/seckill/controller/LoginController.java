package com.zhongyi.seckill.controller;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.zhongyi.seckill.result.Result;
import com.zhongyi.seckill.service.SkUserService;
import com.zhongyi.seckill.vo.LoginVo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/login")
@Slf4j
public class LoginController {

  
    @Autowired
    SkUserService userService;


    @RequestMapping("/toLogin")
    public String toLogin() {
        return "login";
    }

    @RequestMapping("/doLogin")
    @ResponseBody
    public Result<String> doLogin(HttpServletResponse response,@Valid LoginVo loginVo) {//加入JSR303参数校验
        log.info(loginVo.toString());
        String token = userService.doLogin(response, loginVo);
        return Result.success(token);
    }
}
