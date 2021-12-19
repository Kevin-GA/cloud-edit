package com.newtranx.cloud.edit.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.newtranx.cloud.edit.dto.TeamUserDto;
import com.newtranx.cloud.edit.dto.TeamUserListDto;
import com.newtranx.cloud.edit.entities.TeamUserPlus;
import com.newtranx.cloud.edit.entities.TeamUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/***
 * 团队成员Mapper
 * @author 佟文森
 */
@Mapper
public interface TeamUserMapper extends BaseMapper<TeamUser> {

    List<TeamUserListDto> getTeamUserList(TeamUserDto teamUserDto);

    List<TeamUserPlus> getTeamUserByTeamId(TeamUserDto teamUserDto);

    int countTeamUserByTeamId(TeamUserDto teamUserDto);

    @Select("select tu.user_id from team_user tu where tu.is_del=0 and tu.team_id in (SELECT tu1.team_id from team_user tu1 WHERE tu1.user_id=#{userId} and tu1.job_type=0 and tu1.is_del=0) GROUP BY tu.user_id ")
    List<TeamUser> getTeamUserInfo(@Param("userId") Long userId);
}
