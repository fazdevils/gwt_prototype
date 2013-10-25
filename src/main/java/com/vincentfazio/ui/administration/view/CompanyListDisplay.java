package com.vincentfazio.ui.administration.view;

import java.util.List;

import com.vincentfazio.ui.view.Display;

public interface CompanyListDisplay extends Display {

    public static enum CompanyDeckEnum {
        CompanyDetails,
        NoCompany,
        CompanyUserPermissions;
    }

    boolean isCompanyListLoaded();
    
    void selectCompany(Boolean refreshCompanyDetail);

    void selectCompany(String selectedCompanyId, Boolean refreshCompanyDetail);

    void setCompanyList(List<String> companyList);
    
    void setPresenter(Presenter presenter);
    
    void showView(CompanyDeckEnum companyView);

    public interface Presenter {
        void switchCompany(String companyId, CompanyDeckEnum currentPage);
    }
    
}