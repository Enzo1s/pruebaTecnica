package com.example.demo.converter;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.example.demo.entity.Document;
import com.example.demo.model.DocumentModel;
import com.example.demo.model.DocumentShortModel;

@Component
public class DocumentConverter {

	public DocumentShortModel entityToShortModel(Document entity) {
		DocumentShortModel model = new DocumentShortModel();
		BeanUtils.copyProperties(entity, model);
		return model;
	}
	
	public DocumentModel entityToModel(Document entity) {
		DocumentModel model = new DocumentModel();
		BeanUtils.copyProperties(entity, model);
		return model;
	}
}
