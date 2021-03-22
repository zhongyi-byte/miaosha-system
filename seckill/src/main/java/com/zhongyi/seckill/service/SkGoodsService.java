package com.zhongyi.seckill.service;

import com.zhongyi.seckill.entity.SkGoods;
import com.zhongyi.seckill.vo.GoodsVo;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhongyi
 * @since 2021-03-21
 */
public interface SkGoodsService extends IService<SkGoods> {

    List<GoodsVo> findGoodsVo() ;

    GoodsVo findGoodsVoByGoodsId(long goodsId);

}
