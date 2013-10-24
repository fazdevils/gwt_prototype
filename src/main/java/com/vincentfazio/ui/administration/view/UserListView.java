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
import com.vincentfazio.ui.administration.activity.CreateUserActivity;
import com.vincentfazio.ui.administration.global.GwtAdminGlobals;
import com.vincentfazio.ui.administration.view.MainAdminNavigationDisplay.MainAdminNavigationEnum;
import com.vincentfazio.ui.bean.SearchBoxComparator;
import com.vincentfazio.ui.view.component.DataProviderSearchBox;
import com.vincentfazio.ui.view.component.DataProviderSearchBoxUpdateCallback;
import com.vincentfazio.ui.view.util.GwtCellTableResources;

public class UserListView extends Composite implements UserListDisplay, DataProviderSearchBoxUpdateCallback {
    
    private static UserListUiBinder uiBinder = GWT.create(UserListUiBinder.class);


    interface UserListUiBinder extends UiBinder<Widget, UserListView> {
    }

    @UiField
    UserDetailsView userDetails;

    @UiField
    UserPermissionsView userPermissions;

    @UiField
    NoUserSelectedView noUser;

    @UiField
    DeckLayoutPanel userDeck;

    @UiField(provided=true)
    CellTable<String> users;
    
    @UiField(provided=true)
    SimplePager pager;
    
    @UiField(provided=true)
    DataProviderSearchBox<String> userSearchBox;
    
    @UiField
    DivElement userlistDiv;
    
    @UiField
    DivElement emptyUserlistDiv;
    
    @UiField
    Button addUserButton;

    private ListDataProvider<String> userDataProvider;
    
    private SingleSelectionModel<String> selectionModel;
    
    private Presenter presenter;

    private boolean userListLoaded = false;
    
    private boolean refreshUserDetail = true;

    public UserListView() {
        CellTable.Resources resources = GWT.create(GwtCellTableResources.class);
        users = new CellTable<String>(0, resources); // the page size should be set in the UIBinder definition
        Column<String, String> userNameColumn = new Column<String, String>(new TextCell()) {
            @Override
            public String getValue(String name) {
                return name;
            }
        };
        users.addColumn(userNameColumn, "User Name");
        
        selectionModel = new SingleSelectionModel<String>();
        selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
            public void onSelectionChange(SelectionChangeEvent event) {
                switchUser(UserDeckEnum.UserDetails);
            }

        });
        
        
        users.setSelectionModel(selectionModel);
        
        pager = new SimplePager();
        pager.setDisplay(users);
        
        userDataProvider = new ListDataProvider<String>();
        userDataProvider.addDataDisplay(users);
        
        userSearchBox = new DataProviderSearchBox<String>(userDataProvider, new UserContainsStringComparator());
        userSearchBox.setPrompt("Type to search or add user...");
        userSearchBox.setSearchBoxUpdateCallback(this);
        
        initWidget(uiBinder.createAndBindUi(this));
        
        addUserButton.addStyleName("hidden");
        addUserButton.addClickHandler(new ClickHandler() {   
            @Override
            public void onClick(ClickEvent event) {
                new CreateUserActivity(GwtAdminGlobals.getInstance(), userSearchBox.getText()).start(null, null);
            }
        });

        GwtAdminGlobals.getInstance().registerDisplay(UserListDisplay.class, this);
        
        userDeck.showWidget(userDetails);
    }
    
    @Override
    public void setUserList(List<String> userList) {
        userSearchBox.setUnfilteredList(userList);
        userListLoaded = true;
    }

    @Override
    public void selectUser(Boolean refreshUserDetail) {
        String selectedUserId = null;
        if (userDeck.getVisibleWidget().equals(userDetails)) {
            selectedUserId = userDetails.userId.getText();
        } else if (userDeck.getVisibleWidget().equals(userPermissions)) {
            selectedUserId = userPermissions.getUserId();
        } else if (userDeck.getVisibleWidget().equals(noUser)) {
            selectedUserId = userDetails.userId.getText();
        }
        selectUser(selectedUserId, refreshUserDetail);
    }

    @Override
    public void selectUser(String selectedUserId, Boolean refreshUserDetail) {
        this.refreshUserDetail = refreshUserDetail;
        UserDeckEnum currentPage = null;
 
        if (userDeck.getVisibleWidget().equals(userDetails)) {
            currentPage = UserDeckEnum.UserDetails;
        } else if (userDeck.getVisibleWidget().equals(userPermissions)) {
            currentPage = UserDeckEnum.UserPermission;
        }

        // no user has been selected - so pick one
        if (selectedUserId.isEmpty()) {    
            List<String> vendorList = userSearchBox.getUnfilteredList();
            if (!vendorList.isEmpty()) {
                refreshUserDetail = true;
                selectedUserId = vendorList.get(0);
            }
        }
        
        if (!selectedUserId.isEmpty()) {
            if (selectionModel.isSelected(selectedUserId)) {
                switchUser(currentPage);
            } else {
                selectionModel.setSelected(selectedUserId, true);
            }
        }        
    }

    @Override
    public void showView(UserDeckEnum userView) {
        MainAdminNavigationDisplay navigationDisplay = (MainAdminNavigationDisplay) GwtAdminGlobals.getInstance().getDisplay(MainAdminNavigationDisplay.class);
        navigationDisplay.showView(MainAdminNavigationEnum.Users);
        switch (userView) {
            case UserDetails:
                userDeck.showWidget(userDetails);
                break;
            case UserPermission:
                userDeck.showWidget(userPermissions);
                break;       
            case NoUser:
                userDeck.showWidget(noUser);
                break;       
        }
    }

    public static class UserContainsStringComparator implements SearchBoxComparator<String> {

        @Override
        public boolean equals(String bean, String searchText) {
            return bean.toLowerCase().contains(searchText.toLowerCase());
        }
        
    }

    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public boolean isUserListLoaded() {
        return userListLoaded;
    }
    
    private void switchUser(UserDeckEnum currentPage) {
        if (refreshUserDetail) {
            String selectedUserId = getSelectedUserId();
            presenter.switchUser(selectedUserId, currentPage);
        }
        refreshUserDetail = true;
    }

    private String getSelectedUserId() {
        return selectionModel.getSelectedObject();
    }

    @Override
    public void handleSearchBoxChange() {
        Style listStyle = userlistDiv.getStyle();
        Style emptyStyle = emptyUserlistDiv.getStyle();
        
        List<String> userList = userDataProvider.getList();
        if (userList.isEmpty()) {
            listStyle.setDisplay(Display.NONE);
            emptyStyle.setDisplay(Display.BLOCK);
        } else {
            listStyle.setDisplay(Display.BLOCK);
            emptyStyle.setDisplay(Display.NONE);
        }
        
        String searchText = userSearchBox.getText();
        if (userSearchBox.isDisabled() || searchText.isEmpty() || userExists(userList, searchText)) {
            addUserButton.addStyleName("hidden");    
        } else {
            addUserButton.removeStyleName("hidden");
        }
    }

    private boolean userExists(List<String> userList, String searchText) {
        for (String user: userList) {
            if (user.equalsIgnoreCase(searchText)) {
                return true;
            }
        }
        return false;
    }


}