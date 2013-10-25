package com.vincentfazio.ui.bean;


public class CompanyUserPermissionBean {
    
    private boolean hasAccess;
    private String userName;
    
    public boolean hasAccess() {
        return hasAccess;
    }
    public void setAccess(boolean hasAccess) {
        this.hasAccess = hasAccess;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String companyName) {
        this.userName = companyName;
    }
    
    public static class UserNameFieldContainsStringComparator implements SearchBoxComparator<CompanyUserPermissionBean> {

        @Override
        public boolean equals(CompanyUserPermissionBean bean, String searchText) {
            return bean.getUserName().toLowerCase().contains(searchText.toLowerCase());
        }
        
    }

}
