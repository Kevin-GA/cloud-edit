package com.newtranx.cloud.edit.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.newtranx.cloud.edit.dto.TeamUser;
import com.newtranx.cloud.edit.entities.ProjectFile;
import com.newtranx.cloud.edit.entities.ProjectProcess;
import com.newtranx.cloud.edit.entities.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserDao extends BaseMapper<User>
{

    List<User> getUserByProjectId(@Param("projectId") Long projectId);

    @Select("select team_id from team_user where user_id= #{userId} and is_del=0 limit 1")
    String getTeamIdByUserId(@Param("userId")String userId);

    @Select("select tu.user_id from team_user tu where tu.is_del=0 and tu.team_id in (SELECT tu1.team_id from team_user tu1 WHERE tu1.user_id=#{userId} and tu1.job_type=0 and tu1.is_del=0) GROUP BY tu.user_id ")
    List<TeamUser> getTeamUserInfo(@Param("userId") Long userId);
}
