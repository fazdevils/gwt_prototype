package com.vincentfazio.ui.administration.view;

import java.util.List;

import com.vincentfazio.ui.administration.view.UserListDisplay;

public class MockUserListView implements UserListDisplay {

    private boolean userListLoaded = false;
    private List<String> userList = null;
    private String selectedUser = null;
    
    
    @Override
    public boolean isUserListLoaded() {
        return userListLoaded;
    }

    @Override
    public void selectUser(Boolean refreshUserDetail) {
        this.selectedUser = userList.get(0);
    }

    @Override
    public void selectUser(String selectedUserId, Boolean refreshUserDetail) {
        this.selectedUser = selectedUserId;
    }

    @Override
    public void setUserList(List<String> userList) {
        this.userListLoaded = true;
        this.userList = userList;
    }

    @Override
    public void setPresenter(Presenter presenter) {
        //throw new UnsupportedOperationException("Need to test this!!!");        
    }

    @Override
    public void showView(UserDeckEnum userView) {
        //throw new UnsupportedOperationException("Need to test this!!!");        
    }

    public List<String> getUserList() {
        return userList;
    }

    public String getSelectedUser() {
        return selectedUser;
    }

}
