package com.vincentfazio.ui.administration.activity;

import java.util.ArrayList;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.vincentfazio.ui.activity.GwtActivity;
import com.vincentfazio.ui.administration.global.GwtAdminGlobals;
import com.vincentfazio.ui.administration.place.UserDetailsPlace;
import com.vincentfazio.ui.administration.place.UserPermissionsPlace;
import com.vincentfazio.ui.administration.view.UserListDisplay;
import com.vincentfazio.ui.administration.view.UserPermissionsDisplay;
import com.vincentfazio.ui.administration.view.UserListDisplay.UserDeckEnum;
import com.vincentfazio.ui.bean.ErrorBean;
import com.vincentfazio.ui.global.Globals;
import com.vincentfazio.ui.model.UserListModel;
import com.vincentfazio.ui.view.ErrorDisplay;

public class UserListActivity extends GwtActivity implements UserListDisplay.Presenter {
       
    public UserListActivity(Globals globals, Boolean refreshUserDetail) {
        super();
        this.globals = globals;
        this.refreshUserDetail = refreshUserDetail;
        
        UserListDisplay display = (UserListDisplay) globals.getDisplay(UserListDisplay.class);
        display.setPresenter(this);
    }

    private final Globals globals;
    private final Boolean refreshUserDetail;
    
    @Override
    public void start(AcceptsOneWidget panel, EventBus eventBus) {
        UserListModel model = (UserListModel) globals.getModel(UserListModel.class);
        model.getUserList(
            new AsyncCallback<ArrayList<String>>() {
                public void onSuccess(ArrayList<String> result) {
                    UserListDisplay display = (UserListDisplay) globals.getDisplay(UserListDisplay.class);
                    display.setUserList(result);
                    display.selectUser(refreshUserDetail);     
                }
                public void onFailure(Throwable caught) {
                    ErrorDisplay errorDisplay = (ErrorDisplay) globals.getErrorDisplay();
                    errorDisplay.handleError(new ErrorBean(caught.getMessage(), caught));
                }
            }
         );
    }


    @Override
    public void switchUser(String selectedUserId, UserDeckEnum currentPage) {
        switch (currentPage) {
            case UserPermission:
                UserPermissionsDisplay permissionsDisplay =  (UserPermissionsDisplay) GwtAdminGlobals.getInstance().getDisplay(UserPermissionsDisplay.class);
                globals.gotoPlace(new UserPermissionsPlace(selectedUserId, permissionsDisplay.getUserRole()));                
                break;
            case UserDetails:
                globals.gotoPlace(new UserDetailsPlace(selectedUserId));
                break;
            default:
                break;
        }
    }
    
}
