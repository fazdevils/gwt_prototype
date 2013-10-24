package com.vincentfazio.ui.administration.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;
import com.vincentfazio.ui.administration.global.GwtAdminGlobals;
import com.vincentfazio.ui.administration.view.UserListDisplay;
import com.vincentfazio.ui.administration.view.AdminHomeDisplay.AdminDeckEnum;
import com.vincentfazio.ui.administration.view.UserListDisplay.UserDeckEnum;
import com.vincentfazio.ui.view.HomeDisplay;

public class UserDetailsPlace extends Place {

    private String uid;

    public UserDetailsPlace(String token) {
        this.uid = token;
        HomeDisplay homeDisplay = (HomeDisplay) GwtAdminGlobals.getInstance().getDisplay(HomeDisplay.class);
        homeDisplay.showView(AdminDeckEnum.MainNavigation);
        
        UserListDisplay userListDisplay = (UserListDisplay) GwtAdminGlobals.getInstance().getDisplay(UserListDisplay.class);
        userListDisplay.showView(UserDeckEnum.UserDetails);
    }

    public String getUid() {
        return uid;
    }
    
    @Prefix(Tokenizer.PLACE_TOKEN)
    public static class Tokenizer implements PlaceTokenizer<UserDetailsPlace> {
        public static final String PLACE_TOKEN = "user";

        @Override
        public String getToken(UserDetailsPlace place) {
            return place.getUid();
        }

        @Override
        public UserDetailsPlace getPlace(String token) {
            return new UserDetailsPlace(token);
        }
        
    }

}
