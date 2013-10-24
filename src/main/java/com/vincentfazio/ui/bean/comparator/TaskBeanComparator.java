package com.vincentfazio.ui.bean.comparator;

import java.util.Comparator;
import java.util.Date;

import com.vincentfazio.ui.bean.TaskBean;

public class TaskBeanComparator implements Comparator<TaskBean> {

    @Override
    public int compare(TaskBean task1, TaskBean task2) {
    	int compareVal;
    	
    	Date requestedOn1 = task1.getRequestedOn();
		Date requestedOn2 = task2.getRequestedOn();
		if ((null != requestedOn1) && (null != requestedOn2)){
			compareVal = requestedOn1.compareTo(requestedOn2);
	        if (0 != compareVal) {
	            return compareVal;
	        }
		}
		
        compareVal = task1.getTaskType().compareTo(task2.getTaskType());
        if (0 != compareVal) {
            return compareVal;
        }
    
        compareVal = task1.getVendor().compareTo(task2.getVendor());
        if (0 != compareVal) {
            return compareVal;
        }
    
        if (null != task1.getLastActivity()) {
            compareVal = task1.getLastActivity().compareTo(task2.getLastActivity());
            if (0 != compareVal) {
                return compareVal * -1;  // most recent first
            }
        }
        
        return task1.hashCode() - task2.hashCode();
    }

}
