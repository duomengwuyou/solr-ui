package org.myapp.common.model;

public class DisplayItem {
    private String title;
    
    private String contentDate;
    
    private String content;
    
    private String sourceLink;
    
    private String identifyStr;
    
    private int upTimes;
    
    private int downTimes;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContentDate() {
        return contentDate;
    }

    public void setContentDate(String contentDate) {
        this.contentDate = contentDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSourceLink() {
        return sourceLink;
    }

    public void setSourceLink(String sourceLink) {
        this.sourceLink = sourceLink;
    }

    public String getIdentifyStr() {
        return identifyStr;
    }

    public void setIdentifyStr(String identifyStr) {
        this.identifyStr = identifyStr;
    }

    public int getUpTimes() {
        return upTimes;
    }

    public void setUpTimes(int upTimes) {
        this.upTimes = upTimes;
    }

    public int getDownTimes() {
        return downTimes;
    }

    public void setDownTimes(int downTimes) {
        this.downTimes = downTimes;
    }
    
}   
