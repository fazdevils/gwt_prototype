package com.vincentfazio.ui.survey.global;

import com.google.gwt.place.shared.Place;
import com.vincentfazio.ui.global.GwtGlobals;
import com.vincentfazio.ui.model.DocumentationModel;
import com.vincentfazio.ui.model.ErrorModel;
import com.vincentfazio.ui.model.LogoutModel;
import com.vincentfazio.ui.model.MyDetailsModel;
import com.vincentfazio.ui.model.MyPasswordModel;
import com.vincentfazio.ui.model.ProfileSurveyModel;
import com.vincentfazio.ui.model.SecuritySurveyModel;
import com.vincentfazio.ui.model.TaskModel;
import com.vincentfazio.ui.model.CompanyDetailsModel;
import com.vincentfazio.ui.model.mock.ErrorMockModel;
import com.vincentfazio.ui.model.mock.LogoutMockModel;
import com.vincentfazio.ui.model.mock.MyDetailsMockModel;
import com.vincentfazio.ui.model.mock.MyPasswordMockModel;
import com.vincentfazio.ui.model.web_service.ErrorService;
import com.vincentfazio.ui.model.web_service.LogoutService;
import com.vincentfazio.ui.model.web_service.MyDetailsService;
import com.vincentfazio.ui.model.web_service.MyPasswordService;
import com.vincentfazio.ui.model.web_service.ProfileSurveyService;
import com.vincentfazio.ui.model.web_service.SecuritySurveyService;
import com.vincentfazio.ui.model.web_service.TaskService;
import com.vincentfazio.ui.model.web_service.CompanyDetailsService;
import com.vincentfazio.ui.survey.model.mock.ProfileSurveyMockModel;
import com.vincentfazio.ui.survey.model.mock.SecuritySurveyMockModel;
import com.vincentfazio.ui.survey.model.mock.CompanyMockModel;
import com.vincentfazio.ui.survey.place.MyUserPasswordPlace;
import com.vincentfazio.ui.survey.place.MyUserSettingsPlace;
import com.vincentfazio.ui.survey.place.CompanyAccessPlace;

public class GwtSurveyGlobals extends GwtGlobals {
    
    protected static GwtSurveyGlobals instance = null;
    public static GwtGlobals getInstance() {
        if (null == instance) {
            synchronized (GwtSurveyGlobals.class) {
                if (null == instance) {
                    instance = new GwtSurveyGlobals();
                }
            }
        }
        return instance;
    }

    protected GwtSurveyGlobals() {
        super();
    }


    @Override
    protected void setMockModels(GwtGlobals globals) {
        modelMap.put(MyDetailsModel.class, new MyDetailsMockModel(true));
        modelMap.put(TaskModel.class, modelMap.get(MyDetailsModel.class));
        modelMap.put(DocumentationModel.class, modelMap.get(MyDetailsModel.class));
        modelMap.put(CompanyDetailsModel.class, new CompanyMockModel());
        modelMap.put(ProfileSurveyModel.class, new ProfileSurveyMockModel());
        modelMap.put(SecuritySurveyModel.class, new SecuritySurveyMockModel((TaskModel) modelMap.get(TaskModel.class)));
        modelMap.put(MyPasswordModel.class, new MyPasswordMockModel());
        modelMap.put(LogoutModel.class, new LogoutMockModel(globals));
        modelMap.put(ErrorModel.class, new ErrorMockModel(globals));
    }


    @Override
    protected void setSerivceModels(GwtGlobals globals) {
        modelMap.put(MyDetailsModel.class, new MyDetailsService(globals));
        modelMap.put(TaskModel.class, new TaskService(globals));
        modelMap.put(CompanyDetailsModel.class, new CompanyDetailsService(globals));
        modelMap.put(ProfileSurveyModel.class, new ProfileSurveyService(globals));
        modelMap.put(SecuritySurveyModel.class, new SecuritySurveyService(globals));
        modelMap.put(MyPasswordModel.class, new MyPasswordService(globals));
        modelMap.put(LogoutModel.class, new LogoutService(globals));
        modelMap.put(ErrorModel.class, new ErrorService(globals));
    }

    @Override
    public String getRole() {
        return "company";
    }

    
    @Override
    public Place createMyUserSettingsPlace() {
        return new MyUserSettingsPlace();

    }
    @Override
    public Place createMyUserPasswordPlace() {
        return new MyUserPasswordPlace();
    }


    @Override
    public Place getDefaultPlace() {
        return new CompanyAccessPlace();
    }

}
