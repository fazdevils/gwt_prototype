package com.vincentfazio.ui.bean;


public class VendorPermissionBean {
    
    private boolean hasAccess;
    private String vendorName;
    
    public boolean hasAccess() {
        return hasAccess;
    }
    public void setAccess(boolean hasAccess) {
        this.hasAccess = hasAccess;
    }
    public String getVendorName() {
        return vendorName;
    }
    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }
    
    public static class VendorNameFieldContainsStringComparator implements SearchBoxComparator<VendorPermissionBean> {

        @Override
        public boolean equals(VendorPermissionBean bean, String searchText) {
            return bean.getVendorName().toLowerCase().contains(searchText.toLowerCase());
        }
        
    }

}
