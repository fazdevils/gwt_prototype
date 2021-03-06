package com.vincentfazio.ui.survey.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;
import com.vincentfazio.ui.bean.QuestionBean;
import com.vincentfazio.ui.survey.global.GwtSurveyGlobals;
import com.vincentfazio.ui.survey.view.CompanyHomeDisplay.CompanyDeckEnum;
import com.vincentfazio.ui.view.HomeDisplay;

public class SecuritySurveyPlace extends Place {

    private QuestionBean question;
    private String companyId;

    public SecuritySurveyPlace(String companyId, QuestionBean question) {
        this.companyId = companyId;
        this.question = question;

        HomeDisplay display = (HomeDisplay) GwtSurveyGlobals.getInstance().getDisplay(HomeDisplay.class);
        display.showView(CompanyDeckEnum.SecuritySurvey);
    }

    public QuestionBean getQuestion() {
        return question;
    }
    
    public String getCompanyId() {
        return companyId;
    }
    
    @Prefix(Tokenizer.PLACE_TOKEN)
    public static class Tokenizer implements PlaceTokenizer<SecuritySurveyPlace> {
        public static final String PLACE_TOKEN = "security";

        @Override
        public String getToken(SecuritySurveyPlace place) {
            if (null == place.getQuestion()) {
                return place.getCompanyId();
            } else {
                return place.getCompanyId() + "&" + place.getQuestion().getQuestionId();                
            }
        }

        @Override
        public SecuritySurveyPlace getPlace(String token) {
            String[] tokens = token.split("&");
            if (tokens.length > 1) {
                QuestionBean question = new QuestionBean();
                question.setQuestionId(tokens[1]);
                return new SecuritySurveyPlace(tokens[0], question);                
            } else {
                return new SecuritySurveyPlace(tokens[0], null);                                
            }
        }
    }

}
