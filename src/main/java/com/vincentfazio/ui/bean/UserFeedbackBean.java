package com.vincentfazio.ui.bean;

public class UserFeedbackBean {
    
    private Boolean isPositive;
    private String comment;
    private String page;
    private Boolean isError;
    
    public UserFeedbackBean() {
        super();
    }

    public Boolean isPositive() {
        return isPositive;
    }

    public void setIsPositive(Boolean isPositive) {
        this.isPositive = isPositive;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public Boolean isError() {
        return isError;
    }

    public void setIsError(Boolean isError) {
        this.isError = isError;
    }


}
