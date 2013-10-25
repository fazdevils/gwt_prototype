package com.vincentfazio.ui.company.global;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.vincentfazio.ui.global.GwtGlobals;
import com.vincentfazio.ui.model.MyDetailsModel;
import com.vincentfazio.ui.model.TaskModel;
import com.vincentfazio.ui.model.CompanyDetailsModel;
import com.vincentfazio.ui.model.mock.MyDetailsMockModel;
import com.vincentfazio.ui.survey.global.GwtSurveyGlobals;
import com.vincentfazio.ui.survey.model.mock.CompanyMockModel;
import com.vincentfazio.ui.survey.view.CompanyDetailsDisplay;
import com.vincentfazio.ui.company.view.MockCompanyDetailsDisplay;
import com.vincentfazio.ui.company.view.MockCompanyHomeDisplay;
import com.vincentfazio.ui.view.ErrorDisplay;
import com.vincentfazio.ui.view.HomeDisplay;
import com.vincentfazio.ui.view.StatusDisplay;

public class MockCompanyGlobals extends GwtSurveyGlobals {
    
    public static GwtGlobals getInstance() {
        if (null == instance) {
            synchronized (GwtSurveyGlobals.class) {
                if (null == instance) {
                    instance = new MockCompanyGlobals();
                }
            }
        }
        return instance;
    }
    
    private Place currentPlace = null;

    private MockCompanyGlobals() {
        super();
        populateDisplayMap();
    }

    @Override
    protected void setMockModels(GwtGlobals globals) {
        modelMap.put(MyDetailsModel.class, new MyDetailsMockModel(true));
        modelMap.put(TaskModel.class, modelMap.get(MyDetailsModel.class));
        modelMap.put(CompanyDetailsModel.class, new CompanyMockModel());
    }

    private void populateDisplayMap() {
        displayMap.put(CompanyDetailsDisplay.class, new MockCompanyDetailsDisplay());
        displayMap.put(HomeDisplay.class, new MockCompanyHomeDisplay());
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
