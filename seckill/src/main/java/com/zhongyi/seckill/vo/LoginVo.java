package com.zhongyi.seckill.vo;

import javax.validation.constraints.NotNull;

import com.zhongyi.seckill.validator.IsMobile;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
public class LoginVo {
    @NotNull
    @IsMobile
    private String mobile;


    @NotNull
    @Length(min = 32)
    private String password;
}
