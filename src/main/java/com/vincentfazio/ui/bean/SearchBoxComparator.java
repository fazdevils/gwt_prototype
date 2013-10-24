package com.vincentfazio.ui.bean;

public interface SearchBoxComparator<T> {
    
    /**
     * Comparator used to compare a bean field with a string to determine is the bean is filtered in or out 
     * @param bean
     * @param searchText
     * @return true is the bean matches the search text, false if it does not
     */
    public boolean equals(T bean, String searchText);

}
