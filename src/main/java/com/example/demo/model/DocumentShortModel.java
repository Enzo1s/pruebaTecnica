package com.example.demo.model;

import java.util.Date;

import lombok.Data;

@Data
public class DocumentShortModel {

	private String fileName;
	
	private String hash;
	
	private Date lastUpload;
}
