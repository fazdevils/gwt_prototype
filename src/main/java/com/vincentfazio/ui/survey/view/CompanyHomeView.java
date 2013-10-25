package com.vincentfazio.ui.survey.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DeckLayoutPanel;
import com.google.gwt.user.client.ui.Widget;
import com.vincentfazio.ui.global.GwtGlobals;
import com.vincentfazio.ui.survey.global.GwtSurveyGlobals;
import com.vincentfazio.ui.survey.view.documentation.DocumentationListView;
import com.vincentfazio.ui.view.HomeDisplay;
import com.vincentfazio.ui.view.MyDetailsDisplay;
import com.vincentfazio.ui.view.MyDetailsView;
import com.vincentfazio.ui.view.MyPasswordDisplay;
import com.vincentfazio.ui.view.MyPasswordView;
import com.vincentfazio.ui.view.ViewDeckEnum;

public class CompanyHomeView extends Composite implements CompanyHomeDisplay {

    @UiField
    DeckLayoutPanel companyScreens;
    
    @UiField
    CompanyDetailsView companyDetails;
    
    @UiField
    ProfileSurveyView profileSurvey;

    @UiField
    SecuritySurveyView securitySurvey;

    @UiField
    DocumentationListView documentation;

    @UiField(provided=true)
    MyDetailsView userSettings;
    
    @UiField(provided=true)
    MyPasswordView userPassword;
    
    private static CompanyHomeUiBinder uiBinder = GWT.create(CompanyHomeUiBinder.class);


    interface CompanyHomeUiBinder extends UiBinder<Widget, CompanyHomeView> {
    }

    public CompanyHomeView() {
        GwtGlobals globals = GwtSurveyGlobals.getInstance();
        userSettings = (MyDetailsView) globals.getDisplay(MyDetailsDisplay.class);
        userPassword = (MyPasswordView) globals.getDisplay(MyPasswordDisplay.class);        
        
        initWidget(uiBinder.createAndBindUi(this));
        companyScreens.showWidget(companyDetails);
                
        globals.registerDisplay(HomeDisplay.class, this);

    }

    @Override
    public void showView(ViewDeckEnum view) {
        CompanyDeckEnum companyView = (CompanyDeckEnum)view;
        switch (companyView) {
            case CompanyDetails:
                companyScreens.showWidget(companyDetails);

                /**
                 * There a bug in IE where, when going directly to a user or 
                 * company detail page, the stack layout panel does not seem to
                 * draw correctly for some reason.  Calling forceLayout seems 
                 * to fix that. 
                 */
                companyScreens.forceLayout();
                break;
            case SecuritySurvey:
                companyScreens.showWidget(securitySurvey);
                break;       
            case ProfileSurvey:
                companyScreens.showWidget(profileSurvey);
                break;       
            case UserSettings:
                companyScreens.showWidget(userSettings);
                break;       
            case UserPassword:
                companyScreens.showWidget(userPassword);
                break;
		case Documentation:
            companyScreens.showWidget(documentation);
            break;
		default:
			break;       
        }
    }  
    
    
}
