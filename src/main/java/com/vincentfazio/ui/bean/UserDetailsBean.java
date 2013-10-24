package com.vincentfazio.ui.bean;

public class UserDetailsBean {
    
    private String name;
    private String userId;
    private String email;
    private String phone;
    private String title;
    private boolean isAdministrator;
    private boolean isCustomer;
    private boolean isCompany;
    
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
    public boolean isAdministrator() {
        return isAdministrator;
    }
    public void setAdministrator(boolean isAdministrator) {
        this.isAdministrator = isAdministrator;
    }
    public boolean isCustomer() {
        return isCustomer;
    }
    public void setCustomer(boolean isCustomer) {
        this.isCustomer = isCustomer;
    }
    public boolean isCompany() {
        return isCompany;
    }
    public void setCompany(boolean isCompany) {
        this.isCompany = isCompany;
    }
}
