package com.vincentfazio.ui.administration.global;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.vincentfazio.ui.administration.global.GwtAdminGlobals;
import com.vincentfazio.ui.administration.view.MockAdminHomeDisplay;
import com.vincentfazio.ui.administration.view.MockUserListView;
import com.vincentfazio.ui.administration.view.MockCompanyUserPermissionsView;
import com.vincentfazio.ui.administration.view.UserListDisplay;
import com.vincentfazio.ui.administration.view.CompanyUserPermissionsDisplay;
import com.vincentfazio.ui.global.GwtGlobals;
import com.vincentfazio.ui.survey.global.GwtSurveyGlobals;
import com.vincentfazio.ui.view.ErrorDisplay;
import com.vincentfazio.ui.view.HomeDisplay;
import com.vincentfazio.ui.view.StatusDisplay;

public class MockAdminGlobals extends GwtAdminGlobals {
    
    public static GwtGlobals getInstance() {
        if (null == instance) {
            synchronized (GwtSurveyGlobals.class) {
                if (null == instance) {
                    instance = new MockAdminGlobals();
                }
            }
        }
        return instance;
    }

    private Place currentPlace = null;

    private MockAdminGlobals() {
        super();
        populateDisplayMap();
    }

    private void populateDisplayMap() {
        displayMap.put(UserListDisplay.class, new MockUserListView());
        displayMap.put(HomeDisplay.class, new MockAdminHomeDisplay());
        displayMap.put(CompanyUserPermissionsDisplay.class, new MockCompanyUserPermissionsView());
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
