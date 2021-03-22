package com.zhongyi.seckill.service.impl;

import com.zhongyi.seckill.entity.SkGoods;
import com.zhongyi.seckill.mapper.SkGoodsMapper;
import com.zhongyi.seckill.service.SkGoodsService;
import com.zhongyi.seckill.vo.GoodsVo;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhongyi
 * @since 2021-03-21
 */
@Service
public class SkGoodsServiceImpl extends ServiceImpl<SkGoodsMapper, SkGoods> implements SkGoodsService {

    @Autowired
    SkGoodsMapper goodsMapper;
    @Override
    public List<GoodsVo> findGoodsVo() {
        return goodsMapper.findGoodsVo();
    }
    @Override
    public GoodsVo findGoodsVoByGoodsId(long goodsId) {
        return goodsMapper.findGoodsVoByGoodsId(goodsId);
    }

}
