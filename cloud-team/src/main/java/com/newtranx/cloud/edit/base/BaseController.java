package com.newtranx.cloud.edit.base;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Map;

@Component
public class BaseController {
    @Autowired
    TokenStore tokenStore;

    @Autowired
    HttpServletRequest request;

    private static final String ROLE_ADMIN = "ROLE_ADMIN";
    private static final String ROLE_PROJECT_MAN = "ROLE_PROJECT_MAN";
    private static final String ROLE_TMX_MAN = "ROLE_TMX_MAN";
    private static final String ROLE_TRANX_MAN = "ROLE_TRANX_MAN";

    public Object getUserId(OAuth2Authentication authentication) {
        OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) authentication.getDetails();
        OAuth2AccessToken oAuth2AccessToken = tokenStore.readAccessToken(details.getTokenValue());
        Map<String, Object> additionalInformation = oAuth2AccessToken.getAdditionalInformation();
        return additionalInformation.get("userId");
    }

    public Object getUserId(){
        String authorization = request.getHeader("Authorization");
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            return 0;
        }
        String accessToken = authorization.substring("Bearer ".length());
        OAuth2AccessToken oAuth2AccessToken = tokenStore.readAccessToken(accessToken);
        Map<String, Object> additionalInformation = oAuth2AccessToken.getAdditionalInformation();
        return additionalInformation.get("userId");
    }

    public BaseUser getBaseUser() {
        String authorization = request.getHeader("Authorization");
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            return null;
        }

        String accessToken = authorization.substring("Bearer ".length());
        OAuth2AccessToken oAuth2AccessToken = tokenStore.readAccessToken(accessToken);
        Map<String, Object> additionalInformation = oAuth2AccessToken.getAdditionalInformation();
        BaseUser baseUser = new BaseUser();
        baseUser.setUserId(Long.valueOf((Integer)additionalInformation.get("userId")));
        baseUser.setUsername((String) additionalInformation.get("user_name"));
        Collection<String> authorities = (Collection<String>) additionalInformation.get("authorities");
        if (authorities.contains(ROLE_ADMIN)) {
            baseUser.setAdmin(Boolean.TRUE);
        }
        return baseUser;
    }

}
