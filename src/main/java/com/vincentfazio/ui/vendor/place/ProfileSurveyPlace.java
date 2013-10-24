package com.vincentfazio.ui.vendor.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;
import com.vincentfazio.ui.bean.QuestionBean;
import com.vincentfazio.ui.vendor.global.GwtVendorGlobals;
import com.vincentfazio.ui.vendor.view.VendorHomeDisplay.VendorDeckEnum;
import com.vincentfazio.ui.view.HomeDisplay;

public class ProfileSurveyPlace extends Place {

    private QuestionBean question;
    private String vendorId;

    public ProfileSurveyPlace(String vendorId, QuestionBean question) {
        this.vendorId = vendorId;
        this.question = question;

        HomeDisplay display = (HomeDisplay) GwtVendorGlobals.getInstance().getDisplay(HomeDisplay.class);
        display.showView(VendorDeckEnum.ProfileSurvey);
        
    }

    public QuestionBean getQuestion() {
        return question;
    }
    
    public String getVendorId() {
        return vendorId;
    }

    @Prefix(Tokenizer.PLACE_TOKEN)
    public static class Tokenizer implements PlaceTokenizer<ProfileSurveyPlace> {
        public static final String PLACE_TOKEN = "profile";

        @Override
        public String getToken(ProfileSurveyPlace place) {
            if (null == place.getQuestion()) {
                return place.getVendorId();
            } else {
                return place.getVendorId() + "&" + place.getQuestion().getQuestionId();                
            }
        }

        @Override
        public ProfileSurveyPlace getPlace(String token) {
            String[] tokens = token.split("&");
            if (tokens.length > 1) {
                QuestionBean question = new QuestionBean();
                question.setQuestionId(tokens[1]);
                return new ProfileSurveyPlace(tokens[0], question);                
            } else {
                return new ProfileSurveyPlace(tokens[0], null);                                
            }
        }
        
    }

}
