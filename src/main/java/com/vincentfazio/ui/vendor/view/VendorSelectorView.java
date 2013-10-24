package com.vincentfazio.ui.vendor.view;

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
import com.vincentfazio.ui.vendor.activity.VendorAccessActivity;
import com.vincentfazio.ui.vendor.activity.VendorAccessActivity.VendorAccessDisplayType;
import com.vincentfazio.ui.vendor.global.GwtVendorGlobals;
import com.vincentfazio.ui.view.component.ValidationSuggestBox;

public class VendorSelectorView extends Composite implements VendorSelectorDisplay {

    private static VendorSelectorViewUiBinder uiBinder = GWT.create(VendorSelectorViewUiBinder.class);

    interface VendorSelectorViewUiBinder extends UiBinder<Widget, VendorSelectorView> {
    }

    @UiField
    Label vendorId;

    @UiField
    ValidationSuggestBox vendorSearchBox;
    
    private boolean allowVendorChange = false;
    
    private VendorAccessDisplayType displayType;
    
    public VendorSelectorView(final VendorSelectionHandler vendorSelectionHandler, VendorAccessDisplayType displayType) {
        this.displayType = displayType;
        
        initWidget(uiBinder.createAndBindUi(this));

        vendorId.addClickHandler(new ClickHandler() { 
            
            @Override
            public void onClick(ClickEvent event) {
                if (allowVendorChange) {
                    showVendorChange(true);
                    vendorSearchBox.setFocus(true);
                }
            }
        });
        
        vendorSearchBox.getValueBox().addBlurHandler(new BlurHandler() {           
            @Override
            public void onBlur(BlurEvent event) {
                showVendorChange(false);
            }
        });
        
        vendorSearchBox.addSelectionHandler(new SelectionHandler<Suggestion>() { 
            @Override
            public void onSelection(SelectionEvent<Suggestion> event) {
                showVendorChange(false);
                setVendorId(vendorSearchBox.getText());
                vendorSelectionHandler.handleVendorChange(vendorId.getText());
            }
        });

        vendorSearchBox.addValueChangeHandler(new ValueChangeHandler<String>() {           
            @Override
            public void onValueChange(ValueChangeEvent<String> event) {
                showVendorChange(false);
                vendorSelectionHandler.handleVendorChange(vendorId.getText());
            }
        });
        
    }

    public String getVendorId() {
        return vendorId.getText();
    }

    public void setVendorId(String vendorId) {
        if (vendorSearchBox.noSuggestedItems()) {
            new VendorAccessActivity(GwtVendorGlobals.getInstance(), displayType).start(null, null);
        }

        if (!getVendorId().equals(vendorId)) {
            this.vendorId.setText(vendorId);
            this.vendorSearchBox.setValue(vendorId, null != vendorId);
        }
    }

    public void setVendorSearchOptions(List<String> vendorList) {        
        this.vendorSearchBox.setSuggestedItems(vendorList);
        
        allowVendorChange = vendorList.size() > 1;
        
        if (!vendorList.contains(getVendorId())) {
            String vendorId = vendorList.iterator().next();
            setVendorId(vendorId);
        }

    }
        
    public void showVendorChange(boolean showVendorChange) {
        vendorSearchBox.setVisible(showVendorChange);            
        vendorId.setVisible(!showVendorChange);
    }
    
    public interface VendorSelectionHandler {
        void handleVendorChange(String vendorId);
    }
}
