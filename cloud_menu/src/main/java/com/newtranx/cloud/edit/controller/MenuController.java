package com.newtranx.cloud.edit.controller;


import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.newtranx.cloud.edit.common.entities.Result;
import com.newtranx.cloud.edit.common.enums.FailureCodeEnum;
import com.newtranx.cloud.edit.util.BeanPropertyUtil;
import com.newtranx.cloud.edit.dto.MenuDto;
import com.newtranx.cloud.edit.dto.MenuMinDto;
import com.newtranx.cloud.edit.dto.MenuQuery;
import com.newtranx.cloud.edit.entities.Menu;
import com.newtranx.cloud.edit.service.MenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 菜单表 前端控制器
 * </p>
 *
 * @author ywj
 * @since 2021-02-02
 */
@Slf4j
@RestController
@RequestMapping
@Api(value = "菜单接口")
public class MenuController {
    
    @Autowired
    private MenuService menuService;

    @ApiOperation(value = "添加菜单", notes = "添加菜单")
    @ApiResponses({@ApiResponse(code = 200, message = "添加成功与否", response = Result.class)})
    @PostMapping("/save")
    public Result save(@Valid @RequestBody MenuDto menuDto){
        Menu menu = new Menu();
        BeanUtils.copyProperties(menuDto, menu);
        menu.setCreateTime(new Date());
        menu.setUpdateTime(new Date());
        return Result.getSuccessResult(menuService.save(menu));
    }

    @ApiOperation(value = "修改菜单", notes = "修改菜单")
    @ApiResponses({@ApiResponse(code = 200, message = "修改成功与否", response = Result.class)})
    @PutMapping("/update/{menuId}")
    public Result update(@PathVariable("menuId") Long menuId, @Valid @RequestBody MenuDto menuDto){
        Menu menu = menuService.getById(menuId);
        BeanUtils.copyProperties(menuDto, menu, BeanPropertyUtil.getNullPropertyNames(menuDto));
        menu.setMenuId(menuId);
        menu.setUpdateTime(new Date());
        return Result.getSuccessResult(menuService.updateById(menu));
    }

    @ApiOperation(value = "获取单个菜单信息", notes = "获取单个菜单信息")
    @ApiResponses({@ApiResponse(code = 200, message = "获取单个菜单信息", response = MenuDto.class)})
    @GetMapping("/get/{menuId}")
    public Result get(@PathVariable("menuId") Long menuId){
        Menu menu = menuService.getById(menuId);
        MenuDto menuDto = new MenuDto();
        BeanUtils.copyProperties(menu, menuDto);
        return Result.getSuccessResult(menuDto);
    }

    @ApiOperation(value = "删除菜单", notes = "删除菜单")
    @ApiResponses({@ApiResponse(code = 200, message = "删除成功与否", response = Result.class)})
    @DeleteMapping("/delete/{menuId}")
    public Result delete(@PathVariable("menuId") Long menuId){
        Menu menu = new Menu();
        menu.setMenuId(menuId);
        menu.setIsDel(Boolean.TRUE);
        menu.setUpdateTime(new Date());
        return Result.getSuccessResult(menuService.updateById(menu));
    }

    @ApiOperation(value = "菜单分页", notes = "菜单分页")
    @ApiResponses({@ApiResponse(code = 200, message = "菜单分页", response = MenuDto.class)})
    @PostMapping(value = "/page")
    public Result page(@RequestBody MenuQuery menuQuery) {
        IPage<MenuDto> resultDto =  new Page<>();
        Page page = new Page(Optional.ofNullable(menuQuery.getPageIndex()).orElse(1),Optional.ofNullable(menuQuery.getPageSize()).orElse(10));
        page.addOrder(OrderItem.asc("menu_id"));
        try {
            QueryWrapper<Menu> queryWrapper = new QueryWrapper();
            queryWrapper.select(Menu.class, role -> !role.getColumn().equals("is_del")
                    && !role.getColumn().equals("update_time"));
            if (menuQuery.getParentId() == null) {
                menuQuery.setParentId(0l);
            }
            queryWrapper.lambda().eq(Menu::getParentId, menuQuery.getParentId()).eq(Menu::getIsDel, Boolean.FALSE);
//            if (StringUtils.isNoneBlank(userQuery.getUsername())) {
//                queryWrapper.lambda().like(User::getUsername, userQuery.getUsername());
//            }
            IPage<Menu> result = menuService.page(page, queryWrapper);
            BeanUtils.copyProperties(result, resultDto);
            List<MenuDto> menuDtos = new ArrayList<>();
            result.getRecords().forEach(role -> {
                MenuDto MenuDto = new MenuDto();
                BeanUtils.copyProperties(role, MenuDto);
                menuDtos.add(MenuDto);
            });
            resultDto.setRecords(menuDtos);
        } catch (Exception e){
            e.printStackTrace();
        }
        if(CollectionUtil.isNotEmpty(resultDto.getRecords())) {
            return Result.getSuccessResult(resultDto);
        } else {
            return Result.getFailureResult(FailureCodeEnum.INVALID_PARAM);
        }
    }

    @ApiOperation(value = "菜单查询", notes = "菜单查询")
    @ApiResponses({@ApiResponse(code = 200, message = "菜单查询结果", response = MenuMinDto.class)})
    @PostMapping(value = "/query")
    public Result query(@RequestBody MenuQuery menuQuery,  Authentication authentication) {
        List<MenuMinDto> resultDtos = new ArrayList<>();
        try {
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            List<String> authorityList = authorities.parallelStream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
            List<Menu> menuList = menuService.getMenusByRoleCodes(authorityList, menuQuery);
            menuList.forEach(menu -> {
                MenuMinDto menuMinDto = new MenuMinDto();
                BeanUtils.copyProperties(menu, menuMinDto);
                resultDtos.add(menuMinDto);
            });
//            QueryWrapper<Menu> queryWrapper = new QueryWrapper();
//            queryWrapper.select(Menu.class,
//                    role -> role.getColumn().equals("menu_id")
//                            || role.getColumn().equals("menu_name")
//                            || role.getColumn().equals("icon")
//                            || role.getColumn().equals("perms")
//                            || role.getColumn().equals("orderNum")
//                            || role.getColumn().equals("type")
//            );
//            queryWrapper.lambda().eq(Menu::getIsDel, Boolean.FALSE);
//            if (menuQuery.getParentId() == null) {
//                queryWrapper.lambda().eq(Menu::getParentId, 0);
//            } else {
//                queryWrapper.lambda().eq(Menu::getParentId, menuQuery.getParentId());
//            }
//            List<Menu> result = menuService.list(queryWrapper);
//            result.forEach(role -> {
//                MenuMinDto menuDto = new MenuMinDto();
//                BeanUtils.copyProperties(role, menuDto);
//                resultDtos.add(menuDto);
//            });
        } catch (Exception e){
            e.printStackTrace();
        }
        return Result.getSuccessResult(resultDtos);
    }

    @GetMapping("/getPrinciple")
    public OAuth2Authentication getPrinciple(OAuth2Authentication oAuth2Authentication, Principal principal, Authentication authentication) {
        log.info(oAuth2Authentication.getUserAuthentication().getAuthorities().toString());
        log.info(oAuth2Authentication.toString());
        log.info("principal.toString() " + principal.toString());
        log.info("principal.getName() " + principal.getName());
        log.info("authentication: " + authentication.getAuthorities().toString());

        return oAuth2Authentication;
    }
}

