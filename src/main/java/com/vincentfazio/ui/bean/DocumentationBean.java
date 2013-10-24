package com.vincentfazio.ui.bean;

import java.util.Set;
import java.util.TreeSet;

import com.vincentfazio.ui.bean.comparator.DocumentBeanComparator;

public class DocumentationBean {
	
	private String documentationName;
	private Set<DocumentBean> attachedDocuments = new TreeSet<DocumentBean>(new DocumentBeanComparator());
	
	public String getDocumentationName() {
		return documentationName;
	}
	public void setDocumentationName(String documentationName) {
		this.documentationName = documentationName;
	}
	
	public Set<DocumentBean> getAttachedDocuments() {
		return attachedDocuments;
	}
	
	public void attachDocument(DocumentBean document) {
		attachedDocuments.add(document);
	}
	
	public void detatchDocument(DocumentationBean document) {
		
	}
}

