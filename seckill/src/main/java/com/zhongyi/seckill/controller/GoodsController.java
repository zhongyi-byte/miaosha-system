package com.zhongyi.seckill.controller;


import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.druid.pool.ha.selector.StickyRandomDataSourceSelector;
import com.alibaba.druid.sql.ast.statement.SQLIfStatement.Else;
import com.zhongyi.seckill.entity.SkUser;
import com.zhongyi.seckill.result.Result;
import com.zhongyi.seckill.service.SkGoodsService;
import com.zhongyi.seckill.service.SkUserService;
import com.zhongyi.seckill.vo.GoodsVo;
import com.zhongyi.seckill.vo.goodsDetailVo;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;


@RestController
@RequestMapping("/goods")
public class GoodsController {
    @Autowired
    SkUserService userService;
    @Autowired
    SkGoodsService goodsService;
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    ThymeleafViewResolver thymleafViewResolver;

    //未加入redis缓存时,最大qps约为2000
    //加入redis后,qps接近9000

    @RequestMapping(value = "/toList",produces = "text/html;charset = utf-8")
    // @ResponseBody
    public String toList(Model model,SkUser user,HttpServletRequest request,HttpServletResponse response){
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
        
        //Redis 中获取页面,如果为不为空,直接返回页面
        ValueOperations valueOperations = redisTemplate.opsForValue();
        String html = (String)valueOperations.get("goods_List");
        if(!StringUtils.isEmpty(html)){
            return html;
        }

        //如果为空,手动渲染,存入redis并返回

        model.addAttribute("user", user);
        model.addAttribute("goodsList", goodsService.findGoodsVo());

        WebContext webContext = new WebContext(request, response, request.getServletContext(),request.getLocale(),
        model.asMap());
        html = thymleafViewResolver.getTemplateEngine().process("goodsList", webContext);
        if(!StringUtils.isEmpty(html)){
            valueOperations.set("goods_List", html,60,TimeUnit.SECONDS);
        }
        return html;

    }


    //跳转商品详情页
    @RequestMapping(value = "/toDetail2/{goodsId}",produces = "text/html;charset = utf-8")
    // @ResponseBody
    public String toDetail(Model model,SkUser user,@PathVariable long goodsId,HttpServletRequest request,
    HttpServletResponse response){
        if(user == null){
            return "login";
        }
        ValueOperations valueOperations = redisTemplate.opsForValue();
        String html = (String)valueOperations.get("goodsDetail:" + goodsId);
        if(!StringUtils.isEmpty(html)){
            return html;
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

        WebContext webContext = new WebContext(request, response, request.getServletContext(),request.getLocale(),
        model.asMap());
        html = thymleafViewResolver.getTemplateEngine().process("goodsDetail", webContext);
        if(!StringUtils.isEmpty(html)){
            valueOperations.set("goodsDetail:"+ goodsId, html,60,TimeUnit.SECONDS);
        }
        return html;
    }



    @RequestMapping("/toDetail/{goodsId}")
    @ResponseBody
    public Result<goodsDetailVo> toDetail(Model model,SkUser user,@PathVariable long goodsId){
       
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

        goodsDetailVo goodsDetailVo = new goodsDetailVo();
        goodsDetailVo.setGoods(goodsVo);
        goodsDetailVo.setUser(user);
        goodsDetailVo.setSeckillStatus(seckillStatus);
        goodsDetailVo.setRemainSeconds(remainSeconds);
        return Result.success(goodsDetailVo);
    }
}
