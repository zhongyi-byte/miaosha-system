package com.zhongyi.seckill.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.zhongyi.seckill.entity.SkUser;
import com.zhongyi.seckill.service.SkUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/goods")
public class GoodsController {
    @Autowired
    SkUserService userService;

    @RequestMapping("/toList")
    public String toList(Model model,SkUser user){
        // if(ticket == null){
        // return "login";
        // }

        // SkUser user = userService.getUserByCookie(ticket, request, response);
        // if(user == null){
        //     return "login";
        // }
        model.addAttribute("user", user);
        return "goodsList";
    }
}
