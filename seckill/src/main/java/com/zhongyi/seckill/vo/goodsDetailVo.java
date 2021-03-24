package com.zhongyi.seckill.vo;

import com.zhongyi.seckill.entity.SkGoods;
import com.zhongyi.seckill.entity.SkUser;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class goodsDetailVo {
    
    private SkUser user;

    private SkGoods goods;

    private int seckillStatus;

    private int remainSeconds;

     
}
