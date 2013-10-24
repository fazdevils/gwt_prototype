package com.vincentfazio.ui.bean;

import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;

import com.vincentfazio.ui.bean.comparator.DocumentationBeanComparator;

public class DocumentationTaskBean extends TaskBean {

    private Set<DocumentationBean> documentationList = new TreeSet<DocumentationBean>(new DocumentationBeanComparator());

    
    
	public DocumentationTaskBean() {
		super();
		super.setTaskType(TaskType.DocumentationRequest);
	}

	@Override
	public void setTaskType(TaskType taskType) {
		throw new UnsupportedOperationException();
	}

	public Set<DocumentationBean> getDocumentationList() {
		return documentationList;
	}

	public void addDocumentation(Collection<DocumentationBean> documentationList) {
		this.documentationList.addAll(documentationList);
	}

	public Integer getNumberOfDocumentsRequired() {
		if (null == documentationList) {
			return 0;
		}
		if (documentationList.isEmpty()) {
			return 0;
		}
		Integer numberOfDocumentsRequired = 0;
		for (DocumentationBean documentationItem: documentationList) {
			Set<DocumentBean> attachedDocuments = documentationItem.getAttachedDocuments();
			if ((null == attachedDocuments) || attachedDocuments.isEmpty()) {
				++numberOfDocumentsRequired;
			}
		}
		return numberOfDocumentsRequired;
	}
    
    

}
