package com.sparta.newspeed.entity;

public enum UserStatusEnum {
    NORMAL(Authority.NORMAL),
    WITHDREW(Authority.WITHDREW);

    private String status;

    UserStatusEnum(String status) {
        this.status = status;

    }

    public String getStatus() {
        return this.status;
    }

    public static class Authority {
        private static final String NORMAL = "STATUS_NORMAL";
        private static final String WITHDREW = "STATUS_WITHDREW";

    }
}

