package com.vincentfazio.ui.vendor.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;
import com.vincentfazio.ui.vendor.global.GwtVendorGlobals;
import com.vincentfazio.ui.vendor.view.VendorHomeDisplay.VendorDeckEnum;
import com.vincentfazio.ui.view.HomeDisplay;

public class VendorAccessPlace extends Place {

    public VendorAccessPlace() {
        super();
        HomeDisplay display = (HomeDisplay) GwtVendorGlobals.getInstance().getDisplay(HomeDisplay.class);
        display.showView(VendorDeckEnum.VendorDetails);
    }

    @Prefix(Tokenizer.PLACE_TOKEN)
    public static class Tokenizer implements PlaceTokenizer<VendorAccessPlace> {
        public static final String PLACE_TOKEN = "home";

        @Override
        public String getToken(VendorAccessPlace place) {
            return "vendor";            
        }

        @Override
        public VendorAccessPlace getPlace(String token) {
            return new VendorAccessPlace();
        }
        
    }


}
