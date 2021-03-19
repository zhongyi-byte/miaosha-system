package com.zhongyi.seckill.entity;

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
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 名称
     */
    private String userName;

    private String phone;

    /**
     * MD5(MD5(pass明文+固定salt)+salt)
     */
    private String password;

    /**
     * 混淆盐
     */
    private String salt;

    /**
     * 头像
     */
    private String head;

    /**
     * 登录次数
     */
    private Integer loginCount;

    /**
     * 注册时间
     */
    private LocalDateTime registerDate;

    /**
     * 最后一次登录时间
     */
    private LocalDateTime lastLoginDate;


}
