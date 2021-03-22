package com.zhongyi.seckill.service.impl;

import com.zhongyi.seckill.entity.SkGoods;
import com.zhongyi.seckill.entity.SkGoodsSeckill;
import com.zhongyi.seckill.entity.SkOrder;
import com.zhongyi.seckill.entity.SkOrderInfo;
import com.zhongyi.seckill.entity.SkUser;
import com.zhongyi.seckill.mapper.SkOrderInfoMapper;
import com.zhongyi.seckill.service.SkGoodsService;
import com.zhongyi.seckill.service.SkOrderInfoService;
import com.zhongyi.seckill.vo.GoodsVo;

import java.util.Date;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhongyi
 * @since 2021-03-21
 */
@Service
public class SkOrderInfoServiceImpl extends ServiceImpl<SkOrderInfoMapper, SkOrderInfo> implements SkOrderInfoService {
    @Autowired
    private SkGoodsSeckillServiceImpl seckillServiceImpl;

    @Autowired
    private SkOrderInfoMapper orderInfoMapper;
    @Autowired
    private SkOrderInfoService orderInfoService;

    @Autowired
    private SkOrderServiceImpl orderService;

    @Override
    @Transactional
    public SkOrderInfo seckill(SkUser user, GoodsVo goodsVo) {
       SkGoodsSeckill skGoods = seckillServiceImpl.getOne(new QueryWrapper<SkGoodsSeckill>().eq("goods_id", goodsVo.getId()));
        //减少库存
        skGoods.setStockCount(skGoods.getStockCount()-1);
        seckillServiceImpl.updateById(skGoods);

        //生成订单
        SkOrderInfo order = new SkOrderInfo();
        order.setUserId(user.getId());
        order.setGoodsId(goodsVo.getId());
        order.setDeliveryAddrId(0L);
        order.setGoodsName(goodsVo.getGoodsName());
        order.setGoodsCount(1);
        order.setOrderChannel(1);
        order.setStatus(0);
        order.setGoodsPrice(goodsVo.getSeckillPrice());
        // order.setCreateDate(new Date());
        // orderInfoMapper.insert(order);
        // orderInfoService.updateById(order);
        orderInfoService.save(order);
        //生成秒杀订单
        SkOrder seckillOrder = new SkOrder();
        seckillOrder.setGoodsId(goodsVo.getId());
        seckillOrder.setOrderId(order.getId());
        seckillOrder.setUserId(user.getId());
        orderService.save(seckillOrder);

        return order;
    }

}
