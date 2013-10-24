package com.vincentfazio.ui.vendor.global;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.vincentfazio.ui.global.GwtGlobals;
import com.vincentfazio.ui.model.MyDetailsModel;
import com.vincentfazio.ui.model.TaskModel;
import com.vincentfazio.ui.model.VendorDetailsModel;
import com.vincentfazio.ui.model.mock.MyDetailsMockModel;
import com.vincentfazio.ui.vendor.global.GwtVendorGlobals;
import com.vincentfazio.ui.vendor.model.mock.VendorMockModel;
import com.vincentfazio.ui.vendor.view.MockVendorDetailsDisplay;
import com.vincentfazio.ui.vendor.view.MockVendorHomeDisplay;
import com.vincentfazio.ui.vendor.view.VendorDetailsDisplay;
import com.vincentfazio.ui.view.ErrorDisplay;
import com.vincentfazio.ui.view.HomeDisplay;
import com.vincentfazio.ui.view.StatusDisplay;

public class MockVendorGlobals extends GwtVendorGlobals {
    
    public static GwtGlobals getInstance() {
        if (null == instance) {
            synchronized (GwtVendorGlobals.class) {
                if (null == instance) {
                    instance = new MockVendorGlobals();
                }
            }
        }
        return instance;
    }
    
    private Place currentPlace = null;

    private MockVendorGlobals() {
        super();
        populateDisplayMap();
    }

    @Override
    protected void setMockModels(GwtGlobals globals) {
        modelMap.put(MyDetailsModel.class, new MyDetailsMockModel(true));
        modelMap.put(TaskModel.class, modelMap.get(MyDetailsModel.class));
        modelMap.put(VendorDetailsModel.class, new VendorMockModel());
    }

    private void populateDisplayMap() {
        displayMap.put(VendorDetailsDisplay.class, new MockVendorDetailsDisplay());
        displayMap.put(HomeDisplay.class, new MockVendorHomeDisplay());
    }

    @Override
    protected boolean isDevelopmentMode() {
        return true;
    }

    @Override
    public void gotoPlace(Place newPlace) {
        currentPlace = newPlace;
    }

    public Place getCurrentPlace() {
        return currentPlace;
    }
    
    @Override
    protected PlaceController getPlaceController() {
        return null;
    }
    
    @Override
    protected ErrorDisplay createErrorDisplay() {
        return new com.vincentfazio.ui.view.MockErrorView();
    }

    @Override
    protected StatusDisplay createStatusDisplay() {
        return new com.vincentfazio.ui.view.MockStatusView();
    }


}
