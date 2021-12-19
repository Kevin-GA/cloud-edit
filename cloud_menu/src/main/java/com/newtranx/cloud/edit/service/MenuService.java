package com.newtranx.cloud.edit.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.newtranx.cloud.edit.dto.MenuQuery;
import com.newtranx.cloud.edit.entities.Menu;

import java.util.List;

/**
 * <p>
 * 菜单表 服务类
 * </p>
 *
 * @author ywj
 * @since 2021-02-02
 */
public interface MenuService extends IService<Menu> {

    List<Menu> getMenusByRoleCodes(List<String> authorityList, MenuQuery menuQuery);
}
