package com.vincentfazio.ui.vendor.model.mock;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.vincentfazio.ui.bean.VendorDetailsBean;
import com.vincentfazio.ui.model.VendorDetailsModel;

public class VendorMockModel implements VendorDetailsModel {
    
    private Map<String, VendorDetailsBean> mockVendorMap = new TreeMap<String, VendorDetailsBean>(new VendorNameComparator());

    public VendorMockModel() {
        super();
        for (int i=0; i < 3; ++i) {
            String vendorId = "Vendor " + (i+1);
            VendorDetailsBean vendor = new VendorDetailsBean();
            vendor.setVendorId(vendorId);
            vendor.setStockExchange(vendorId);
            vendor.setState("NY");
            vendor.setUrl("www." + vendorId.replace(" ", "") + ".com");
            vendor.setStockExchange(vendorId);

            mockVendorMap.put(vendorId, vendor);
        }
    }

    @Override
    public void getVendor(String vendorId, AsyncCallback<VendorDetailsBean> asyncCallback) {
        VendorDetailsBean vendor = getVendor(vendorId);
        if (null == vendor) {
            asyncCallback.onFailure(new Exception("Supplier not found"));
        } else {
            asyncCallback.onSuccess(vendor);
        }
    }

    @Override
    public void saveVendor(VendorDetailsBean vendor, AsyncCallback<String> asyncCallback) 
    {
        mockVendorMap.put(vendor.getVendorId(), copyVendor(vendor));        
        asyncCallback.onSuccess("Saved");  
    }

    private VendorDetailsBean getVendor(String vendorId) {
        VendorDetailsBean vendor = mockVendorMap.get(vendorId);
        if (null == vendor) {
            return null;
        }
        return copyVendor(vendor);
    }
    
    private VendorDetailsBean copyVendor(VendorDetailsBean bean) {
        VendorDetailsBean copy = new VendorDetailsBean();
        copy.setVendorId(bean.getVendorId());
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

    static class VendorNameComparator implements Comparator<String> {

        @Override
        public int compare(String arg1, String arg2) {
            Comparable<?> vendor1;
            Comparable<?> vendor2;
            if ((null != arg1) && arg1.startsWith("Vendor ")) {
                try {
                    vendor1 = new Integer(arg1.replace("Vendor ", ""));
                    if ((null != arg2) && arg2.startsWith("Vendor ")) {
                        vendor2 = new Integer(arg2.replace("Vendor ", ""));
                    } else {
                        vendor1 = arg1;
                        vendor2 = arg2;
                    }
                } catch (NumberFormatException e) {
                    vendor1 = arg1;
                    vendor2 = arg2;                    
                }
            } else {
                vendor1 = arg1;
                vendor2 = arg2;
            }
            
            
            if (isaInteger(vendor1) && isaInteger(vendor2)) {
                Integer userId1 = (Integer)vendor1;
                return userId1.compareTo((Integer)vendor2);
            }
            
            int compare = vendor1.toString().toLowerCase().compareTo(vendor2.toString().toLowerCase());
            if (0 == compare) {
                compare = vendor1.toString().compareTo(vendor2.toString());                
            }
            return compare;
        }
        
        private boolean isaInteger(Object o1) {
            return o1 instanceof Integer;
        }

    }

    @Override
    public void createVendor(String vendorId, AsyncCallback<String> asyncCallback) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteVendor(String vendorId, AsyncCallback<String> asyncCallback) {
        throw new UnsupportedOperationException();
    }

}
