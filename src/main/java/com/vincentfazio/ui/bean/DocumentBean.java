package com.vincentfazio.ui.bean;

import java.util.Date;

public class DocumentBean {
	
	private Integer id;
	private String name;
	private String description;
	private Date uploadedAt;
	private String uploadedBy;
	private Integer size;
	private Integer extension;
	
	//private Set<DocumentationBean> documentationFor = new TreeSet<DocumentationBean>(new DocumentationBeanComparator());  // TODO think this through... don't want to create the impression of a 2-way relationship that I'll need to manage
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getUploadedAt() {
		return uploadedAt;
	}
	public void setUploadedAt(Date uploadedAt) {
		this.uploadedAt = uploadedAt;
	}
	public String getUploadedBy() {
		return uploadedBy;
	}
	public void setUploadedBy(String uploadedBy) {
		this.uploadedBy = uploadedBy;
	}
	public Integer getSize() {
		return size;
	}
	public void setSize(Integer size) {
		this.size = size;
	}
	public Integer getExtension() {
		return extension;
	}
	public void setExtension(Integer extension) {
		this.extension = extension;
	}
		

}
