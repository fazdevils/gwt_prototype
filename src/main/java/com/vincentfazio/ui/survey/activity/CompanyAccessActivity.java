package com.vincentfazio.ui.survey.activity;

import java.util.List;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.vincentfazio.ui.activity.GwtActivity;
import com.vincentfazio.ui.bean.ErrorBean;
import com.vincentfazio.ui.bean.MyDetailsBean;
import com.vincentfazio.ui.global.Globals;
import com.vincentfazio.ui.model.MyDetailsModel;
import com.vincentfazio.ui.survey.view.ProfileSurveyDisplay;
import com.vincentfazio.ui.survey.view.SecuritySurveyDisplay;
import com.vincentfazio.ui.survey.view.CompanyDetailsDisplay;
import com.vincentfazio.ui.view.ErrorDisplay;

public class CompanyAccessActivity extends GwtActivity {

    public static enum CompanyAccessDisplayType {
        ProfileSurvey,
        SecuritySurvey,
        CompanyDetails
    }
    
    private Globals globals;
    private CompanyAccessDisplayType displayType;

    public CompanyAccessActivity(Globals globals, CompanyAccessDisplayType displayType) {
        this.globals = globals;
        this.displayType = displayType;
    }

    @Override
    public void start(AcceptsOneWidget panel, EventBus eventBus) {

        MyDetailsModel detailsModel = (MyDetailsModel) globals.getModel(MyDetailsModel.class);
        detailsModel.getMyDetails(new AsyncCallback<MyDetailsBean>() {

            @Override
            public void onSuccess(MyDetailsBean result) {
                switch (displayType) {
                    case CompanyDetails:
                        updateCompanyDetailsDisplay(result.getCompanyAccess());
                        break;
                    case ProfileSurvey:
                        updateCompanyProfileDisplay(result.getCompanyAccess());
                        break;
                    case SecuritySurvey:
                        updateCompanySecurityDisplay(result.getCompanyAccess());
                        break;
                }
            }

            private void updateCompanyDetailsDisplay(List<String> companyAccessList) {
                new ActiveTasksActivity(globals).start(null, null); 
                new CompletedTasksActivity(globals).start(null, null);                            

                if (!companyAccessList.isEmpty()) {
                    CompanyDetailsDisplay display = (CompanyDetailsDisplay) globals.getDisplay(CompanyDetailsDisplay.class);
                    display.setCompanySearchOptions(companyAccessList);
                }
            }

            private void updateCompanyProfileDisplay(List<String> companyAccessList) {
                if (!companyAccessList.isEmpty()) {
                    ProfileSurveyDisplay display = (ProfileSurveyDisplay) globals.getDisplay(ProfileSurveyDisplay.class);
                    display.setCompanySearchOptions(companyAccessList);
                }
            }

            private void updateCompanySecurityDisplay(List<String> companyAccessList) {
                if (!companyAccessList.isEmpty()) {
                    SecuritySurveyDisplay display = (SecuritySurveyDisplay) globals.getDisplay(SecuritySurveyDisplay.class);
                    display.setCompanySearchOptions(companyAccessList);
                }
            }

            @Override
            public void onFailure(Throwable caught) {
                ErrorDisplay errorDisplay = (ErrorDisplay) globals.getErrorDisplay();
                errorDisplay.handleError(new ErrorBean(caught.getMessage(), caught));
            }
        });   
    }

}
