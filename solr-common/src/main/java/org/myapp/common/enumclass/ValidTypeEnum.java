package org.myapp.common.enumclass;

public enum ValidTypeEnum {
    VALID(1, "valid"), IN_VALID(0, "invalid");

    private int typeCode;
    private String typeMessage;

    ValidTypeEnum(int typeCode, String typeMessage) {
        this.typeCode = typeCode;
        this.typeMessage = typeMessage;
    }

    public int getTypeCode() {
        return typeCode;
    }

    public String getTypeMessage() {
        return typeMessage;
    }

}
