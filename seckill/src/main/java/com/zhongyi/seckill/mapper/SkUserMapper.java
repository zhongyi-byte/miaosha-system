package com.zhongyi.seckill.mapper;

import com.zhongyi.seckill.entity.SkUser;

import org.apache.ibatis.annotations.Update;

import io.lettuce.core.dynamic.annotation.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zhongyi
 * @since 2021-03-20
 */
public interface SkUserMapper extends BaseMapper<SkUser> {

    @Update("update sk_user set login_count = '#{loginCount}' where user_id = #{user_id}")
    void setLoginCount(@Param("loginCount") long loginCount,@Param("user_id") long user_id);
}
