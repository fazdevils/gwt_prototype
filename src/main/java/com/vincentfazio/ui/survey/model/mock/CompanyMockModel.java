package com.vincentfazio.ui.survey.model.mock;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.vincentfazio.ui.bean.CompanyDetailsBean;
import com.vincentfazio.ui.model.CompanyDetailsModel;

public class CompanyMockModel implements CompanyDetailsModel {
    
    private Map<String, CompanyDetailsBean> mockCompanyMap = new TreeMap<String, CompanyDetailsBean>(new CompanyNameComparator());

    public CompanyMockModel() {
        super();
        for (int i=0; i < 3; ++i) {
            String companyId = "Company " + (i+1);
            CompanyDetailsBean company = new CompanyDetailsBean();
            company.setCompanyId(companyId);
            company.setStockExchange(companyId);
            company.setState("NY");
            company.setUrl("www." + companyId.replace(" ", "") + ".com");
            company.setStockExchange(companyId);

            mockCompanyMap.put(companyId, company);
        }
    }

    @Override
    public void getCompany(String companyId, AsyncCallback<CompanyDetailsBean> asyncCallback) {
        CompanyDetailsBean company = getCompany(companyId);
        if (null == company) {
            asyncCallback.onFailure(new Exception("Company not found"));
        } else {
            asyncCallback.onSuccess(company);
        }
    }

    @Override
    public void saveCompany(CompanyDetailsBean company, AsyncCallback<String> asyncCallback) 
    {
        mockCompanyMap.put(company.getCompanyId(), copyCompany(company));        
        asyncCallback.onSuccess("Saved");  
    }

    private CompanyDetailsBean getCompany(String companyId) {
        CompanyDetailsBean company = mockCompanyMap.get(companyId);
        if (null == company) {
            return null;
        }
        return copyCompany(company);
    }
    
    private CompanyDetailsBean copyCompany(CompanyDetailsBean bean) {
        CompanyDetailsBean copy = new CompanyDetailsBean();
        copy.setCompanyId(bean.getCompanyId());
        copy.setAddress(bean.getAddress());
        copy.setCity(bean.getCity());
        copy.setPhone(bean.getPhone());
        copy.setState(bean.getState());
        copy.setZip(bean.getZip());
        copy.setCountry(bean.getCountry());
        copy.setStockExchange(bean.getStockExchange());
        copy.setUrl(bean.getUrl());
        copy.setStockSymbol(bean.getStockSymbol());
        copy.setDunsNumber(bean.getDunsNumber());
        copy.setExperianBin(bean.getExperianBin());

        return copy;
    }

    static class CompanyNameComparator implements Comparator<String> {

        @Override
        public int compare(String arg1, String arg2) {
            Comparable<?> company1;
            Comparable<?> company2;
            if ((null != arg1) && arg1.startsWith("Company ")) {
                try {
                    company1 = new Integer(arg1.replace("Company ", ""));
                    if ((null != arg2) && arg2.startsWith("Company ")) {
                        company2 = new Integer(arg2.replace("Company ", ""));
                    } else {
                        company1 = arg1;
                        company2 = arg2;
                    }
                } catch (NumberFormatException e) {
                    company1 = arg1;
                    company2 = arg2;                    
                }
            } else {
                company1 = arg1;
                company2 = arg2;
            }
            
            
            if (isaInteger(company1) && isaInteger(company2)) {
                Integer userId1 = (Integer)company1;
                return userId1.compareTo((Integer)company2);
            }
            
            int compare = company1.toString().toLowerCase().compareTo(company2.toString().toLowerCase());
            if (0 == compare) {
                compare = company1.toString().compareTo(company2.toString());                
            }
            return compare;
        }
        
        private boolean isaInteger(Object o1) {
            return o1 instanceof Integer;
        }

    }

    @Override
    public void createCompany(String companyId, AsyncCallback<String> asyncCallback) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteCompany(String companyId, AsyncCallback<String> asyncCallback) {
        throw new UnsupportedOperationException();
    }

}
