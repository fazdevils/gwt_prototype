package com.vincentfazio.ui.administration.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;
import com.vincentfazio.ui.administration.global.GwtAdminGlobals;
import com.vincentfazio.ui.administration.view.AdminHomeDisplay.AdminDeckEnum;
import com.vincentfazio.ui.view.HomeDisplay;

public class MyUserSettingsPlace extends Place {

    public MyUserSettingsPlace() {
    }

    @Prefix(Tokenizer.PLACE_TOKEN)
    public static class Tokenizer implements PlaceTokenizer<MyUserSettingsPlace> {
        public static final String PLACE_TOKEN = "settings";

        @Override
        public String getToken(MyUserSettingsPlace place) {
            HomeDisplay homeDisplay = (HomeDisplay) GwtAdminGlobals.getInstance().getDisplay(HomeDisplay.class);
            homeDisplay.showView(AdminDeckEnum.UserSettings);
            
            return "user";            
        }

        @Override
        public MyUserSettingsPlace getPlace(String token) {
            return new MyUserSettingsPlace();
        }
        
    }

}
