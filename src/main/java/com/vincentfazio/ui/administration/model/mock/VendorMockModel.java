package com.vincentfazio.ui.administration.model.mock;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.vincentfazio.ui.bean.VendorDetailsBean;
import com.vincentfazio.ui.model.VendorDetailsModel;
import com.vincentfazio.ui.model.VendorListModel;

public class VendorMockModel implements VendorDetailsModel, VendorListModel {
    
    private Map<String, VendorDetailsBean> mockVendorMap = new TreeMap<String, VendorDetailsBean>(new VendorNameComparator());

    public VendorMockModel() {
        super();
        for (int i=0; i < 4000; ++i) {
            String vendorId = "Vendor " + (i+1);
            VendorDetailsBean vendor = new VendorDetailsBean();
            vendor.setVendorId(vendorId);
            vendor.setAddress((i+1) + "Vendor Street");
            vendor.setCity("Buffalo");
            vendor.setState("NY");
            vendor.setUrl("http://www." + vendorId.replace(" ", "") + ".com");

            mockVendorMap.put(vendorId, vendor);
        }
        
        String vendorId = "A Long Vendor Name - Longer than Most Will Ever Be";
        VendorDetailsBean vendor = new VendorDetailsBean();
        vendor.setVendorId(vendorId);
        mockVendorMap.put(vendorId, vendor);

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

    @Override
    public void createVendor(String vendorId, AsyncCallback<String> asyncCallback) {
        VendorDetailsBean vendor = new VendorDetailsBean();
        vendor.setVendorId(vendorId);
        mockVendorMap.put(vendorId, vendor);
        asyncCallback.onSuccess("Created");  
    }

    @Override
    public void deleteVendor(String vendorId, AsyncCallback<String> asyncCallback) {
        mockVendorMap.remove(vendorId);        
        asyncCallback.onSuccess("Deleted");
    }

    /* (non-Javadoc)
     * @see com.vincentfazio.ui.administration.model.VendorListModel#getVendorList(com.google.gwt.user.client.rpc.AsyncCallback)
     */
    @Override
    public void getVendorList(AsyncCallback<ArrayList<String>> asyncCallback) {
        asyncCallback.onSuccess(new ArrayList<String>(mockVendorMap.keySet()));
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
            if (arg1.startsWith("Vendor ")) {
                try {
                    vendor1 = new Integer(arg1.replace("Vendor ", ""));
                    if (arg2.startsWith("Vendor ")) {
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

}
