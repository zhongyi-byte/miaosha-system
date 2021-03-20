package com.zhongyi.seckill.controller;


import javax.servlet.http.HttpSession;

import com.zhongyi.seckill.entity.SkUser;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/goods")
public class GoodsController {
    
    @RequestMapping("/toList")
    public String toList(HttpSession session,Model model,@CookieValue(value ="userTicket",required = false) String ticket){
        if(ticket == null){
        return "login";
        }

        SkUser user = (SkUser)session.getAttribute(ticket);
        if(user == null){
            return "login";
        }
        model.addAttribute("user", user);
        return "goodsList";
    }
}
