package com.vincentfazio.ui.administration.view;

import java.util.List;

import com.google.gwt.cell.client.TextCell;
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
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DeckLayoutPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.vincentfazio.ui.administration.activity.CreateCompanyActivity;
import com.vincentfazio.ui.administration.global.GwtAdminGlobals;
import com.vincentfazio.ui.administration.view.MainAdminNavigationDisplay.MainAdminNavigationEnum;
import com.vincentfazio.ui.bean.SearchBoxComparator;
import com.vincentfazio.ui.view.component.DataProviderSearchBox;
import com.vincentfazio.ui.view.component.DataProviderSearchBoxUpdateCallback;
import com.vincentfazio.ui.view.util.GwtCellTableResources;

public class CompanyListView extends Composite implements CompanyListDisplay, DataProviderSearchBoxUpdateCallback{

    private static CompanyListUiBinder uiBinder = GWT.create(CompanyListUiBinder.class);


    interface CompanyListUiBinder extends UiBinder<Widget, CompanyListView> {
    }

    @UiField
    DeckLayoutPanel companyDeck;

    @UiField(provided=true)
    CellTable<String> companies;
    
    @UiField(provided=true)
    SimplePager pager;
    
    @UiField
    CompanyDetailsView companyDetails;
    
    @UiField
    NoCompanySelectedView noCompany;
    
    @UiField
    CompanyUserPermissionsView companyUserPermissions;
    
    @UiField(provided=true)
    DataProviderSearchBox<String> companySearchBox;

    @UiField
    DivElement companylistDiv;
    
    @UiField
    DivElement emptyCompanylistDiv;

    @UiField
    Button addCompanyButton;

    private ListDataProvider<String> companyDataProvider;
    
    private SingleSelectionModel<String> selectionModel;
    
    private Presenter presenter;

    private boolean companyListLoaded = false;

    private boolean refreshCompanyDetail = true;
    
    public CompanyListView() {

    	CellTable.Resources resources = GWT.create(GwtCellTableResources.class);
    	companies = new CellTable<String>(0, resources); // the page size should be set in the UIBinder definition
        Column<String, String> companyNameColumn = new Column<String, String>(new TextCell()) {
            @Override
            public String getValue(String name) {
                return name;
            }
        };
        companies.addColumn(companyNameColumn, "Company Name");
        
        selectionModel = new SingleSelectionModel<String>();
        selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
            public void onSelectionChange(SelectionChangeEvent event) {
                switchCompany(CompanyDeckEnum.CompanyDetails);                
            }

        });
        
        companies.setSelectionModel(selectionModel);
        
        pager = new SimplePager();
        pager.setDisplay(companies);
        
        companyDataProvider = new ListDataProvider<String>();
        companyDataProvider.addDataDisplay(companies);

        companySearchBox = new DataProviderSearchBox<String>(companyDataProvider, new CompanyContainsStringComparator());
        companySearchBox.setPrompt("Type to search or add company...");
        companySearchBox.setSearchBoxUpdateCallback(this);
        
        initWidget(uiBinder.createAndBindUi(this));
        
        addCompanyButton.addStyleName("hidden");
        addCompanyButton.addClickHandler(new ClickHandler() {   
            @Override
            public void onClick(ClickEvent event) {
                new CreateCompanyActivity(GwtAdminGlobals.getInstance(), companySearchBox.getText()).start(null, null);
            }
        });

        GwtAdminGlobals.getInstance().registerDisplay(CompanyListDisplay.class, this);
    }
    
    /* (non-Javadoc)
     * @see com.vincentfazio.ui.administration.view.UserListDisplay#setList(java.util.List)
     */
    @Override
    public void setCompanyList(List<String> companyList) {
        companySearchBox.setUnfilteredList(companyList);
        companyListLoaded = true;
    }

    @Override
    public void selectCompany(Boolean refreshCompanyDetail) {
        String selectedCompanyId = companyDetails.companyId.getText();
        selectCompany(selectedCompanyId, refreshCompanyDetail);
    }
     
    @Override
    public void selectCompany(String selectedCompanyId, Boolean refreshCompanyDetail) { 
        this.refreshCompanyDetail = refreshCompanyDetail;
        
        // no company has been selected - so pick one
        if (selectedCompanyId.isEmpty()) {    
            List<String> companyList = companySearchBox.getUnfilteredList();
            if (!companyList.isEmpty()) {
                this.refreshCompanyDetail = true;
                selectedCompanyId = companyList.get(0);
            }
        }
        if (!selectedCompanyId.isEmpty()) {
            if (selectionModel.isSelected(selectedCompanyId)) {
                switchCompany(getCurrentPage());
            } else {
                selectionModel.setSelected(selectedCompanyId, true);
            }
        }        
    }

    private CompanyDeckEnum getCurrentPage() {
        CompanyDeckEnum currentPage = null;
        
        if (null == companyDeck.getVisibleWidget()) {
            currentPage = CompanyDeckEnum.CompanyDetails;
        }else if (companyDeck.getVisibleWidget().equals(companyDetails)) {
            currentPage = CompanyDeckEnum.CompanyDetails;
        } else if (companyDeck.getVisibleWidget().equals(companyUserPermissions)) {
            currentPage = CompanyDeckEnum.CompanyUserPermissions;
        }

        return currentPage;
    }
    
    
    @Override
    public boolean isCompanyListLoaded() {
        return companyListLoaded ;
    }

    @Override
    public void showView(CompanyDeckEnum companyView) {
        MainAdminNavigationDisplay navigationDisplay = (MainAdminNavigationDisplay) GwtAdminGlobals.getInstance().getDisplay(MainAdminNavigationDisplay.class);
        navigationDisplay.showView(MainAdminNavigationEnum.Companies);
        switch (companyView) {
            case CompanyDetails:
                companyDeck.showWidget(companyDetails);
                break;
            case NoCompany:
                companyDeck.showWidget(noCompany);
                break;
            case CompanyUserPermissions:
                companyDeck.showWidget(companyUserPermissions);
                break;                
        }
    }

    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }


    private void switchCompany(CompanyDeckEnum currentPage) {
        if (refreshCompanyDetail) {
            String selectedCompanyId = getSelectedCompanyId();
            presenter.switchCompany(selectedCompanyId, currentPage);
        }
        refreshCompanyDetail = true;
    }
    
    private String getSelectedCompanyId() {
        return selectionModel.getSelectedObject();
    }

    public static class CompanyContainsStringComparator implements SearchBoxComparator<String> {
        @Override
        public boolean equals(String bean, String searchText) {
            return bean.toLowerCase().contains(searchText.toLowerCase());
        }
    }

    @Override
    public void handleSearchBoxChange() {
        Style listStyle = companylistDiv.getStyle();
        Style emptyStyle = emptyCompanylistDiv.getStyle();
        
        List<String> companyList = companyDataProvider.getList();
        if (companyList.isEmpty()) {
            listStyle.setDisplay(Display.NONE);
            emptyStyle.setDisplay(Display.BLOCK);
        } else {
            listStyle.setDisplay(Display.BLOCK);
            emptyStyle.setDisplay(Display.NONE);
        }
        
        String searchText = companySearchBox.getText();
        if (companySearchBox.isDisabled() || searchText.isEmpty() || companyExists(companyList, searchText)) {
            addCompanyButton.addStyleName("hidden");    
        } else {
            addCompanyButton.removeStyleName("hidden");
        }
        
    }

    private boolean companyExists(List<String> companyList, String searchText) {
        for (String company: companyList) {
            if (company.equalsIgnoreCase(searchText)) {
                return true;
            }
        }
        return false;
    }

}