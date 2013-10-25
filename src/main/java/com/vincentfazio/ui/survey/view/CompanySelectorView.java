package com.vincentfazio.ui.survey.view;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SuggestOracle.Suggestion;
import com.google.gwt.user.client.ui.Widget;
import com.vincentfazio.ui.survey.activity.CompanyAccessActivity;
import com.vincentfazio.ui.survey.activity.CompanyAccessActivity.CompanyAccessDisplayType;
import com.vincentfazio.ui.survey.global.GwtSurveyGlobals;
import com.vincentfazio.ui.view.component.ValidationSuggestBox;

public class CompanySelectorView extends Composite implements CompanySelectorDisplay {

    private static CompanySelectorViewUiBinder uiBinder = GWT.create(CompanySelectorViewUiBinder.class);

    interface CompanySelectorViewUiBinder extends UiBinder<Widget, CompanySelectorView> {
    }

    @UiField
    Label companyId;

    @UiField
    ValidationSuggestBox companySearchBox;
    
    private boolean allowCompanyChange = false;
    
    private CompanyAccessDisplayType displayType;
    
    public CompanySelectorView(final CompanySelectionHandler companySelectionHandler, CompanyAccessDisplayType displayType) {
        this.displayType = displayType;
        
        initWidget(uiBinder.createAndBindUi(this));

        companyId.addClickHandler(new ClickHandler() { 
            
            @Override
            public void onClick(ClickEvent event) {
                if (allowCompanyChange) {
                    showCompanyChange(true);
                    companySearchBox.setFocus(true);
                }
            }
        });
        
        companySearchBox.getValueBox().addBlurHandler(new BlurHandler() {           
            @Override
            public void onBlur(BlurEvent event) {
                showCompanyChange(false);
            }
        });
        
        companySearchBox.addSelectionHandler(new SelectionHandler<Suggestion>() { 
            @Override
            public void onSelection(SelectionEvent<Suggestion> event) {
                showCompanyChange(false);
                setCompanyId(companySearchBox.getText());
                companySelectionHandler.handleCompanyChange(companyId.getText());
            }
        });

        companySearchBox.addValueChangeHandler(new ValueChangeHandler<String>() {           
            @Override
            public void onValueChange(ValueChangeEvent<String> event) {
                showCompanyChange(false);
                companySelectionHandler.handleCompanyChange(companyId.getText());
            }
        });
        
    }

    public String getCompanyId() {
        return companyId.getText();
    }

    public void setCompanyId(String companyId) {
        if (companySearchBox.noSuggestedItems()) {
            new CompanyAccessActivity(GwtSurveyGlobals.getInstance(), displayType).start(null, null);
        }

        if (!getCompanyId().equals(companyId)) {
            this.companyId.setText(companyId);
            this.companySearchBox.setValue(companyId, null != companyId);
        }
    }

    public void setCompanySearchOptions(List<String> companyList) {        
        this.companySearchBox.setSuggestedItems(companyList);
        
        allowCompanyChange = companyList.size() > 1;
        
        if (!companyList.contains(getCompanyId())) {
            String companyId = companyList.iterator().next();
            setCompanyId(companyId);
        }

    }
        
    public void showCompanyChange(boolean showCompanyChange) {
        companySearchBox.setVisible(showCompanyChange);            
        companyId.setVisible(!showCompanyChange);
    }
    
    public interface CompanySelectionHandler {
        void handleCompanyChange(String companyId);
    }
}
