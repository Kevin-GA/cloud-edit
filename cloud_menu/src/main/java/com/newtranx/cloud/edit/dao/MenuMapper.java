package com.newtranx.cloud.edit.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.newtranx.cloud.edit.dto.MenuQuery;
import com.newtranx.cloud.edit.entities.Menu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 菜单表 Mapper 接口
 * </p>
 *
 * @author ywj
 * @since 2021-02-02
 */
@Mapper
public interface MenuMapper extends BaseMapper<Menu> {

    @Select({
            "<script>",
            "select",
            "distinct m.*",
            "from menu m left join role_menu rm on m.menu_id = rm.menu_id",
            "left join role r on r.role_id = rm.role_id",
            "where m.is_del = 0 and r.role_code in",
            "<foreach collection='roleCodes' item='roleCode' open='(' separator=',' close=')'>",
            "#{roleCode}",
            "</foreach>",
            "<if test='menuQuery.parentId != null'> and m.parent_id = #{menuQuery.parentId} </if>",
            "<if test='menuQuery.parentId == null'> and m.parent_id = 0 </if>",
            "</script>"
    })
    List<Menu> getMenusByRoleCodes(@Param("roleCodes") List<String> roleCodes, @Param("menuQuery") MenuQuery menuQuery);
}
