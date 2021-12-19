package com.newtranx.cloud.edit.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author: niujiaxin
 * @Date: 2021-03-19 16:03
 *
 */

@Data
@Component
@ConfigurationProperties(prefix = "file")
public class FileSavePathConfig {

    private String savePath;

}
