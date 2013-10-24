package com.vincentfazio.ui.vendor.view;

import java.util.List;
import java.util.Set;
import java.util.SortedSet;

import com.vincentfazio.ui.bean.TaskBean;
import com.vincentfazio.ui.bean.VendorDetailsBean;
import com.vincentfazio.ui.vendor.view.VendorDetailsDisplay;

public class MockVendorDetailsDisplay implements VendorDetailsDisplay {

    private boolean valid = true;
    private boolean unsavedChanges = false;
    private VendorDetailsBean vendorDetailsBean;
    private SortedSet<TaskBean> activeTasks;
    private SortedSet<TaskBean> completedTasks;
    
    @Override
    public boolean isValid() {
        return valid;
    }

    @Override
    public VendorDetailsBean getVendorDetails() {
        return vendorDetailsBean;
    }

    @Override
    public void setVendorDetails(VendorDetailsBean vendor) {
        vendorDetailsBean = vendor;
    }

    @Override
    public boolean hasUnsavedChanges() {
        return unsavedChanges;
    }

    @Override
    public void setHasUnsavedChanges(boolean hasUnsavedChanges) {
        this.unsavedChanges = hasUnsavedChanges;
    }

    @Override
    public void setVendorSearchOptions(List<String> vendorList) {
        //this.vendorSearchOptions = vendorList;
    }

    @Override
    public void setActiveTasks(SortedSet<TaskBean> activeTasks) {
        this.activeTasks = activeTasks;
    }

    public SortedSet<TaskBean> getActiveTasks() {
        return activeTasks;
    }

    @Override
    public void highlightVendorDetailsPane() {
        throw new UnsupportedOperationException("Need to test this!!!");        
    }

    @Override
    public void setCompletedTasks(SortedSet<TaskBean> completedTasks) {
        this.completedTasks = completedTasks;
    }

    public Set<TaskBean> getCompletedTasks() {
        return completedTasks;
    }

}
