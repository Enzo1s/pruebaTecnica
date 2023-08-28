package com.example.demo.model;

import java.util.Date;

import lombok.Data;

@Data
public class DocumentModel {

	private String fileName;
	
	private String hashSha256;
	
	private String hashSha512;
	
	private Date lastUpload;
}
