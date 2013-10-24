package com.vincentfazio.ui.administration.activity;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.vincentfazio.ui.activity.GwtActivity;
import com.vincentfazio.ui.administration.global.GwtAdminGlobals;
import com.vincentfazio.ui.administration.view.UserDetailsDisplay;
import com.vincentfazio.ui.administration.view.UserListDisplay;
import com.vincentfazio.ui.administration.view.UserListDisplay.UserDeckEnum;
import com.vincentfazio.ui.bean.ErrorBean;
import com.vincentfazio.ui.bean.StatusBean;
import com.vincentfazio.ui.bean.UserDetailsBean;
import com.vincentfazio.ui.global.Globals;
import com.vincentfazio.ui.model.UserDetailsModel;
import com.vincentfazio.ui.view.ErrorDisplay;
import com.vincentfazio.ui.view.StatusDisplay;

public class UserDetailsActivity extends GwtActivity {

    private final Globals globals;
    private final String userId;

    public UserDetailsActivity(Globals globals, String userId) {
        super();
        this.globals = globals;
        this.userId = userId;
    }
    
    
    @Override
    public void start(AcceptsOneWidget panel, EventBus eventBus) {    
        UserDetailsDisplay userDetailsDisplay = (UserDetailsDisplay) globals.getDisplay(UserDetailsDisplay.class);
        UserDetailsBean userDetails = userDetailsDisplay.getUserDetails();
        
        if ((null == userDetails) || !userDetails.getUserId().equals(userId)) {
            userDetails = new UserDetailsBean();
            userDetails.setUserId(userId);
            userDetailsDisplay.setUserDetails(userDetails);
        
            UserDetailsModel model = (UserDetailsModel) globals.getModel(UserDetailsModel.class);
            model.getUser(userId,
                    new AsyncCallback<UserDetailsBean>() {
                        public void onSuccess(UserDetailsBean result) {
                            UserDetailsDisplay userDetailsDisplay = (UserDetailsDisplay) globals.getDisplay(UserDetailsDisplay.class);
                            userDetailsDisplay.setUserDetails(result);
    
                            // initialize the user list if it isn't
                            UserListDisplay userListDisplay = (UserListDisplay) globals.getDisplay(UserListDisplay.class);
                            if (!userListDisplay.isUserListLoaded()) {
                                new UserListActivity(GwtAdminGlobals.getInstance(), false).start(null, null);
                            } else {
                                userListDisplay.selectUser(userId, false);
                            }
                        }
                        public void onFailure(Throwable caught) {
                            ErrorDisplay errorDisplay = (ErrorDisplay) globals.getErrorDisplay();
                            errorDisplay.handleError(new ErrorBean(caught.getMessage(), caught));
                            UserListDisplay userListDisplay = (UserListDisplay) globals.getDisplay(UserListDisplay.class);
                            userListDisplay.showView(UserDeckEnum.NoUser);
                            if (userListDisplay.isUserListLoaded()) {
                                userListDisplay.selectUser(userId, false);
                            } else { 
                                UserDetailsBean userDetailsBean = new UserDetailsBean();
                                userDetailsBean.setUserId(userId);
                                UserDetailsDisplay userDetailsDisplay = (UserDetailsDisplay) globals.getDisplay(UserDetailsDisplay.class);
                                userDetailsDisplay.setUserDetails(userDetailsBean);
                                new UserListActivity(GwtAdminGlobals.getInstance(), false).start(null, null);
                            }
                        }
                    }
                 );
        }
    }
    
    
    @Override
    public String mayStop() {
        UserDetailsDisplay userDetailsDisplay = (UserDetailsDisplay) globals.getDisplay(UserDetailsDisplay.class);
        if (userDetailsDisplay.hasUnsavedChanges() && userDetailsDisplay.isValid()) {
            new UserDetailsSaveActivity(GwtAdminGlobals.getInstance(), userDetailsDisplay.getUserDetails()).start(null, null);
        } else if (!userDetailsDisplay.isValid()) {
            StatusDisplay statusDisplay = (StatusDisplay) globals.getStatusDisplay();
            statusDisplay.handleStatusUpdate(new StatusBean("Changes Discarded"));            
        }
        return null;
    }


}
