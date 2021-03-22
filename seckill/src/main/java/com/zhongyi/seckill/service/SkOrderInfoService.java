package com.zhongyi.seckill.service;

import com.zhongyi.seckill.entity.SkOrderInfo;
import com.zhongyi.seckill.entity.SkUser;
import com.zhongyi.seckill.vo.GoodsVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhongyi
 * @since 2021-03-21
 */
public interface SkOrderInfoService extends IService<SkOrderInfo> {

    SkOrderInfo seckill(SkUser user, GoodsVo goodsVo);

}
