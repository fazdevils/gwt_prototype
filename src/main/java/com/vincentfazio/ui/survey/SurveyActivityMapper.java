package com.vincentfazio.ui.survey;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;
import com.vincentfazio.ui.activity.MyDetailsActivity;
import com.vincentfazio.ui.activity.MyPasswordActivity;
import com.vincentfazio.ui.bean.DocumentationBean;
import com.vincentfazio.ui.global.Globals;
import com.vincentfazio.ui.survey.activity.DocumentationDetailsActivity;
import com.vincentfazio.ui.survey.activity.DocumentationListActivity;
import com.vincentfazio.ui.survey.activity.ProfileSurveyActivity;
import com.vincentfazio.ui.survey.activity.SecuritySurveyActivity;
import com.vincentfazio.ui.survey.activity.CompanyAccessActivity;
import com.vincentfazio.ui.survey.activity.CompanyDetailsActivity;
import com.vincentfazio.ui.survey.activity.CompanyAccessActivity.CompanyAccessDisplayType;
import com.vincentfazio.ui.survey.place.DocumentationPlace;
import com.vincentfazio.ui.survey.place.MyUserPasswordPlace;
import com.vincentfazio.ui.survey.place.MyUserSettingsPlace;
import com.vincentfazio.ui.survey.place.ProfileSurveyPlace;
import com.vincentfazio.ui.survey.place.SecuritySurveyPlace;
import com.vincentfazio.ui.survey.place.CompanyAccessPlace;
import com.vincentfazio.ui.survey.place.CompanyDetailsPlace;

public class SurveyActivityMapper implements ActivityMapper {

    private Globals globals;
    
    public SurveyActivityMapper(Globals globals) {
        super();
        this.globals = globals;
    }

    @Override
    public Activity getActivity(Place place) {
        if (place instanceof MyUserSettingsPlace) {
            return new MyDetailsActivity(globals);
        } else if (place instanceof MyUserPasswordPlace) {
            return new MyPasswordActivity(globals);
        } else if (place instanceof CompanyAccessPlace) {
            return new CompanyAccessActivity(globals, CompanyAccessDisplayType.CompanyDetails);
        } else if (place instanceof CompanyDetailsPlace) {
            return new CompanyDetailsActivity(((CompanyDetailsPlace)place).getCompanyId(), globals);
        } else if (place instanceof ProfileSurveyPlace) {
            return new ProfileSurveyActivity(((ProfileSurveyPlace)place).getCompanyId(), ((ProfileSurveyPlace)place).getQuestion(), globals);
        } else if (place instanceof SecuritySurveyPlace) {
            return new SecuritySurveyActivity(((SecuritySurveyPlace)place).getCompanyId(), ((SecuritySurveyPlace)place).getQuestion(), globals);
        } else if (place instanceof DocumentationPlace) {
            DocumentationPlace documentationPlace = (DocumentationPlace)place;
			DocumentationBean documentation = documentationPlace.getDocumentation();
			String companyId = documentationPlace.getCompanyId();
            if (null == documentation) {
    			return new DocumentationListActivity(companyId, globals);            	
            } else {
            	return new DocumentationDetailsActivity(companyId, documentation, globals);
            }
        } 
        return null;
    }

}
