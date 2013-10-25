package com.vincentfazio.ui.survey.view;

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
import com.vincentfazio.ui.bean.CompanyDetailsBean;
import com.vincentfazio.ui.survey.activity.CompleteTaskActivity;
import com.vincentfazio.ui.survey.activity.CompanyDetailsActivity;
import com.vincentfazio.ui.survey.activity.CompanyDetailsSaveActivity;
import com.vincentfazio.ui.survey.activity.CompanyAccessActivity.CompanyAccessDisplayType;
import com.vincentfazio.ui.survey.global.GwtSurveyGlobals;
import com.vincentfazio.ui.survey.place.CompanyDetailsPlace;
import com.vincentfazio.ui.view.component.CountryValidationSuggestBox;
import com.vincentfazio.ui.view.component.NotificationAcknowledgementCallback;
import com.vincentfazio.ui.view.component.StateValidationSuggestBox;
import com.vincentfazio.ui.view.component.ValidationTextBox;

public class CompanyDetailsView extends Composite implements CompanyDetailsDisplay, NotificationAcknowledgementCallback {

    private static CompanyDetailsUiBinder uiBinder = GWT.create(CompanyDetailsUiBinder.class);


    interface CompanyDetailsUiBinder extends UiBinder<Widget, CompanyDetailsView> {
    }


    public CompanyDetailsView() {
        companySelector = new CompanySelectorView(new CompanySelectorView.CompanySelectionHandler() {           
            @Override
            public void handleCompanyChange(String companyId) {               
                GwtSurveyGlobals.getInstance().gotoPlace(new CompanyDetailsPlace(companyId));
            }
        }, CompanyAccessDisplayType.CompanyDetails);
        
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
                String currentCompanyId = getCompanyId();
                companySelector.showCompanyChange(false);
                setHasUnsavedChanges(false);
                new CompanyDetailsActivity(currentCompanyId, GwtSurveyGlobals.getInstance()).start(null, null);                
            }
        });
        
        saveButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                new CompanyDetailsSaveActivity(GwtSurveyGlobals.getInstance(), getCompanyDetails()).start(null, null);
            }
        });       
        
        demographicsVerificationButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                new CompleteTaskActivity(openCompanyDemographicsTask,  GwtSurveyGlobals.getInstance()).start(null, null);                
            }
        });
        
        
        companySelector.showCompanyChange(false);
        
        GwtSurveyGlobals.getInstance().registerDisplay(CompanyDetailsDisplay.class, this);

    }

    @UiField
    Panel companyDetailsPanel;
    
    @UiField
    StackLayoutPanel taskMenu;
    
    @UiField
    TaskView activeTasks;
    
    @UiField
    TaskView completedTasks;
       
    @UiField(provided=true)
    CompanySelectorView companySelector;
       
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

    private TaskBean openCompanyDemographicsTask = null;
     
    private String getCompanyId() {
        return companySelector.getCompanyId();
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
    public void setCompanySearchOptions(List<String> companyList) {  
        companySelector.setCompanySearchOptions(companyList);
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
    public void setCompanyDetails(CompanyDetailsBean company) {
        companySelector.setCompanyId(company.getCompanyId());
        setUrl(company.getUrl());
        setAddress(company.getAddress());
        setCity(company.getCity());
        setCountry(company.getCountry());
        if (isUsCountry()) {
            setState(company.getState());
            setZip(company.getZip());
            setPhone(company.getPhone());            
        } else {
            setProvince(company.getState());
            setPostalCode(company.getZip());
            setInternationalPhone(company.getPhone());                        
        }
        setStockExchange(company.getStockExchange());
        setStockSymbol(company.getStockSymbol());
        setDunsNumber(company.getDunsNumber());
        setExperianBin(company.getExperianBin());

        setHasUnsavedChanges(false);
        setOpenCompanyDemographicsTask();            

    }

    @Override
    public CompanyDetailsBean getCompanyDetails() {
        CompanyDetailsBean company = new CompanyDetailsBean();
        
        company.setCompanyId(companySelector.getCompanyId());
        company.setUrl(getUrl());
        company.setAddress(getAddress());
        company.setCity(getCity());
        company.setCountry(getCountry());
        if (isUsCountry()) {
            company.setState(getState());
            company.setZip(getZip());
            company.setPhone(getPhone());            
        } else {
            company.setState(getProvince());
            company.setZip(getPostalCode());
            company.setPhone(getInternationalPhone());            
        }
        company.setStockExchange(getStockExchange());
        company.setStockSymbol(getStockSymbol());
        company.setDunsNumber(getDunsNumber());
        company.setExperianBin(getExperianBin());
        
        return company;
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
        setOpenCompanyDemographicsTask();
    }

    private void setOpenCompanyDemographicsTask() {
        openCompanyDemographicsTask = null;
        for (TaskBean task: activeTasks.getTasks()) {
            if (openCompanyDemographicsTask(task)) {
                openCompanyDemographicsTask = task;
                break;
            }
        }
        boolean showDemographicsVerificationButton = null != openCompanyDemographicsTask;
        demographicsVerificationButton.setVisible(showDemographicsVerificationButton);        
    }

    public ArrayList<Animation> createNotificationAnimations(SortedSet<TaskBean> tasks) {
        ArrayList<Animation> animations = new ArrayList<Animation>();
        
        if (tasks.size() > 0) {
            TaskBean firstTask = tasks.first();
            if (openCompanyDemographicsTask(firstTask)) {
                BackgroundFadeOutAnimation animation = createCompanyPanelAnimation();
                animations.add(animation);
            }    
            
        }
        
        return animations;
    }

    private boolean openCompanyDemographicsTask(TaskBean task) {
        return task.getTaskType().equals(TaskType.DemographicSurvey) && task.getCompany().equals(companySelector.getCompanyId());
    }

    private BackgroundFadeOutAnimation createCompanyPanelAnimation() {
        Color bgColor = GwtSurveyGlobals.getInstance().getNotificationColor();
        BackgroundFadeOutAnimation animation = new BackgroundFadeOutAnimation(companyDetailsPanel.getElement(), bgColor);
        return animation;
    }

    @Override
    public void highlightCompanyDetailsPane() {
        createCompanyPanelAnimation().run(1500);
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
