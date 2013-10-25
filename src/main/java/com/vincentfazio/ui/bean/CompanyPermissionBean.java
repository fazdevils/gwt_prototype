package com.vincentfazio.ui.bean;


public class CompanyPermissionBean {
    
    private boolean hasAccess;
    private String companyName;
    
    public boolean hasAccess() {
        return hasAccess;
    }
    public void setAccess(boolean hasAccess) {
        this.hasAccess = hasAccess;
    }
    public String getCompanyName() {
        return companyName;
    }
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    
    public static class CompanyNameFieldContainsStringComparator implements SearchBoxComparator<CompanyPermissionBean> {

        @Override
        public boolean equals(CompanyPermissionBean bean, String searchText) {
            return bean.getCompanyName().toLowerCase().contains(searchText.toLowerCase());
        }
        
    }

}
