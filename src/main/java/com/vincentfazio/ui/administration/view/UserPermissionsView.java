package com.vincentfazio.ui.administration.view;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SingleSelectionModel;
import com.vincentfazio.ui.administration.activity.UserPermissionsSaveActivity;
import com.vincentfazio.ui.administration.global.GwtAdminGlobals;
import com.vincentfazio.ui.administration.place.UserPermissionsPlace;
import com.vincentfazio.ui.administration.view.component.CompanyPermissionCellComponentCallback;
import com.vincentfazio.ui.bean.CompanyPermissionBean;
import com.vincentfazio.ui.view.component.DataProviderSearchBox;
import com.vincentfazio.ui.view.component.DataProviderSearchBoxUpdateCallback;
import com.vincentfazio.ui.view.util.GwtCellTableResources;

public class UserPermissionsView extends Composite implements UserPermissionsDisplay, DataProviderSearchBoxUpdateCallback, CompanyPermissionCellComponentCallback {

    private static UserPermissionsUiBinder uiBinder = GWT.create(UserPermissionsUiBinder.class);


    interface UserPermissionsUiBinder extends UiBinder<Widget, UserPermissionsView> {
    }


    public UserPermissionsView() {
        createUnauthorizedCompanyList();
        
        initWidget(uiBinder.createAndBindUi(this));
        
        // TODO this may be a better way to handle all of my click handlers
        // http://code.google.com/webtoolkit/doc/latest/DevGuideUiHandlers.html

        
        undoButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                String currentUserId = userId;
                userId = "";
                setHasUnsavedChanges(false);
                GwtAdminGlobals.getInstance().gotoPlace(new UserPermissionsPlace(currentUserId, userRole));                
            }
        });

        
        backButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                History.back();
            }
        });

        saveButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                new UserPermissionsSaveActivity(GwtAdminGlobals.getInstance(), userId, userRole.toLowerCase(), companySearchBox.getUnfilteredList()).start(null, null);
            }
        });
        
        GwtAdminGlobals.getInstance().registerDisplay(UserPermissionsDisplay.class, this);
    }
    


    @UiField
    Label userRoleTitle;

    @UiField
    Anchor undoButton;
    
    @UiField
    Anchor backButton;
    
    @UiField
    Button saveButton;
    
    @UiField
    DivElement saveDiv;
    
    @UiField
    DivElement backDiv;
    
    @UiField(provided=true)
    DataProviderSearchBox<CompanyPermissionBean> companySearchBox;

    @UiField(provided=true)
    CellTable<CompanyPermissionBean> companyPermissions;
    
    @UiField(provided=true)
    SimplePager companyPermissionsPager;

    @UiField
    DivElement companylistDiv;
    
    @UiField
    DivElement emptyCompanylistDiv;

     
    private String userId = "";
    private String userRole = "";
    private ListDataProvider<CompanyPermissionBean> companyPermissionsDataProvider;   
    private SingleSelectionModel<CompanyPermissionBean> companyPermissionsSelectionModel;
    private CompanyPermissionHeader companyPermissionHeader;
    private ArrayList<String> companyList = new ArrayList<String>();

    private boolean hasChanges = false;
    

    @Override
    public String getUserId() {
        return userId;
    }

    @Override
    public void setUserId(String userId) {
        this.userId = userId;
        setUserRoleLabel();
    }

    @Override
    public String getUserRole() {
        return userRole;
    }

    @Override
    public void setUserRole(String userRole) {
        if (userRole.equals("administrator")) {
            this.userRole = "Administrator";            
        } else if (userRole.equals("customer")) {
            this.userRole = "Customer Admin";            
        } else if (userRole.equals("company")) {
            this.userRole = "Customer";            
        }  else {
            this.userRole = userRole;
        }
        setUserRoleLabel();
    }

    @Override
    public List<CompanyPermissionBean> getCompaniesPermissionList() {
        return companySearchBox.getUnfilteredList();
    }
    
    @Override
    public void setCompaniesPermissionList(List<CompanyPermissionBean> companyPermissionList) {
        companySearchBox.setUnfilteredList(companyPermissionList);
        setHasUnsavedChanges(false);
    }
    
    @Override
    public void handleSearchBoxChange() {
        areAllSelected();
        
        Style listStyle = companylistDiv.getStyle();
        Style emptyStyle = emptyCompanylistDiv.getStyle();
        
        if (companyPermissionsDataProvider.getList().isEmpty()) {
            listStyle.setDisplay(Display.NONE);
            emptyStyle.setDisplay(Display.BLOCK);
        } else {
            listStyle.setDisplay(Display.BLOCK);
            emptyStyle.setDisplay(Display.NONE);
        }

    }

    @Override
    public boolean hasUnsavedChanges() {
        return hasChanges;
    }
    
    @Override
    public void setHasUnsavedChanges(boolean hasUnsavedChanges) {
        hasChanges = hasUnsavedChanges;
        
        Style backDivStyle = backDiv.getStyle();
        Style saveDivStyle = saveDiv.getStyle();
        if (hasChanges) {
            backDivStyle.setDisplay(Display.NONE);            
            saveDivStyle.setDisplay(Display.BLOCK);
        } else {
            saveDivStyle.setDisplay(Display.NONE);            
            backDivStyle.setDisplay(Display.BLOCK);            
        }
        
        areAllSelected();
    }

    @Override
    public void handleCellSelection(boolean isSelected) {
        setHasUnsavedChanges(true);
    }

    @Override
    public void handleHeaderSelection(boolean isSelected) {
        selectAllCompanies(isSelected);
    }

    private void setUserRoleLabel() {
        userRoleTitle.setText(userId + " - " + userRole + " Role");
    }
    
    
    private void createUnauthorizedCompanyList() {

        CellTable.Resources resources = GWT.create(GwtCellTableResources.class);
        companyPermissions = new CellTable<CompanyPermissionBean>(0, resources); // the page size should be set in the UIBinder definition
        
        companyPermissionsSelectionModel = new SingleSelectionModel<CompanyPermissionBean>();
        companyPermissions.setSelectionModel(companyPermissionsSelectionModel);
        

        Column<CompanyPermissionBean, CompanyPermissionBean> companyPermissionSelectionColumn = new Column<CompanyPermissionBean, CompanyPermissionBean>(new CompanyPermissionCell(this)) {
            @Override
            public CompanyPermissionBean getValue(CompanyPermissionBean companyPermission) {
                return companyPermission;
            }
        };   
        companyPermissionHeader = new CompanyPermissionHeader("Company Access", companyPermissions, this);
        companyPermissions.addColumn(companyPermissionSelectionColumn, companyPermissionHeader);
                
        companyPermissionsPager = new SimplePager();
        companyPermissionsPager.setDisplay(companyPermissions);
        
        companyPermissionsDataProvider = new ListDataProvider<CompanyPermissionBean>();
        companyPermissionsDataProvider.addDataDisplay(companyPermissions);
        
        companySearchBox = new DataProviderSearchBox<CompanyPermissionBean>(companyPermissionsDataProvider, new CompanyPermissionBean.CompanyNameFieldContainsStringComparator());
        companySearchBox.setPrompt("Type to search for company...");
        companySearchBox.setSearchBoxUpdateCallback(this);

    }
    
    private void selectAllCompanies(boolean hasAccess) {
        List<CompanyPermissionBean> companyPermissions = companyPermissionsDataProvider.getList();
        for (CompanyPermissionBean companyPermission: companyPermissions) {
            companyPermission.setAccess(hasAccess);      
        }
        companyPermissionsDataProvider.flush();
        companyPermissionsDataProvider.refresh();
        setHasUnsavedChanges(true);
    }
    
    private void areAllSelected() {
        Boolean allSelected = true;
        
        for (CompanyPermissionBean companyPermission: companyPermissionsDataProvider.getList()) {
            if (!companyPermission.hasAccess()) {
                allSelected = false;
                break;
            }
        }
        
        companyPermissionHeader.setValue(allSelected);
    }

    @Override
    public void setCompanyList(ArrayList<String> companies) {
        companyList.clear();
        companyList.addAll(companies);
        
    }

    @Override
    public ArrayList<String> getCompanyList() {
        return companyList;
    }

    @Override
    public boolean isCompanyListLoaded() {
        return (null != companyList) && !companyList.isEmpty() ;
    }

}
