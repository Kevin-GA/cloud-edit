package com.newtranx.cloud.edit.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.newtranx.cloud.edit.dao.MenuMapper;
import com.newtranx.cloud.edit.dto.MenuQuery;
import com.newtranx.cloud.edit.entities.Menu;
import com.newtranx.cloud.edit.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 菜单表 服务实现类
 * </p>
 *
 * @author ywj
 * @since 2021-02-02
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Autowired
    MenuMapper menuMapper;


    @Override
    public List<Menu> getMenusByRoleCodes(List<String> authorityList, MenuQuery menuQuery) {
        return menuMapper.getMenusByRoleCodes(authorityList, menuQuery);
    }
}
