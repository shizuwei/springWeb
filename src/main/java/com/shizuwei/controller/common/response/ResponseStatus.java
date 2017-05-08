package com.shizuwei.controller.common.response;

public enum ResponseStatus {

    OK(200), PARTLY_OK(300), BUSINESS_ERROR(400), SYSTEM_ERROR(500), PARAM_ERROR(600), NO_LOGIN(700), NO_PERMISSION(800);

    private int value;

    private ResponseStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

}
