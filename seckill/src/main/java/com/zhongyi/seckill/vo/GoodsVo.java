package com.zhongyi.seckill.vo;

import java.math.BigDecimal;
import java.sql.Date;

import com.zhongyi.seckill.entity.SkGoods;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsVo extends SkGoods{
    
    //秒杀价格
    private BigDecimal seckillPrice;
    //库存
    private Integer stockCount;
    //开始日期
    private Date startDate;
    //结束日期
    private Date endDate;


}
