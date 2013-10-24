package com.vincentfazio.ui.vendor.view;

import java.util.List;

public interface VendorSelectorDisplay {

    String getVendorId();

    void setVendorId(String vendorId);

    void setVendorSearchOptions(List<String> vendorList);

}