//package com.newtranx.cloud.edit.common.base;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.oauth2.common.OAuth2AccessToken;
//import org.springframework.security.oauth2.provider.OAuth2Authentication;
//import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
//import org.springframework.security.oauth2.provider.token.TokenStore;
//import org.springframework.stereotype.Component;
//
//import javax.servlet.http.HttpServletRequest;
//import java.util.Map;
//
//@Component
//public class BaseController {
//    @Autowired
//    TokenStore tokenStore;
//
//    @Autowired
//    HttpServletRequest request;
//
//    public Object getUserId(OAuth2Authentication authentication) {
//        OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) authentication.getDetails();
//        OAuth2AccessToken oAuth2AccessToken = tokenStore.readAccessToken(details.getTokenValue());
//        Map<String, Object> additionalInformation = oAuth2AccessToken.getAdditionalInformation();
//        return additionalInformation.get("userId");
//    }
//
//    public Object getUserId(){
//        String authorization = request.getHeader("Authorization");
//        if (authorization == null || !authorization.startsWith("Bearer ")) {
//            return 0;
//        }
//        String accessToken = authorization.substring("Bearer ".length());
//        OAuth2AccessToken oAuth2AccessToken = tokenStore.readAccessToken(accessToken);
//        Map<String, Object> additionalInformation = oAuth2AccessToken.getAdditionalInformation();
//        return additionalInformation.get("userId");
//    }
//}