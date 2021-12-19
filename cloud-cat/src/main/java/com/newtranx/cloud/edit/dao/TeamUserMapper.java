package com.newtranx.cloud.edit.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.newtranx.cloud.edit.entities.TeamUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/***
 * 团队成员Mapper
 * @author 佟文森
 */
@Mapper
public interface TeamUserMapper extends BaseMapper<TeamUser> {

}
