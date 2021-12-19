package com.newtranx.task.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 自定义权限注解验证
 */
@Component
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class UserPermissionEvaluator extends GlobalMethodSecurityConfiguration implements PermissionEvaluator {

    /**
     * hasPermission鉴权方法
     * 这里仅仅判断PreAuthorize注解中的权限表达式
     * 实际中可以根据业务需求设计数据库通过targetUrl和permission做更复杂鉴权
     * 当然targetUrl不一定是URL可以是数据Id还可以是管理员标识等,这里根据需求自行设计
     * @Author Sans
     * @CreateTime 2019/10/6 18:25
     * @Param  authentication  用户身份(在使用hasPermission表达式时Authentication参数默认会自动带上)
     * @Param  targetUrl  请求路径
     * @Param  permission 请求路径权限
     * @Return boolean 是否通过
     */
    @Override
    public boolean hasPermission(Authentication authentication, Object targetUrl, Object permission) {
        // 获取用户信息
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Set<String> targetUrls = authorities.parallelStream()
                .map(GrantedAuthority::getAuthority)
                .filter(item -> item.contains("/") && !item.contains(":"))
                .collect(Collectors.toSet());
        Set<String> permissions = authorities.parallelStream()
                .map(GrantedAuthority::getAuthority)
                .filter(item -> !item.contains("/") && item.contains(":"))
                .collect(Collectors.toSet());
        // 查询用户权限(这里可以将权限放入缓存中提升效率)
        // 权限对比8
        if (targetUrls.contains(targetUrl) && permissions.contains(permission.toString())){
            return true;
        }
        return false;
    }
    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        return false;
    }

    @Override
    public DefaultMethodSecurityExpressionHandler createExpressionHandler(){
        DefaultMethodSecurityExpressionHandler handler = new DefaultMethodSecurityExpressionHandler();
        handler.setPermissionEvaluator(new UserPermissionEvaluator());
        return handler;
    }
}