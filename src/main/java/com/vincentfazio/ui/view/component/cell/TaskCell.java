package com.vincentfazio.ui.view.component.cell;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.vincentfazio.ui.bean.DocumentationTaskBean;
import com.vincentfazio.ui.bean.TaskBean;
import com.vincentfazio.ui.bean.TaskType;

public class TaskCell extends AbstractCell<TaskBean> {

    @Override
    public void render(
            com.google.gwt.cell.client.Cell.Context context, 
            TaskBean task, 
            SafeHtmlBuilder sb) 
    {
        if (task == null) {
            return;
        }

        TaskType taskType = task.getTaskType();
        sb.appendHtmlConstant("<div class=\"-task-item\">");

        sb.appendHtmlConstant("<div class=\"task-description\">");
		sb.append(SafeHtmlUtils.fromString(taskType.getDescription() + " for " + task.getVendor()));
        sb.appendHtmlConstant("</div>");

        DateTimeFormat dateFormat = DateTimeFormat.getFormat("MMM dd, yyyy");
        sb.appendHtmlConstant("<div class=\"task-status\">");
        switch (taskType) {
        	case DemographicSurvey:
        	case ProfileSurvey:
        	case SecuritySurvey:
                sb.append(SafeHtmlUtils.fromString("Requested " + dateFormat.format(task.getRequestedOn())));
                break;
        	case DocumentationRequest:
        		DocumentationTaskBean documentationTask = (DocumentationTaskBean)task;
        		sb.append(SafeHtmlUtils.fromString(documentationTask.getNumberOfDocumentsRequired() + " Documents Requested"));
        		break;
        		
        }
        sb.appendHtmlConstant("</div>");            
        
        sb.appendHtmlConstant("</div>");
    }

}
