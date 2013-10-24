package com.vincentfazio.ui.bean;


public class ErrorBean {
    
    private String errorDescription;
    private Throwable error;
    
    public ErrorBean(String errorDescription, Throwable error) {
        super();
        this.errorDescription = errorDescription;
        this.error = error;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public Throwable getError() {
        return error;
    }

}
