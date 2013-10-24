package com.vincentfazio.ui.administration.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;
import com.vincentfazio.ui.administration.global.GwtAdminGlobals;
import com.vincentfazio.ui.administration.view.UserListDisplay;
import com.vincentfazio.ui.administration.view.AdminHomeDisplay.AdminDeckEnum;
import com.vincentfazio.ui.administration.view.UserListDisplay.UserDeckEnum;
import com.vincentfazio.ui.view.HomeDisplay;

public class UserPermissionsPlace extends Place {

    private String uid;
    private String role;

    public UserPermissionsPlace(String uid, String role) {
        this.uid = uid;
        this.role = role;

        HomeDisplay homeDisplay = (HomeDisplay) GwtAdminGlobals.getInstance().getDisplay(HomeDisplay.class);
        homeDisplay.showView(AdminDeckEnum.MainNavigation);
        
        UserListDisplay userListDisplay = (UserListDisplay) GwtAdminGlobals.getInstance().getDisplay(UserListDisplay.class);
        userListDisplay.showView(UserDeckEnum.UserPermission);
    }

    public String getUid() {
        return uid;
    }
    
    public String getRole() {
        return role;
    }

    @Prefix(Tokenizer.PLACE_TOKEN)
    public static class Tokenizer implements PlaceTokenizer<UserPermissionsPlace> {
        public static final String PLACE_TOKEN = "userp";

        @Override
        public String getToken(UserPermissionsPlace place) {
            return place.getUid() + "&" + place.getRole().toLowerCase();
        }

        @Override
        public UserPermissionsPlace getPlace(String token) {
            String[] tokens = token.split("&");
            return new UserPermissionsPlace(tokens[0], tokens[1]);
        }
        
    }

}
