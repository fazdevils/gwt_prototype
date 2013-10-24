package com.vincentfazio.ui.vendor.view;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;

import com.google.gwt.animation.client.Animation;
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
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.StackLayoutPanel;
import com.google.gwt.user.client.ui.SuggestOracle.Suggestion;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.vincentfazio.ui.animation.BackgroundFadeOutAnimation;
import com.vincentfazio.ui.animation.Color;
import com.vincentfazio.ui.bean.TaskBean;
import com.vincentfazio.ui.bean.TaskType;
import com.vincentfazio.ui.bean.VendorDetailsBean;
import com.vincentfazio.ui.vendor.activity.CompleteTaskActivity;
import com.vincentfazio.ui.vendor.activity.VendorDetailsActivity;
import com.vincentfazio.ui.vendor.activity.VendorDetailsSaveActivity;
import com.vincentfazio.ui.vendor.activity.VendorAccessActivity.VendorAccessDisplayType;
import com.vincentfazio.ui.vendor.global.GwtVendorGlobals;
import com.vincentfazio.ui.vendor.place.VendorDetailsPlace;
import com.vincentfazio.ui.view.component.CountryValidationSuggestBox;
import com.vincentfazio.ui.view.component.NotificationAcknowledgementCallback;
import com.vincentfazio.ui.view.component.StateValidationSuggestBox;
import com.vincentfazio.ui.view.component.ValidationTextBox;

public class VendorDetailsView extends Composite implements VendorDetailsDisplay, NotificationAcknowledgementCallback {

    private static VendorDetailsUiBinder uiBinder = GWT.create(VendorDetailsUiBinder.class);


    interface VendorDetailsUiBinder extends UiBinder<Widget, VendorDetailsView> {
    }


    public VendorDetailsView() {
        vendorSelector = new VendorSelectorView(new VendorSelectorView.VendorSelectionHandler() {           
            @Override
            public void handleVendorChange(String vendorId) {               
                GwtVendorGlobals.getInstance().gotoPlace(new VendorDetailsPlace(vendorId));
            }
        }, VendorAccessDisplayType.VendorDetails);
        
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
                vendorSelector.showVendorChange(false);
                setHasUnsavedChanges(false);
                new VendorDetailsActivity(currentVendorId, GwtVendorGlobals.getInstance()).start(null, null);                
            }
        });
        
        saveButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                new VendorDetailsSaveActivity(GwtVendorGlobals.getInstance(), getVendorDetails()).start(null, null);
            }
        });       
        
        demographicsVerificationButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                new CompleteTaskActivity(openVendorDemographicsTask,  GwtVendorGlobals.getInstance()).start(null, null);                
            }
        });
        
        
        vendorSelector.showVendorChange(false);
        
        GwtVendorGlobals.getInstance().registerDisplay(VendorDetailsDisplay.class, this);

    }

    @UiField
    Panel vendorDetailsPanel;
    
    @UiField
    StackLayoutPanel taskMenu;
    
    @UiField
    TaskView activeTasks;
    
    @UiField
    TaskView completedTasks;
       
    @UiField(provided=true)
    VendorSelectorView vendorSelector;
       
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
    Anchor undoButton;
    
    @UiField
    Button saveButton;
    
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

    @UiField
    Button demographicsVerificationButton;

    private boolean hasChanges = false;
    
    private boolean acknowledgedAlert = false;

    private TaskBean openVendorDemographicsTask = null;
     
    private String getVendorId() {
        return vendorSelector.getVendorId();
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

    @Override
    public void setVendorSearchOptions(List<String> vendorList) {  
        vendorSelector.setVendorSearchOptions(vendorList);
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
        vendorSelector.setVendorId(vendor.getVendorId());
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
        setOpenVendorDemographicsTask();            

    }

    @Override
    public VendorDetailsBean getVendorDetails() {
        VendorDetailsBean vendor = new VendorDetailsBean();
        
        vendor.setVendorId(vendorSelector.getVendorId());
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

    @Override
    public void setHasUnsavedChanges(boolean hasUnsavedChanges) {
        hasChanges = hasUnsavedChanges;
        
        Style divStyle = saveDiv.getStyle();
        if (hasChanges ) {
            divStyle.setDisplay(Display.BLOCK);            
            saveButton.setEnabled(isValid());
        } else {
            divStyle.setDisplay(Display.NONE);            
            url.resetValidation();
            state.resetValidation();
            zip.resetValidation();
            phone.resetValidation();
            country.resetValidation();
            dunsNumber.resetValidation();
            experianBin.resetValidation();
        }

        setCountrySpecificFields();
    }

    @Override
    public boolean hasUnsavedChanges() {
        return hasChanges;
    }

    @Override
    public void setActiveTasks(SortedSet<TaskBean> tasks) {
        activeTasks.setTasks(tasks, createNotificationAnimations(tasks), !acknowledgedAlert, this);      
        setOpenVendorDemographicsTask();
    }

    private void setOpenVendorDemographicsTask() {
        openVendorDemographicsTask = null;
        for (TaskBean task: activeTasks.getTasks()) {
            if (openVendorDemographicsTask(task)) {
                openVendorDemographicsTask = task;
                break;
            }
        }
        boolean showDemographicsVerificationButton = null != openVendorDemographicsTask;
        demographicsVerificationButton.setVisible(showDemographicsVerificationButton);        
    }

    public ArrayList<Animation> createNotificationAnimations(SortedSet<TaskBean> tasks) {
        ArrayList<Animation> animations = new ArrayList<Animation>();
        
        if (tasks.size() > 0) {
            TaskBean firstTask = tasks.first();
            if (openVendorDemographicsTask(firstTask)) {
                BackgroundFadeOutAnimation animation = createVendorPanelAnimation();
                animations.add(animation);
            }    
            
        }
        
        return animations;
    }

    private boolean openVendorDemographicsTask(TaskBean task) {
        return task.getTaskType().equals(TaskType.DemographicSurvey) && task.getVendor().equals(vendorSelector.getVendorId());
    }

    private BackgroundFadeOutAnimation createVendorPanelAnimation() {
        Color bgColor = GwtVendorGlobals.getInstance().getNotificationColor();
        BackgroundFadeOutAnimation animation = new BackgroundFadeOutAnimation(vendorDetailsPanel.getElement(), bgColor);
        return animation;
    }

    @Override
    public void highlightVendorDetailsPane() {
        createVendorPanelAnimation().run(1500);
    }
    
    @Override
    public void acknowledgeNotification() {
        acknowledgedAlert = true;
    }

    @Override
    public void setCompletedTasks(SortedSet<TaskBean> tasks) {
        completedTasks.setTasks(tasks, null, false, null);
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
