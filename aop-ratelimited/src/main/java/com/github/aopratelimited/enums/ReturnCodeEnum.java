package com.github.aopratelimited.enums;

public enum ReturnCodeEnum {
    Login_Account_Error(429);

    private final int code;

    ReturnCodeEnum(int i) {
        this.code = i;
    }

    public int getCode() {
        return code;
    }
}