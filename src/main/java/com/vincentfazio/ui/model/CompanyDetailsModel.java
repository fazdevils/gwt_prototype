package com.vincentfazio.ui.model;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.vincentfazio.ui.bean.CompanyDetailsBean;

public interface CompanyDetailsModel extends Model {

    void getCompany(String companyId, AsyncCallback<CompanyDetailsBean> asyncCallback);

    void saveCompany(CompanyDetailsBean companyDetails, AsyncCallback<String> asyncCallback);

    void createCompany(String companyId, AsyncCallback<String> asyncCallback);

    void deleteCompany(String companyId, AsyncCallback<String> asyncCallback);

}