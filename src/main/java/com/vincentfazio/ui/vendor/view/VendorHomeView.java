package com.vincentfazio.ui.vendor.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DeckLayoutPanel;
import com.google.gwt.user.client.ui.Widget;
import com.vincentfazio.ui.global.GwtGlobals;
import com.vincentfazio.ui.vendor.global.GwtVendorGlobals;
import com.vincentfazio.ui.vendor.view.documentation.DocumentationListView;
import com.vincentfazio.ui.view.HomeDisplay;
import com.vincentfazio.ui.view.MyDetailsDisplay;
import com.vincentfazio.ui.view.MyDetailsView;
import com.vincentfazio.ui.view.MyPasswordDisplay;
import com.vincentfazio.ui.view.MyPasswordView;
import com.vincentfazio.ui.view.ViewDeckEnum;

public class VendorHomeView extends Composite implements VendorHomeDisplay {

    @UiField
    DeckLayoutPanel vendorScreens;
    
    @UiField
    VendorDetailsView vendorDetails;
    
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
    
    private static VendorHomeUiBinder uiBinder = GWT.create(VendorHomeUiBinder.class);


    interface VendorHomeUiBinder extends UiBinder<Widget, VendorHomeView> {
    }

    public VendorHomeView() {
        GwtGlobals globals = GwtVendorGlobals.getInstance();
        userSettings = (MyDetailsView) globals.getDisplay(MyDetailsDisplay.class);
        userPassword = (MyPasswordView) globals.getDisplay(MyPasswordDisplay.class);        
        
        initWidget(uiBinder.createAndBindUi(this));
        vendorScreens.showWidget(vendorDetails);
                
        globals.registerDisplay(HomeDisplay.class, this);

    }

    @Override
    public void showView(ViewDeckEnum view) {
        VendorDeckEnum vendorView = (VendorDeckEnum)view;
        switch (vendorView) {
            case VendorDetails:
                vendorScreens.showWidget(vendorDetails);

                /**
                 * There a bug in IE where, when going directly to a user or 
                 * supplier detail page, the stack layout panel does not seem to
                 * draw correctly for some reason.  Calling forceLayout seems 
                 * to fix that. 
                 */
                vendorScreens.forceLayout();
                break;
            case SecuritySurvey:
                vendorScreens.showWidget(securitySurvey);
                break;       
            case ProfileSurvey:
                vendorScreens.showWidget(profileSurvey);
                break;       
            case UserSettings:
                vendorScreens.showWidget(userSettings);
                break;       
            case UserPassword:
                vendorScreens.showWidget(userPassword);
                break;
		case Documentation:
            vendorScreens.showWidget(documentation);
            break;
		default:
			break;       
        }
    }  
    
    
}
