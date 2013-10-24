package com.vincentfazio.ui.view.component;

import java.util.List;

import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.view.client.ListDataProvider;
import com.vincentfazio.ui.bean.SearchBoxComparator;

public class DataProviderSearchBox<T> extends TextBox {

    private ListDataProvider<T> dataProvider;
    private SearchBoxComparator<T> comparator;
    private List<T> unfilteredList;
    private String prompt = "Type to filter...";
    private DataProviderSearchBoxUpdateCallback updateCallback = null;
    
    /**
     * 
     * @param dataProvider The data provider which will be filtered by this box
     * @param comparator The comparator used to determine if an item is filtered out or not
     */
    public DataProviderSearchBox(ListDataProvider<T> dataProvider, SearchBoxComparator<T> comparator) {
        super();
        
        this.dataProvider = dataProvider;
        this.comparator = comparator;

        resetSearchBox();
        addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                if (isDisabled()) {
                    setText("");
                    removeStyleName("disabled");
                }
            }

        });
        addBlurHandler(new BlurHandler() {
            @Override
            public void onBlur(BlurEvent event) {
                styleSeachBox();
            }
        });
        addKeyUpHandler(new KeyUpHandler() {     
            @Override
            public void onKeyUp(KeyUpEvent event) {
                 filterList();
            }
        });

    }

    
    /**
     * Set the full set of data and apply the filters
     */
    public void setUnfilteredList(List<T> unfilteredList) {
        this.unfilteredList = unfilteredList;
        filterList();
    }

    
    public List<T> getUnfilteredList() {
        return unfilteredList;
    }


    public void setPrompt(String prompt) {
        this.prompt = prompt;
        if (isDisabled()) {
            setText(this.prompt);            
        }
    }


    /**
     * Callback which performs some additional processing when the search box changes - aside from updating the 
     * data provider itself (which is updated automatically as part of this component's processing).
     * @param updateCallback
     */
    public void setSearchBoxUpdateCallback(DataProviderSearchBoxUpdateCallback updateCallback) {
        this.updateCallback = updateCallback;
    }


    /**
     * Reset the search text and style the search box
     */
    private void resetSearchBox() {
        setText("");
        styleSeachBox();
    }

    
    /**
     * Style the search box
     */
    private void styleSeachBox() {
        String searchText = getText();
        if (searchText.isEmpty()) {
           addStyleName("disabled"); 
           setText(prompt);
        }
    }

    public boolean isDisabled() {
        return getStyleName().contains("disabled");
    }

    private void filterList() {
        String searchText = getText();
        List<T> providerList = dataProvider.getList();
        providerList.clear();
        
        if (searchText.isEmpty() || isDisabled()) {
            providerList.addAll(unfilteredList);
        } else {
            for (T bean: unfilteredList) {
                if (comparator.equals(bean, searchText)) {
                    providerList.add(bean);
                }
            }
        }
        
        if (null != updateCallback) {
            updateCallback.handleSearchBoxChange();
        }
    }
}
