package com.newtranx.task.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.newtranx.task.commons.utils.PageData;
import com.newtranx.task.domain.Task;
import com.newtranx.task.dto.TaskR;
import com.newtranx.task.dto.TeamUser;
import com.newtranx.task.dto.TeamUserR;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author AHui
 * @since 2021-01-29
 */
public interface TeamUserMapper{
    /**
     * 查询当前任务所有参与人员
     * @param teamId
     * @return
     */
    List<TeamUserR> selectTeamUser(@Param("teamId") Long teamId);

    /**
     * 查询某个团队下的所有人员
     * @param teamId
     * @return
     */
    List<TeamUser> selectTeamUserByTeamId(@Param("teamId") Long teamId);

    @Select("select tu.user_id from team_user tu where tu.is_del=0 and tu.team_id in (SELECT tu1.team_id from team_user tu1 WHERE tu1.user_id=#{userId} and tu1.job_type=0 and tu1.is_del=0) GROUP BY tu.user_id ")
    List<TeamUser> getTeamUserInfo(@Param("userId") Long userId);
}
