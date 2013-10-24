package com.vincentfazio.ui.vendor.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;
import com.vincentfazio.ui.vendor.global.GwtVendorGlobals;
import com.vincentfazio.ui.vendor.view.VendorHomeDisplay.VendorDeckEnum;
import com.vincentfazio.ui.view.HomeDisplay;

public class VendorDetailsPlace extends Place {

    private String vendorId;

    public VendorDetailsPlace(String token) {
        this.vendorId = token;

        HomeDisplay display = (HomeDisplay) GwtVendorGlobals.getInstance().getDisplay(HomeDisplay.class);
        display.showView(VendorDeckEnum.VendorDetails);
    }

    public String getVendorId() {
        return vendorId;
    }
    
    @Prefix(Tokenizer.PLACE_TOKEN)
    public static class Tokenizer implements PlaceTokenizer<VendorDetailsPlace> {
        public static final String PLACE_TOKEN = "vendor";

        @Override
        public String getToken(VendorDetailsPlace place) {
            if (null == place.getVendorId()) {
                return "";
            }
            return place.getVendorId();
        }

        @Override
        public VendorDetailsPlace getPlace(String token) {
            return new VendorDetailsPlace(token);
        }
        
    }

}
