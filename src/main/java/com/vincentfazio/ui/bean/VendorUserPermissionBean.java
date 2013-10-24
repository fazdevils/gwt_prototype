package com.vincentfazio.ui.bean;


public class VendorUserPermissionBean {
    
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
    public void setUserName(String vendorName) {
        this.userName = vendorName;
    }
    
    public static class UserNameFieldContainsStringComparator implements SearchBoxComparator<VendorUserPermissionBean> {

        @Override
        public boolean equals(VendorUserPermissionBean bean, String searchText) {
            return bean.getUserName().toLowerCase().contains(searchText.toLowerCase());
        }
        
    }

}
