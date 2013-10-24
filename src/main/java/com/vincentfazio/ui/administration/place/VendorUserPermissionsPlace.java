package com.vincentfazio.ui.administration.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;
import com.vincentfazio.ui.administration.global.GwtAdminGlobals;
import com.vincentfazio.ui.administration.view.VendorListDisplay;
import com.vincentfazio.ui.administration.view.AdminHomeDisplay.AdminDeckEnum;
import com.vincentfazio.ui.administration.view.VendorListDisplay.VendorDeckEnum;
import com.vincentfazio.ui.view.HomeDisplay;

public class VendorUserPermissionsPlace extends Place {

    private String vendorId;
    private String role;

    public VendorUserPermissionsPlace(String vendorId, String role) {
        this.vendorId = vendorId;
        this.role = role;

        HomeDisplay homeDisplay = (HomeDisplay) GwtAdminGlobals.getInstance().getDisplay(HomeDisplay.class);
        homeDisplay.showView(AdminDeckEnum.MainNavigation);
        
        VendorListDisplay vendorListDisplay = (VendorListDisplay) GwtAdminGlobals.getInstance().getDisplay(VendorListDisplay.class);
        vendorListDisplay.showView(VendorDeckEnum.VendorUserPermissions);
    }

    public String getVendorId() {
        return vendorId;
    }
    
    public String getRole() {
        return role;
    }

    @Prefix(Tokenizer.PLACE_TOKEN)
    public static class Tokenizer implements PlaceTokenizer<VendorUserPermissionsPlace> {
        public static final String PLACE_TOKEN = "vendorp";

        @Override
        public String getToken(VendorUserPermissionsPlace place) {
            return place.getVendorId() + "&" + place.getRole().toLowerCase();
        }

        @Override
        public VendorUserPermissionsPlace getPlace(String token) {
            String[] tokens = token.split("&");
            return new VendorUserPermissionsPlace(tokens[0], tokens[1]);
        }
        
    }

}
