package com.newtranx.cloud.edit.enums;


import com.baomidou.mybatisplus.core.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;

import java.io.Serializable;

/**
 * 描述:项目流程枚举类
 *
 * @version V1.0
 */
public enum ProjectProcessTypesEnum implements IEnum {

    TRANSLATE(1, "翻译"),
    EDIT(2, "编辑"),
    CHECK(3, "校对"),
    ;

    ProjectProcessTypesEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    private String name;

    private Integer code;

    public Integer getCode() {
        return code;
    }

    @Override
    public Serializable getValue() {
        return code;
    }

    @JsonValue
    public String getName(){
        return this.name;
    }


    public static ProjectProcessTypesEnum getByCode(Integer code) {
        ProjectProcessTypesEnum[] values = ProjectProcessTypesEnum.values();
        for (ProjectProcessTypesEnum projectProcessTypeEnum : values) {
            if (projectProcessTypeEnum.getCode().equals(code)) {
                return projectProcessTypeEnum;
            }
        }
        return null;
    }
}
