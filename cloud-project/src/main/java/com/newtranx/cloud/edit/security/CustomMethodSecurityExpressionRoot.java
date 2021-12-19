package com.newtranx.cloud.edit.security;//package com.newtranx.cloud.edit.security;
//
//import org.springframework.security.access.expression.SecurityExpressionRoot;
//import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.util.AntPathMatcher;
//
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.List;
//import java.util.stream.Collectors;
//
///**
// * 自定义权限校验
// */
//public class CustomMethodSecurityExpressionRoot extends SecurityExpressionRoot implements MethodSecurityExpressionOperations {
//    private static final AntPathMatcher antPathMatcher = new AntPathMatcher();
//    public CustomMethodSecurityExpressionRoot(Authentication authentication) {
//        super(authentication);
//    }
//    private Object filterObject;
//    private Object returnObject;
//    private Object target;
//
//    public boolean hasPrivilege(String permission){
//        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
//        return authorities.stream()
//                    .map(GrantedAuthority::getAuthority)
//                    .filter(item -> !item.startsWith("ROLE_"))
//                    .anyMatch(x -> antPathMatcher.match(x, permission));
//    }
//
//
//    public void setFilterObject(Object filterObject) {
//        this.filterObject = filterObject;
//    }
//
//    public Object getFilterObject() {
//        return this.filterObject;
//    }
//
//    public void setReturnObject(Object returnObject) {
//        this.returnObject = returnObject;
//    }
//
//    public Object getReturnObject() {
//        return this.returnObject;
//    }
//
//    void setThis(Object target) {
//        this.target = target;
//    }
//
//    public Object getThis() {
//        return this.target;
//    }
//
//}