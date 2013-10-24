package com.vincentfazio.ui.model.parser.xml;

import java.util.SortedSet;

import org.junit.Test;

import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.xml.client.impl.DOMParseException;
import com.vincentfazio.ui.bean.TaskBean;
import com.vincentfazio.ui.bean.TaskType;
import com.vincentfazio.ui.model.parser.xml.TaskListXmlParser;

public class OpenTaskXmlParserTest extends GWTTestCase {

    @Test
    public void testParseTasks() {
        TaskListXmlParser xmlParser = new TaskListXmlParser();
        SortedSet<TaskBean> tasks;
        
        try {
            tasks = xmlParser.parse("");
            fail();
        } catch (DOMParseException e) {
        }

        tasks = xmlParser.parse("<task-list></task-list>");
        assertEquals(0, tasks.size());

        tasks = xmlParser.parse(
                "<task-list>" +
                    "<survey-activity>" +
                		"<vendor>Dell</vendor>" +
                		"<days-since-created>41</days-since-created>" +
                		"<days-since-last-activity>1</days-since-last-activity>" +
                		"<number-of-reminder-emails-sent>0</number-of-reminder-emails-sent>" +
                		"<requested-on>2012-10-10 08:10:57.000</requested-on>" +
                		"<last-activity>2012-11-19 11:26:42.000</last-activity>" +
            		"</survey-activity>" +
            		"<profile-survey-activity>" +
                		"<vendor>Service Master Building Maintenance</vendor>" +
                		"<days-since-created>8</days-since-created>" +
                		"<days-since-last-activity>8</days-since-last-activity>" +
                		"<number-of-reminder-emails-sent>0</number-of-reminder-emails-sent>" +
                		"<requested-on>2012-11-12 16:54:13.000</requested-on>" +
                		"<last-activity>2011-01-31 14:14:53.000</last-activity>" +
            		"</profile-survey-activity>" +
                    "<demographic-survey-activity>" +
                        "<vendor>Service Master Building Maintenance</vendor>" +
                        "<days-since-created>8</days-since-created>" +
                        "<days-since-last-activity>8</days-since-last-activity>" +
                        "<number-of-reminder-emails-sent>0</number-of-reminder-emails-sent>" +
                        "<requested-on>2012-11-12 16:54:13.000</requested-on>" +
                        "<last-activity>2011-01-31 14:14:53.000</last-activity>" +
                    "</demographic-survey-activity>" +
        		"</task-list>");
        assertEquals(3, tasks.size());
        TaskBean[] taskArray = (TaskBean[]) tasks.toArray(new TaskBean[0]);
        TaskBean taskBean = taskArray[0];
        assertEquals(TaskType.SecuritySurvey, taskBean.getTaskType());
        assertEquals("Dell", taskBean.getVendor());
        
        taskBean = taskArray[2];
        assertEquals(TaskType.ProfileSurvey, taskBean.getTaskType());
        assertEquals("Service Master Building Maintenance", taskBean.getVendor());

        taskBean = taskArray[1];
        assertEquals(TaskType.DemographicSurvey, taskBean.getTaskType());
        assertEquals("Service Master Building Maintenance", taskBean.getVendor());
	}
 
    @Override
    public String getModuleName() {
        return "com.vincentfazio.ui.gwtprototype";
    } 
}
