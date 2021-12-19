package com.newtranx.cloud.edit.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.newtranx.cloud.edit.entities.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ywj
 * @since 2021-01-26
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
    User getUserById(@Param("id") Long id);
    User getByRefUserId(@Param("refUserId") String refUserId);
}
