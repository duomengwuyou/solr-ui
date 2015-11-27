package org.myapp.common.enumclass;

public enum RatingTypeEnum {
    TOTAL_SCORE(1, "TOTAL Score"), DAILY_SCORE(2, "Daily Score"), TOTAL_RATING_NUM(3, "Total Increasing Num"),
    TOTAL_INCREASING(4, "Total Increasing"), FIVE_STAR(5, "5 Stars"), FOUR_STAR(6, "4 Stars"),
    THREE_STAR(7, "3 Stars"), TWO_STAR(8, "2 Stars"), ONE_STAR(9, "1 Star"), ALL(10, "All");

    private int typeCode;
    private String typeMessage;

    RatingTypeEnum(int typeCode, String typeMessage) {
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
