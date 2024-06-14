package com.sparta.newspeed.entity;

public enum  ContentTypeEnum {
    PEED("PEED"),
    COMMENT("COMMENT");

    private String type;

    ContentTypeEnum(String type) {
        this.type = type;
    }
}
