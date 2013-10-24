package com.vincentfazio.ui.administration.activity;

import java.util.ArrayList;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.vincentfazio.ui.activity.GwtActivity;
import com.vincentfazio.ui.administration.global.GwtAdminGlobals;
import com.vincentfazio.ui.administration.place.VendorDetailsPlace;
import com.vincentfazio.ui.administration.place.VendorUserPermissionsPlace;
import com.vincentfazio.ui.administration.view.UserPermissionsDisplay;
import com.vincentfazio.ui.administration.view.VendorListDisplay;
import com.vincentfazio.ui.administration.view.VendorUserPermissionsDisplay;
import com.vincentfazio.ui.administration.view.VendorListDisplay.VendorDeckEnum;
import com.vincentfazio.ui.bean.ErrorBean;
import com.vincentfazio.ui.global.Globals;
import com.vincentfazio.ui.model.VendorListModel;
import com.vincentfazio.ui.view.ErrorDisplay;

public class VendorListActivity extends GwtActivity implements VendorListDisplay.Presenter {
    
    public VendorListActivity(Globals globals, Boolean refreshVendorDetail) {
        super();
        this.globals = globals;
        this.refreshVendorDetail = refreshVendorDetail;
        
        VendorListDisplay vendorListDisplay = (VendorListDisplay) globals.getDisplay(VendorListDisplay.class);
        vendorListDisplay.setPresenter(this);
    }

    private final Boolean refreshVendorDetail;
    private final Globals globals;
    
    @Override
    public void start(AcceptsOneWidget panel, EventBus eventBus) {
        VendorListModel model = (VendorListModel) globals.getModel(VendorListModel.class);
        model.getVendorList(
                new AsyncCallback<ArrayList<String>>() {
                    public void onSuccess(ArrayList<String> result) {
                        VendorListDisplay vendorListDisplay = (VendorListDisplay) globals.getDisplay(VendorListDisplay.class);
                        vendorListDisplay.setVendorList(result);
                        vendorListDisplay.selectVendor(refreshVendorDetail);
                        
                        UserPermissionsDisplay userPermissionsDisplay = (UserPermissionsDisplay) globals.getDisplay(UserPermissionsDisplay.class);
                        userPermissionsDisplay.setVendorList(result);                        
                    }
                    public void onFailure(Throwable caught) {
                        ErrorDisplay errorDisplay = (ErrorDisplay) globals.getErrorDisplay();
                        errorDisplay.handleError(new ErrorBean(caught.getMessage(), caught));
                    }
                }
             );
    }

    @Override
    public void switchVendor(String vendorId, VendorDeckEnum currentPage) {
        switch (currentPage) {
            case VendorUserPermissions:
                VendorUserPermissionsDisplay permissionsDisplay =  (VendorUserPermissionsDisplay) GwtAdminGlobals.getInstance().getDisplay(VendorUserPermissionsDisplay.class);
                globals.gotoPlace(new VendorUserPermissionsPlace(vendorId, permissionsDisplay.getUserRole()));                
                break;
            case VendorDetails:
                globals.gotoPlace(new VendorDetailsPlace(vendorId));
                break;
            default:
                break;
        }

    }

}
