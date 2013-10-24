package com.vincentfazio.ui.bean;

import java.util.Date;

public class TaskBean {
    
    private String company;
    private TaskType taskType;
    private Date lastActivity;
    private String requestedBy;
    private Date requestedOn;
    private String completedBy;
    private Date completedOn;
    
    public String getCompany() {
        return company;
    }
    public void setCompany(String company) {
        this.company = company;
    }
    
    public TaskType getTaskType() {
        return taskType;
    }
    public void setTaskType(TaskType taskType) {
        this.taskType = taskType;
    }
    public Date getLastActivity() {
        return lastActivity;
    }
    public void setLastActivity(Date lastActivity) {
        this.lastActivity = lastActivity;
    }
    public String getRequestedBy() {
        return requestedBy;
    }
    public void setRequestedBy(String requestedBy) {
        this.requestedBy = requestedBy;
    }
    public Date getRequestedOn() {
        return requestedOn;
    }
    public void setRequestedOn(Date requestedOn) {
        this.requestedOn = requestedOn;
    }
    public String getCompletedBy() {
        return completedBy;
    }
    public void setCompletedBy(String completedBy) {
        this.completedBy = completedBy;
    }
    public Date getCompletedOn() {
        return completedOn;
    }
    public void setCompletedOn(Date completedOn) {
        this.completedOn = completedOn;
    }
}
