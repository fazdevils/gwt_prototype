package com.vincentfazio.ui.vendor.activity;

import java.util.List;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.vincentfazio.ui.activity.GwtActivity;
import com.vincentfazio.ui.bean.ErrorBean;
import com.vincentfazio.ui.bean.MyDetailsBean;
import com.vincentfazio.ui.global.Globals;
import com.vincentfazio.ui.model.MyDetailsModel;
import com.vincentfazio.ui.vendor.view.ProfileSurveyDisplay;
import com.vincentfazio.ui.vendor.view.SecuritySurveyDisplay;
import com.vincentfazio.ui.vendor.view.VendorDetailsDisplay;
import com.vincentfazio.ui.view.ErrorDisplay;

public class VendorAccessActivity extends GwtActivity {

    public static enum VendorAccessDisplayType {
        ProfileSurvey,
        SecuritySurvey,
        VendorDetails
    }
    
    private Globals globals;
    private VendorAccessDisplayType displayType;

    public VendorAccessActivity(Globals globals, VendorAccessDisplayType displayType) {
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
                    case VendorDetails:
                        updateVendorDetailsDisplay(result.getVendorAccess());
                        break;
                    case ProfileSurvey:
                        updateVendorProfileDisplay(result.getVendorAccess());
                        break;
                    case SecuritySurvey:
                        updateVendorSecurityDisplay(result.getVendorAccess());
                        break;
                }
            }

            private void updateVendorDetailsDisplay(List<String> vendorAccessList) {
                new ActiveTasksActivity(globals).start(null, null); 
                new CompletedTasksActivity(globals).start(null, null);                            

                if (!vendorAccessList.isEmpty()) {
                    VendorDetailsDisplay display = (VendorDetailsDisplay) globals.getDisplay(VendorDetailsDisplay.class);
                    display.setVendorSearchOptions(vendorAccessList);
                }
            }

            private void updateVendorProfileDisplay(List<String> vendorAccessList) {
                if (!vendorAccessList.isEmpty()) {
                    ProfileSurveyDisplay display = (ProfileSurveyDisplay) globals.getDisplay(ProfileSurveyDisplay.class);
                    display.setVendorSearchOptions(vendorAccessList);
                }
            }

            private void updateVendorSecurityDisplay(List<String> vendorAccessList) {
                if (!vendorAccessList.isEmpty()) {
                    SecuritySurveyDisplay display = (SecuritySurveyDisplay) globals.getDisplay(SecuritySurveyDisplay.class);
                    display.setVendorSearchOptions(vendorAccessList);
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
