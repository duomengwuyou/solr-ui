package org.myapp.common.enumclass;

public enum ErrorStatusEnum {
    USER_UNEXIST("ERROR001", "User Unexist", 0), NULL_DATA("ERROR002", "Data is Blank", 0), PASSWORD_WRONG("ERROR003",
            "Passwork Wrong", 0), INSERT_USER_ERROR("ERROR004", "Insert Data Error", 0), USER_EXIST("ERROR005",
            "User Already Exist", 0), REGISTER_TOKEN_ERROR("ERROR006", "Token Wrong", 0), DEL_USER_ERROR("ERROR007",
            "DEL User Error", 0);

    private String code;
    private String message;
    private int status;

    ErrorStatusEnum(String code, String message, int status) {
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
