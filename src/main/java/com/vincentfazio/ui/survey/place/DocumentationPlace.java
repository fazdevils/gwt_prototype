package com.vincentfazio.ui.survey.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;
import com.vincentfazio.ui.bean.DocumentationBean;
import com.vincentfazio.ui.survey.global.GwtSurveyGlobals;
import com.vincentfazio.ui.survey.view.CompanyHomeDisplay.CompanyDeckEnum;
import com.vincentfazio.ui.view.HomeDisplay;

public class DocumentationPlace extends Place {

    private DocumentationBean  documentation;
    private String companyId;

    public DocumentationPlace(String companyId, DocumentationBean documentation) {
        this.companyId = companyId;
        this.documentation = documentation;

        HomeDisplay display = (HomeDisplay) GwtSurveyGlobals.getInstance().getDisplay(HomeDisplay.class);
        display.showView(CompanyDeckEnum.Documentation);
    }

    public DocumentationBean getDocumentation() {
        return documentation;
    }
    
    public String getCompanyId() {
        return companyId;
    }
    
    @Prefix(Tokenizer.PLACE_TOKEN)
    public static class Tokenizer implements PlaceTokenizer<DocumentationPlace> {
        public static final String PLACE_TOKEN = "documentation";

        @Override
        public String getToken(DocumentationPlace place) {
            if (null == place.getDocumentation()) {
                return place.getCompanyId();
            } else {
                return place.getCompanyId() + "&" + place.getDocumentation().getDocumentationName();                
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
