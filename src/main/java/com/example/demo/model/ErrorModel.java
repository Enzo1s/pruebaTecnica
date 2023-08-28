package com.example.demo.model;

import lombok.Data;

@Data
public class ErrorModel {
	
	private long timestamp;
	
	private String status;
	
	private String message;
	
	private String path;
}
