package com.zhongyi.seckill.controller;


import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.druid.sql.ast.statement.SQLIfStatement.Else;
import com.zhongyi.seckill.entity.SkUser;
import com.zhongyi.seckill.service.SkGoodsService;
import com.zhongyi.seckill.service.SkUserService;
import com.zhongyi.seckill.vo.GoodsVo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/goods")
public class GoodsController {
    @Autowired
    SkUserService userService;
    @Autowired
    SkGoodsService goodsService;
    @RequestMapping("/toList")
    public String toList(Model model,SkUser user){
        // if(user == null){
        //     return "login";
        // }
        // if(ticket == null){
        // return "login";
        // }

        // SkUser user = userService.getUserByCookie(ticket, request, response);
        // if(user == null){
        //     return "login";
        // }
        model.addAttribute("user", user);
        model.addAttribute("goodsList", goodsService.findGoodsVo());
        return "goodsList";
    }

    //跳转商品详情页
    @RequestMapping("/toDetail/{goodsId}")
    public String toDetail(Model model,SkUser user,@PathVariable long goodsId){
        if(user == null){
            return "login";
        }

        model.addAttribute("user", user);
        model.addAttribute("goods", goodsService.findGoodsVoByGoodsId(goodsId));
        GoodsVo goodsVo = goodsService.findGoodsVoByGoodsId(goodsId);


        Date startDate = goodsVo.getStartDate();
        Date endDate = goodsVo.getEndDate();
        Date nowDate = new Date();
        //秒杀倒计时
        int remainSeconds = 0;
        //秒杀状态
        int seckillStatus = 2;
        //秒杀还未开始
        if(nowDate.before(startDate)){
            seckillStatus = 0;
            remainSeconds = (int)(startDate.getTime() - nowDate.getTime())/1000  ;
        }
        else if(nowDate.after(endDate)){
            //秒杀已经结束
            seckillStatus = 2;
            remainSeconds = -1;
        }
        else{
            //秒杀进行中
            seckillStatus = 1;
            remainSeconds = 0;
        }
        model.addAttribute("remainSeconds", remainSeconds);
        model.addAttribute("seckillStatus", seckillStatus);
        model.addAttribute("nowDate", nowDate);
        return "goodsDetail";
    }
}
