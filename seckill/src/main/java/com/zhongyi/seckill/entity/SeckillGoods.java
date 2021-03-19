package com.zhongyi.seckill.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author zhongyi
 * @since 2021-03-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SeckillGoods implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 商品id
     */
    private Long goodsId;

    /**
     * 秒杀价
     */
    private BigDecimal seckilPrice;

    /**
     * 秒杀数量
     */
    private Integer stockCount;

    private LocalDateTime startDate;

    private LocalDateTime endDate;


}
