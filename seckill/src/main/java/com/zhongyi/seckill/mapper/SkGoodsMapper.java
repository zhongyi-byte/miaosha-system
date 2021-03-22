package com.zhongyi.seckill.mapper;

import com.zhongyi.seckill.entity.SkGoods;
import com.zhongyi.seckill.vo.GoodsVo;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zhongyi
 * @since 2021-03-21
 */
public interface SkGoodsMapper extends BaseMapper<SkGoods> {

    //获取商品列表
    @Select("select g.*, sg.stock_count, sg.start_date, sg.end_date, sg.seckill_price, sg.version from sk_goods_seckill sg left join sk_goods g on sg.goods_id = g.id")
    List<GoodsVo> findGoodsVo();


    @Select("select g.*, sg.stock_count, sg.start_date, sg.end_date, sg.seckill_price, sg.version  from sk_goods_seckill sg left join sk_goods g  on sg.goods_id = g.id where g.id = #{goodsId}")
    GoodsVo findGoodsVoByGoodsId(@Param("goodsId") long goodsId);

}
