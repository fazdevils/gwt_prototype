package com.vincentfazio.ui.model.parser.xml;

import java.util.ArrayList;
import java.util.SortedSet;
import java.util.TreeSet;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.XMLParser;
import com.vincentfazio.ui.bean.TaskBean;
import com.vincentfazio.ui.bean.TaskType;
import com.vincentfazio.ui.bean.comparator.TaskBeanComparator;

public class TaskListXmlParser extends XmlParser<SortedSet<TaskBean>> {
    
    private DateTimeFormat dateFormat = DateTimeFormat.getFormat("yyyy-MM-dd HH:mm:ss.SSS"); 
    
    public SortedSet<TaskBean> parse(String responseXml) {
        SortedSet<TaskBean> tasks = new TreeSet<TaskBean>(new TaskBeanComparator());
        
        // parse the XML document into a DOM
        Document messageDom = XMLParser.parse(responseXml);
        
        NodeList taskList = messageDom.getElementsByTagName("task-list");

        tasks.addAll(parseTaskList(taskList, "survey-activity", TaskType.SecuritySurvey));
        tasks.addAll(parseTaskList(taskList, "profile-survey-activity", TaskType.ProfileSurvey));
        tasks.addAll(parseTaskList(taskList, "demographic-survey-activity", TaskType.DemographicSurvey));

        return tasks;
    }


    private ArrayList<TaskBean> parseTaskList(NodeList taskList, String surveyTag, TaskType securityTaskType)
    {
        ArrayList<TaskBean> tasks = new ArrayList<TaskBean>();
        
        NodeList surveyNodes = ((Element)taskList.item(0)).getElementsByTagName(surveyTag);
        for (int i=0; i < surveyNodes.getLength(); ++i) {
            Element taskElement = (Element)surveyNodes.item(i);
           
            TaskBean taskBean = parseTaskBean(taskElement);
            taskBean.setTaskType(securityTaskType);
            
            tasks.add(taskBean);
        }
        
        return tasks;
    }


    private TaskBean parseTaskBean(Element taskElement) {
        TaskBean taskBean = new TaskBean();
        taskBean.setVendor(extractField(taskElement, "vendor"));
        taskBean.setRequestedBy(extractField(taskElement, "requested-by"));
        
        String requestedOn = extractField(taskElement, "requested-on");
        if ((null != requestedOn) && (!requestedOn.trim().isEmpty())) {
            taskBean.setRequestedOn(dateFormat.parse(requestedOn));
        }
        taskBean.setCompletedBy(extractField(taskElement, "completed-by"));
        String completedOn = extractField(taskElement, "completed-on");
        if ((null != completedOn) && (!completedOn.trim().isEmpty())) {
            taskBean.setCompletedOn(dateFormat.parse(completedOn));
        }
        return taskBean;
    }


    public String createXml(SortedSet<TaskBean> tasList) {
        throw new UnsupportedOperationException();
    }

}
