package com.zhongyi.seckill.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhongyi.seckill.entity.SkGoodsSeckill;
import com.zhongyi.seckill.entity.SkOrder;
import com.zhongyi.seckill.entity.SkOrderInfo;
import com.zhongyi.seckill.entity.SkUser;
import com.zhongyi.seckill.exception.GlobalException;
import com.zhongyi.seckill.exception.GlobalExceptionHandler;
import com.zhongyi.seckill.result.CodeMsg;
import com.zhongyi.seckill.result.Result;
import com.zhongyi.seckill.service.SkGoodsSeckillService;
import com.zhongyi.seckill.service.SkGoodsService;
import com.zhongyi.seckill.service.SkOrderInfoService;
import com.zhongyi.seckill.service.SkOrderService;
import com.zhongyi.seckill.vo.GoodsVo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.slf4j.Slf4j;

@RequestMapping("/seckill")
@Controller
@Slf4j
public class SeckillController {
    @Autowired
    SkGoodsService goodsService;

    @Autowired
    SkOrderService SkorderService;

    @Autowired
    SkOrderInfoService orderService;

    @Autowired
    SkGoodsSeckillService skGoodsService;

    @RequestMapping("/doSeckill")
    public String doSeckill(Model model,SkUser user,@RequestParam("goodsId") long goodsId){
        if(user == null){
            return "login";
        }
        model.addAttribute("user", user);
        GoodsVo goodsVo = goodsService.findGoodsVoByGoodsId(goodsId);
        //判断库存是否充足
        if(goodsVo.getStockCount() < 1){
            model.addAttribute("errmsg", CodeMsg.SECKILL_OVER);
            return "seckillFail";
            // throw new GlobalException(CodeMsg.SECKILL_OVER);
        }

        //判断是否重复抢购
        SkOrder  order = SkorderService.getOne(new QueryWrapper<SkOrder>().eq("user_id", user.getId()).eq("goods_id", goodsId));
        if(order != null){
            // throw new GlobalException(CodeMsg.REPEATE_SECKILL);
            model.addAttribute("errmsg", CodeMsg.REPEATE_SECKILL);
            return "seckillFail";
        }

        SkOrderInfo orderInfo = orderService.seckill(user,goodsVo);
        model.addAttribute("orderInfo", orderInfo);
        model.addAttribute("goods", goodsVo);
        SkGoodsSeckill skGoods = skGoodsService.getOne(new QueryWrapper<SkGoodsSeckill>().eq("goods_id", goodsVo.getId()));
        log.info("SeckillGoodsPrice:" + skGoods.getSeckillPrice());
        log.info("GoodsPrice:" + goodsVo.getGoodsPrice());
        return "orderDetail";
    }
}
