package com.vincentfazio.ui.vendor.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;
import com.vincentfazio.ui.bean.DocumentationBean;
import com.vincentfazio.ui.vendor.global.GwtVendorGlobals;
import com.vincentfazio.ui.vendor.view.VendorHomeDisplay.VendorDeckEnum;
import com.vincentfazio.ui.view.HomeDisplay;

public class DocumentationPlace extends Place {

    private DocumentationBean  documentation;
    private String vendorId;

    public DocumentationPlace(String vendorId, DocumentationBean documentation) {
        this.vendorId = vendorId;
        this.documentation = documentation;

        HomeDisplay display = (HomeDisplay) GwtVendorGlobals.getInstance().getDisplay(HomeDisplay.class);
        display.showView(VendorDeckEnum.Documentation);
    }

    public DocumentationBean getDocumentation() {
        return documentation;
    }
    
    public String getVendorId() {
        return vendorId;
    }
    
    @Prefix(Tokenizer.PLACE_TOKEN)
    public static class Tokenizer implements PlaceTokenizer<DocumentationPlace> {
        public static final String PLACE_TOKEN = "documentation";

        @Override
        public String getToken(DocumentationPlace place) {
            if (null == place.getDocumentation()) {
                return place.getVendorId();
            } else {
                return place.getVendorId() + "&" + place.getDocumentation().getDocumentationName();                
            }
        }

        @Override
        public DocumentationPlace getPlace(String token) {
            String[] tokens = token.split("&");
            if (tokens.length > 1) {
            	DocumentationBean documentation = new DocumentationBean();
            	documentation.setDocumentationName(tokens[1]);
                return new DocumentationPlace(tokens[0], documentation);                
            } else {
                return new DocumentationPlace(tokens[0], null);                                
            }
        }
    }

}
