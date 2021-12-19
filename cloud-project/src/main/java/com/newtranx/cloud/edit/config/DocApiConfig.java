package com.newtranx.cloud.edit.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author: niujiaxin
 * @Date: 2021-03-06 22:41
 *
 * doc调用接口配置
 */
@Data
@Component
@ConfigurationProperties(prefix = "doc.api")
public class DocApiConfig {

    private String baseAPIURL;

    /**
     * 拆解句段-生成XLIF
     */
    private String split;

    /**
     * 生成双语
     */
    private String geneng;

    /**
     * 生成译文
     */
    private String genchieng;
}
