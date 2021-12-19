package com.newtranx.cloud.edit.filter;

import com.newtranx.cloud.edit.common.util.HttpUtil;
import com.newtranx.cloud.edit.config.AccountConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class MyLogGateWayFilter implements GlobalFilter,Ordered {

    @Autowired
    private AccountConfig accountConfig;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("***********come in MyLogGateWayFilter:  "+new Date());
        ServerHttpRequest request = exchange.getRequest();
        String accessToken = request.getHeaders().getFirst("accessToken");

        if(accessToken == null) {
            accessToken = request.getQueryParams().getFirst("accessToken");
            if(accessToken == null) {
                log.info("*******accessToken为null， 非法请求，o(╥﹏╥)o");
                exchange.getResponse().setStatusCode(HttpStatus.NOT_ACCEPTABLE);
                return exchange.getResponse().setComplete();
            }
        }

        HttpResponse response = HttpUtil.get(accountConfig.getDomainname() + accountConfig.getToken_status(), accessToken);
        if (response.getStatusLine().getStatusCode() != HttpStatus.OK.value()){
            exchange.getResponse().setStatusCode(HttpStatus.NOT_ACCEPTABLE);
            return exchange.getResponse().setComplete();
        }
        Map<String, Object> refUserMap = new HashMap<>();
        refUserMap.put("username", "ywj");
        exchange.getAttributes().put("refUser", refUserMap);
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
