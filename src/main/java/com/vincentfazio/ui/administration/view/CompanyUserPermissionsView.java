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
import com.google.gwt.view.client.SelectionModel;
import com.google.gwt.view.client.SingleSelectionModel;
import com.vincentfazio.ui.administration.activity.CompanyUserPermissionsSaveActivity;
import com.vincentfazio.ui.administration.global.GwtAdminGlobals;
import com.vincentfazio.ui.administration.place.CompanyUserPermissionsPlace;
import com.vincentfazio.ui.administration.view.component.UserPermissionCellComponentCallback;
import com.vincentfazio.ui.bean.CompanyUserPermissionBean;
import com.vincentfazio.ui.view.component.DataProviderSearchBox;
import com.vincentfazio.ui.view.component.DataProviderSearchBoxUpdateCallback;
import com.vincentfazio.ui.view.util.GwtCellTableResources;

public class CompanyUserPermissionsView extends Composite implements CompanyUserPermissionsDisplay, DataProviderSearchBoxUpdateCallback, UserPermissionCellComponentCallback {

    private static CompanyPermissionsUiBinder uiBinder = GWT.create(CompanyPermissionsUiBinder.class);


    interface CompanyPermissionsUiBinder extends UiBinder<Widget, CompanyUserPermissionsView> {
    }


    public CompanyUserPermissionsView() {
        createUnauthorizedUserList();
        
        initWidget(uiBinder.createAndBindUi(this));
        
        undoButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                String currentCompanyId = company;
                company = "";
                setHasUnsavedChanges(false);
                GwtAdminGlobals.getInstance().gotoPlace(new CompanyUserPermissionsPlace(currentCompanyId, userRole));                
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
                new CompanyUserPermissionsSaveActivity(GwtAdminGlobals.getInstance(), company, userRole.toLowerCase(), userSearchBox.getUnfilteredList()).start(null, null);
            }
        });
        
        GwtAdminGlobals.getInstance().registerDisplay(CompanyUserPermissionsDisplay.class, this);
    }
    


    @UiField
    Label companyRoleTitle;

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
    DataProviderSearchBox<CompanyUserPermissionBean> userSearchBox;

    @UiField(provided=true)
    CellTable<CompanyUserPermissionBean> userPermissions;
    
    @UiField(provided=true)
    SimplePager userPermissionsPager;

    @UiField
    DivElement companylistDiv;
    
    @UiField
    DivElement emptyCompanylistDiv;

     
    private String company = "";
    private String userRole = "";
    private ListDataProvider<CompanyUserPermissionBean> userPermissionsDataProvider;   
    private SelectionModel<CompanyUserPermissionBean> userPermissionsSelectionModel;
    private UserPermissionHeader userPermissionHeader;
    private ArrayList<String> userList = new ArrayList<String>();

    private boolean hasChanges = false;
    

    @Override
    public String getCompanyId() {
        return company;
    }

    @Override
    public void setCompanyId(String companyId) {
        this.company = companyId;
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
    public List<CompanyUserPermissionBean> getCompanyUserPermissionList() {
        return userSearchBox.getUnfilteredList();
    }
    
    @Override
    public void setCompanyUserPermissionList(List<CompanyUserPermissionBean> companyPermissionList) {
        userSearchBox.setUnfilteredList(companyPermissionList);
        setHasUnsavedChanges(false);
    }
    
    @Override
    public void handleSearchBoxChange() {
        areAllSelected();
        
        Style listStyle = companylistDiv.getStyle();
        Style emptyStyle = emptyCompanylistDiv.getStyle();
        
        if (userPermissionsDataProvider.getList().isEmpty()) {
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
        selectAllUsers(isSelected);
    }

    private void setUserRoleLabel() {
        companyRoleTitle.setText(company + " - " + userRole + " Users");
    }
    
    
    private void createUnauthorizedUserList() {

        CellTable.Resources resources = GWT.create(GwtCellTableResources.class);
        userPermissions = new CellTable<CompanyUserPermissionBean>(0, resources); // the page size should be set in the UIBinder definition
        
        userPermissionsSelectionModel = new SingleSelectionModel<CompanyUserPermissionBean>();
        userPermissions.setSelectionModel(userPermissionsSelectionModel);
        

        Column<CompanyUserPermissionBean, CompanyUserPermissionBean> companyPermissionSelectionColumn = new Column<CompanyUserPermissionBean, CompanyUserPermissionBean>(new UserPermissionCell(this)) {
            @Override
            public CompanyUserPermissionBean getValue(CompanyUserPermissionBean userPermission) {
                return userPermission;
            }
        };   
        userPermissionHeader = new UserPermissionHeader("User Access", userPermissions, this);
        userPermissions.addColumn(companyPermissionSelectionColumn, userPermissionHeader);
                
        userPermissionsPager = new SimplePager();
        userPermissionsPager.setDisplay(userPermissions);
        
        userPermissionsDataProvider = new ListDataProvider<CompanyUserPermissionBean>();
        userPermissionsDataProvider.addDataDisplay(userPermissions);
        
        userSearchBox = new DataProviderSearchBox<CompanyUserPermissionBean>(userPermissionsDataProvider, new CompanyUserPermissionBean.UserNameFieldContainsStringComparator());
        userSearchBox.setPrompt("Type to search for user...");
        userSearchBox.setSearchBoxUpdateCallback(this);

    }
    
    private void selectAllUsers(boolean hasAccess) {
        List<CompanyUserPermissionBean> userPermissions = userPermissionsDataProvider.getList();
        for (CompanyUserPermissionBean userPermission: userPermissions) {
            userPermission.setAccess(hasAccess);      
        }
        userPermissionsDataProvider.flush();
        userPermissionsDataProvider.refresh();
        setHasUnsavedChanges(true);
    }
    
    private void areAllSelected() {
        Boolean allSelected = true;
        
        for (CompanyUserPermissionBean userPermission: userPermissionsDataProvider.getList()) {
            if (!userPermission.hasAccess()) {
                allSelected = false;
                break;
            }
        }
        
        userPermissionHeader.setValue(allSelected);
    }

    @Override
    public void setUserList(ArrayList<String> companies) {
        userList.clear();
        userList.addAll(companies);
        
    }

    @Override
    public ArrayList<String> getUserList() {
        return userList;
    }

}
