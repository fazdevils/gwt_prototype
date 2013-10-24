package com.vincentfazio.ui.model.parser.xml;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.XMLParser;
import com.vincentfazio.ui.bean.QuestionBean;

public class ProfileSurveyXmlParser extends AbstractProfileSurveyXmlParser<List<QuestionBean>> {
    
    public List<QuestionBean> parse(String responseXml) {
        ArrayList<QuestionBean> questions = new ArrayList<QuestionBean>();
        
        // parse the XML document into a DOM
        Document messageDom = XMLParser.parse(responseXml);
        
        NodeList questionCategoryList = messageDom.getElementsByTagName("question-category");
        for (int i=0; i < questionCategoryList.getLength(); ++i) {
            Element questionCategory = (Element) questionCategoryList.item(i);
            Node questionListNode = questionCategory.getElementsByTagName("questions").item(0);
            NodeList questionList = questionListNode.getChildNodes();
            for (int j=0; j < questionList.getLength(); ++j) {
                Node question = questionList.item(j);
                if (Node.ELEMENT_NODE == question.getNodeType()) {
                    questions.add(parseQuestion(null, (Element) question));
                }
            }            
        }
        return questions;
    }


    public String createXml(List<QuestionBean> tasList) {
        throw new UnsupportedOperationException();
    }

}
