package com.vincentfazio.ui.bean;

import java.util.List;

public class MyDetailsBean {
    
    private String name;
    private String userId;
    private String email;
    private String phone;
    private String title;
    private List<String> companyAccess;
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public List<String> getCompanyAccess() {
        return companyAccess;
    }
    public void setCompanyAccess(List<String> companyAccess) {
        this.companyAccess = companyAccess;
    }
}
