package com.vincentfazio.ui.administration.activity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.vincentfazio.ui.administration.activity.CreateUserActivity;
import com.vincentfazio.ui.administration.global.MockAdminGlobals;
import com.vincentfazio.ui.administration.model.mock.UserMockModel;
import com.vincentfazio.ui.administration.place.UserDetailsPlace;
import com.vincentfazio.ui.administration.view.MockUserListView;
import com.vincentfazio.ui.administration.view.UserListDisplay;
import com.vincentfazio.ui.model.UserListModel;
import com.vincentfazio.ui.view.MockErrorView;
import com.vincentfazio.ui.view.MockStatusView;

public class CreateUserActivityTest {

    @Test
    public void testCareateUserActivity() {
        
        // setup
        MockAdminGlobals globals = (MockAdminGlobals) MockAdminGlobals.getInstance();
        MockStatusView statusDisplay = (MockStatusView) globals.getStatusDisplay();
        MockErrorView errorDisplay = (MockErrorView) globals.getErrorDisplay();
        MockUserListView userListView = (MockUserListView) globals.getDisplay(UserListDisplay.class);
        globals.gotoPlace(null);
        statusDisplay.handleStatusUpdate(null);
        errorDisplay.handleError(null);
        
        UserMockModel userModel = (UserMockModel) globals.getModel(UserListModel.class);

        String userId = "testUser";
        assertFalse(userModel.contains(userId));
        assertNull(statusDisplay.getStatus());
        assertNull(errorDisplay.getError());
        assertFalse(userListView.isUserListLoaded());
        assertNull(globals.getCurrentPlace());
        
        
        int numberOfUsers = userModel.getNumberOfUsers();
        
        
        // *** user clicked the add user button
        new CreateUserActivity(globals, userId).start(null, null);
        
        
        // verify user was added to the model        
        assertTrue(userModel.contains(userId));
        assertEquals(numberOfUsers+1, userModel.getNumberOfUsers());
        
        // verify status display
        assertNotNull(statusDisplay.getStatus());
        assertNull(errorDisplay.getError());
        assertEquals("User Created", statusDisplay.getStatus().getStatusDescription());
        
        // verify that the user list display has the correct number of users and the correct user is selected
        assertTrue(userListView.isUserListLoaded());
        assertEquals(numberOfUsers+1, userListView.getUserList().size());
        userListView.selectUser(userId, false);
        assertEquals(userId, userListView.getSelectedUser());
        
        // verify that we're in the right place
        UserDetailsPlace currentPlace = (UserDetailsPlace) globals.getCurrentPlace();
        assertNotNull(currentPlace);
        assertEquals(userId, currentPlace.getUid());
        
        
    }

}
