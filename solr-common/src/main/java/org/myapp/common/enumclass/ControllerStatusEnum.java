package org.myapp.common.enumclass;

public enum ControllerStatusEnum {
    OK("SUCCESS001", "OK", 1), ERROR("ERROR001", "ERROR", 0), INVALID_PARAM("ERROR002", "Params Invalid", 0), PASSWORK_NOT_SAME("ERROR003", "两次输入的密码不同", 0);

    private String code;
    private String message;
    private int status;

    ControllerStatusEnum(String code, String message, int status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }

}
