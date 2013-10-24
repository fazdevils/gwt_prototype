package com.vincentfazio.ui.vendor.view;

import java.util.List;
import java.util.SortedSet;

import com.vincentfazio.ui.bean.TaskBean;
import com.vincentfazio.ui.bean.VendorDetailsBean;
import com.vincentfazio.ui.view.Display;


public interface VendorDetailsDisplay extends Display {

    boolean isValid();

    VendorDetailsBean getVendorDetails();    
    void setVendorDetails(VendorDetailsBean vendor);

    boolean hasUnsavedChanges();
    void setHasUnsavedChanges(boolean hasUnsavedChanges);

    void setVendorSearchOptions(List<String> vendorList);
    
    void setActiveTasks(SortedSet<TaskBean> activeTasks);

    void highlightVendorDetailsPane();

    void setCompletedTasks(SortedSet<TaskBean> completedTasks);

}