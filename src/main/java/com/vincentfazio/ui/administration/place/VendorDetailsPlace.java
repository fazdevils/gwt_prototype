package com.vincentfazio.ui.administration.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;
import com.vincentfazio.ui.administration.global.GwtAdminGlobals;
import com.vincentfazio.ui.administration.view.VendorListDisplay;
import com.vincentfazio.ui.administration.view.AdminHomeDisplay.AdminDeckEnum;
import com.vincentfazio.ui.administration.view.VendorListDisplay.VendorDeckEnum;
import com.vincentfazio.ui.view.HomeDisplay;

public class VendorDetailsPlace extends Place {

    private String vendorId;

    public VendorDetailsPlace(String token) {
        this.vendorId = token;

        HomeDisplay homeDisplay = (HomeDisplay) GwtAdminGlobals.getInstance().getDisplay(HomeDisplay.class);
        homeDisplay.showView(AdminDeckEnum.MainNavigation);
        
        VendorListDisplay vendorListDisplay = (VendorListDisplay) GwtAdminGlobals.getInstance().getDisplay(VendorListDisplay.class);
        vendorListDisplay.showView(VendorDeckEnum.VendorDetails);
    }

    public String getVendorId() {
        return vendorId;
    }
    
    @Prefix(Tokenizer.PLACE_TOKEN)
    public static class Tokenizer implements PlaceTokenizer<VendorDetailsPlace> {
        public static final String PLACE_TOKEN = "vendor";

        @Override
        public String getToken(VendorDetailsPlace place) {
            return place.getVendorId();
        }

        @Override
        public VendorDetailsPlace getPlace(String token) {
            return new VendorDetailsPlace(token);
        }
        
    }

}
