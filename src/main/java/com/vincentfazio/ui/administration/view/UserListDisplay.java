package com.vincentfazio.ui.administration.view;

import java.util.List;

import com.vincentfazio.ui.view.Display;

public interface UserListDisplay extends Display {

    public static enum UserDeckEnum {
        UserDetails,
        UserPermission,
        NoUser;
    }

    boolean isUserListLoaded();
    
    void selectUser(Boolean refreshUserDetail);

    void selectUser(String selectedUserId, Boolean refreshUserDetail);

    void setUserList(List<String> userList);

    void setPresenter(Presenter presenter);
    
    void showView(UserDeckEnum userView);

    public interface Presenter {
        public void switchUser(String userId, UserDeckEnum currentPage);
    }

}