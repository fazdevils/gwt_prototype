package com.vincentfazio.ui.administration.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SuggestOracle.Suggestion;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.vincentfazio.ui.administration.activity.DeleteVendorActivity;
import com.vincentfazio.ui.administration.activity.VendorDetailsSaveActivity;
import com.vincentfazio.ui.administration.activity.VendorDnbRefreshActivity;
import com.vincentfazio.ui.administration.global.GwtAdminGlobals;
import com.vincentfazio.ui.administration.place.VendorDetailsPlace;
import com.vincentfazio.ui.administration.place.VendorUserPermissionsPlace;
import com.vincentfazio.ui.bean.VendorDetailsBean;
import com.vincentfazio.ui.view.component.CountryValidationSuggestBox;
import com.vincentfazio.ui.view.component.StateValidationSuggestBox;
import com.vincentfazio.ui.view.component.ValidationTextBox;

public class VendorDetailsView extends Composite implements VendorDetailsDisplay {

    private static VendorDetailsUiBinder uiBinder = GWT.create(VendorDetailsUiBinder.class);


    interface VendorDetailsUiBinder extends UiBinder<Widget, VendorDetailsView> {
    }


    public VendorDetailsView() {
        initWidget(uiBinder.createAndBindUi(this));
        
        url.addClickHandler(new ClickHandler() { 
             @Override
            public void onClick(ClickEvent arg0) {
                 if (url.getText().isEmpty()) {
                     url.setText("http://");
                 }
            }
        });
        url.addKeyUpHandler(new KeyUpHandler() {
            @Override
            public void onKeyUp(KeyUpEvent event) {
                setHasUnsavedChanges(true);
            }
        });   
        address.addKeyUpHandler(new KeyUpHandler() {
            @Override
            public void onKeyUp(KeyUpEvent event) {
                setHasUnsavedChanges(true);
            }
        });
        city.addKeyUpHandler(new KeyUpHandler() {
            @Override
            public void onKeyUp(KeyUpEvent event) {
                setHasUnsavedChanges(true);
            }
        });
        state.addKeyUpHandler(new KeyUpHandler() {
            @Override
            public void onKeyUp(KeyUpEvent event) {
                setHasUnsavedChanges(true);  
            }
        });
        state.addSelectionHandler(new SelectionHandler<Suggestion>() { 
            @Override
            public void onSelection(SelectionEvent<Suggestion> event) {
                setHasUnsavedChanges(true);
            }
        });
        zip.addKeyUpHandler(new KeyUpHandler() {
            @Override
            public void onKeyUp(KeyUpEvent event) {
                setHasUnsavedChanges(true);
            }
        });
        phone.addKeyUpHandler(new KeyUpHandler() {
            @Override
            public void onKeyUp(KeyUpEvent event) {
                setHasUnsavedChanges(true);
            }
        });
        country.addKeyUpHandler(new KeyUpHandler() {
            @Override
            public void onKeyUp(KeyUpEvent event) {
                setHasUnsavedChanges(true);  
            }
        });       
        country.addSelectionHandler(new SelectionHandler<Suggestion>() { 
            @Override
            public void onSelection(SelectionEvent<Suggestion> event) {
                setHasUnsavedChanges(true);
            }
        });        
        stockExchange.addKeyUpHandler(new KeyUpHandler() {
            @Override
            public void onKeyUp(KeyUpEvent event) {
                setHasUnsavedChanges(true);
            }
        });
        stockSymbol.addKeyUpHandler(new KeyUpHandler() {
            @Override
            public void onKeyUp(KeyUpEvent event) {
                setHasUnsavedChanges(true);
            }
        });
        experianBin.addKeyUpHandler(new KeyUpHandler() {
            @Override
            public void onKeyUp(KeyUpEvent event) {
                setHasUnsavedChanges(true);
            }
        });
        dunsNumber.addKeyUpHandler(new KeyUpHandler() {
            @Override
            public void onKeyUp(KeyUpEvent event) {
                setHasUnsavedChanges(true);
            }
        });
        province.addKeyUpHandler(new KeyUpHandler() {
            @Override
            public void onKeyUp(KeyUpEvent event) {
                setHasUnsavedChanges(true);
            }
        });
        postalCode.addKeyUpHandler(new KeyUpHandler() {
            @Override
            public void onKeyUp(KeyUpEvent event) {
                setHasUnsavedChanges(true);
            }
        });
        internationalPhone.addKeyUpHandler(new KeyUpHandler() {
            @Override
            public void onKeyUp(KeyUpEvent event) {
                setHasUnsavedChanges(true);
            }
        });

        undoButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                String currentVendorId = getVendorId();
                setVendorId("");
                setHasUnsavedChanges(false);
                GwtAdminGlobals.getInstance().gotoPlace(new VendorDetailsPlace(currentVendorId));                
            }
        });

        
        saveButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                new VendorDetailsSaveActivity(GwtAdminGlobals.getInstance(), getVendorDetails()).start(null, null);
            }
        });
        
        deleteButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                if (Window.confirm("Are you sure you want to permanently delete this supplier?")) {
                    new DeleteVendorActivity(GwtAdminGlobals.getInstance(), getVendorId()).start(null, null);
                }
            }
        });
        
        customerAccessButton.addClickHandler(new ClickHandler() {     
            @Override
            public void onClick(ClickEvent event) {
                if (!hasChanges) {
                    GwtAdminGlobals.getInstance().gotoPlace(new VendorUserPermissionsPlace(vendorId.getText(), "Customer"));
                }
            }
        });
        
        dnbRequestButton.addClickHandler(new ClickHandler() {     
            @Override
            public void onClick(ClickEvent event) {
                if (!hasChanges) {
                    new VendorDnbRefreshActivity(GwtAdminGlobals.getInstance(), getVendorId()).start(null, null);
                }
            }
        });

        GwtAdminGlobals.getInstance().registerDisplay(VendorDetailsDisplay.class, this);
    }

    @UiField
    Label vendorId;

    @UiField
    ValidationTextBox url;
    
    @UiField
    TextBox address;

    @UiField
    TextBox city;
    
    @UiField
    StateValidationSuggestBox state;
    
    @UiField
    ValidationTextBox zip;
    
    @UiField
    ValidationTextBox phone;
    
    @UiField
    CountryValidationSuggestBox country;
    
    @UiField
    TextBox stockExchange;
    
    @UiField
    TextBox stockSymbol;
    
    @UiField
    ValidationTextBox dunsNumber;
    
    @UiField
    ValidationTextBox experianBin;

    @UiField
    DivElement saveDiv;

    @UiField
    DivElement deleteDiv;

    @UiField
    Anchor undoButton;
    
    @UiField
    Button saveButton;

    @UiField
    Anchor deleteButton;
    
    @UiField
    Anchor customerAccessButton;

    @UiField
    Anchor dnbRequestButton;
    
    @UiField
    DivElement usDiv;
        
    @UiField
    DivElement internationalDiv;
    
    @UiField
    TextBox province;
    
    @UiField
    TextBox postalCode;
    
    @UiField
    TextBox internationalPhone;
    

    private boolean hasChanges = false;
    
     
    private String getVendorId() {
        return vendorId.getText();
    }

    private String getUrl() {
        return url.getText();
    }

    private String getAddress() {
        return address.getText();
    }

    private String getCity() {
        return city.getText();
    }

    private String getState() {
        return state.getText();
    }

    private String getZip() {
        return zip.getText();
    }

    private String getPhone() {
        return phone.getText();
    }

    private String getCountry() {
        return country.getText();
    }

    private String getStockExchange() {
        return stockExchange.getText();
    }
    
    private String getStockSymbol() {
        return stockSymbol.getText();
    }
    private String getDunsNumber() {
        return dunsNumber.getText();
    }
    private String getExperianBin() {
        return experianBin.getText();
    }
    private String getPostalCode() {
        return postalCode.getText();
    }
    private String getInternationalPhone() {
        return internationalPhone.getText();
    }
    private String getProvince() {
        return province.getText();
    }

    private void setVendorId(String vendorId) {
        this.vendorId.setText(vendorId);
    }

    private void setUrl(String url) {
        this.url.setText(url);
    }

    private void setAddress(String address) {
        this.address.setText(address);
    }

    private void setCity(String city) {
        this.city.setText(city);
    }

    private void setState(String state) {
        this.state.setText(state);
    }
    
    private void setZip(String zip) {
        this.zip.setText(zip);
    }

    private void setPhone(String phone) {
        this.phone.setText(phone);
    }

    private void setCountry(String country) {
        this.country.setText(country);
    }
    
    private void setStockExchange(String stockExchange) {
        this.stockExchange.setText(stockExchange);
    }

    private void setStockSymbol(String stockSymbol) {
        this.stockSymbol.setText(stockSymbol);
    }

    private void setDunsNumber(String dunsNumber) {
        this.dunsNumber.setText(dunsNumber);
    }

    private void setExperianBin(String experianBin) {
        this.experianBin.setText(experianBin);
    }

    private void setPostalCode(String postalCode) {
        this.postalCode.setText(postalCode);
    }

    private void setInternationalPhone(String internationalPhone) {
        this.internationalPhone.setText(internationalPhone);
    }

    private void setProvince(String province) {
        this.province.setText(province);
    }

    @Override
    public void setVendorDetails(VendorDetailsBean vendor) {        
        resetDisplay();
        
        setVendorId(vendor.getVendorId());
        setUrl(vendor.getUrl());
        setAddress(vendor.getAddress());
        setCity(vendor.getCity());
        setCountry(vendor.getCountry());
        if (isUsCountry()) {
            setState(vendor.getState());
            setZip(vendor.getZip());
            setPhone(vendor.getPhone());            
        } else {
            setProvince(vendor.getState());
            setPostalCode(vendor.getZip());
            setInternationalPhone(vendor.getPhone());                        
        }
        setStockExchange(vendor.getStockExchange());
        setStockSymbol(vendor.getStockSymbol());
        setDunsNumber(vendor.getDunsNumber());
        setExperianBin(vendor.getExperianBin());

        setHasUnsavedChanges(false);
    }

    @Override
    public VendorDetailsBean getVendorDetails() {
        VendorDetailsBean vendor = new VendorDetailsBean();
        
        vendor.setVendorId(getVendorId());
        vendor.setUrl(getUrl());
        vendor.setAddress(getAddress());
        vendor.setCity(getCity());
        vendor.setCountry(getCountry());
        if (isUsCountry()) {
            vendor.setState(getState());
            vendor.setZip(getZip());
            vendor.setPhone(getPhone());            
        } else {
            vendor.setState(getProvince());
            vendor.setZip(getPostalCode());
            vendor.setPhone(getInternationalPhone());            
        }
        vendor.setStockExchange(getStockExchange());
        vendor.setStockSymbol(getStockSymbol());
        vendor.setDunsNumber(getDunsNumber());
        vendor.setExperianBin(getExperianBin());
        
        return vendor;
    }

    @Override
    public boolean isValid() {
        boolean usIsValid = true;
        if (isUsCountry()) {
            usIsValid = state.isValid() && zip.isValid() && phone.isValid();
        }
        return url.isValid() && usIsValid && country.isValid() && dunsNumber.isValid() && experianBin.isValid();
    }

    private void resetDisplay() {
        setVendorId(null);
        setUrl(null);
        setAddress(null);
        setCity(null);
        setState(null);
        setZip(null);
        setPhone(null);
        setCountry(null);
        setStockExchange(null);
        setStockSymbol(null);
        setDunsNumber(null);
        setExperianBin(null);
        setPostalCode(null);
        setProvince(null);
        setInternationalPhone(null);
        
        setHasUnsavedChanges(false);

    }

    @Override
    public void setHasUnsavedChanges(boolean hasUnsavedChanges) {
        hasChanges = hasUnsavedChanges;
        
        Style divStyle = saveDiv.getStyle();
        Style deleteDivStyle = deleteDiv.getStyle();
        if (hasChanges ) {
            deleteDivStyle.setDisplay(Display.NONE);
            divStyle.setDisplay(Display.BLOCK);            
            saveButton.setEnabled(isValid());
            
            customerAccessButton.setTitle("Save changes to enable this link");
            dnbRequestButton.setTitle("Save changes to enable this link");
            
            customerAccessButton.addStyleName("disabled");
            dnbRequestButton.addStyleName("disabled");

        } else {
            deleteDivStyle.setDisplay(Display.BLOCK);
            divStyle.setDisplay(Display.NONE);            
            url.resetValidation();
            state.resetValidation();
            zip.resetValidation();
            phone.resetValidation();
            country.resetValidation();
            dunsNumber.resetValidation();
            experianBin.resetValidation();
            
            customerAccessButton.setTitle(null);
            dnbRequestButton.setTitle(null);
            
            customerAccessButton.removeStyleName("disabled");
            dnbRequestButton.removeStyleName("disabled");

        }

        setCountrySpecificFields();
    }

    @Override
    public boolean hasUnsavedChanges() {
        return hasChanges;
    }

    private void setCountrySpecificFields() {    
        Style usDivStyle = usDiv.getStyle();
        Style internationalDivStyle = internationalDiv.getStyle();
        if (isUsCountry()) {
            usDivStyle.setDisplay(Display.BLOCK);
            internationalDivStyle.setDisplay(Display.NONE);
        } else {
            usDivStyle.setDisplay(Display.NONE);
            internationalDivStyle.setDisplay(Display.BLOCK);
        }
    }

    private boolean isUsCountry() {
        String country = getCountry();
        return (null == country) || (country.isEmpty()) || country.equalsIgnoreCase("us");
    }
    
}
