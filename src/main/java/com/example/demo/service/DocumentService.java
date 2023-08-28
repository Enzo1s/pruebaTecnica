package com.example.demo.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.converter.DocumentConverter;
import com.example.demo.entity.Document;
import com.example.demo.model.DocumentModel;
import com.example.demo.model.DocumentShortModel;
import com.example.demo.model.ErrorModel;
import com.example.demo.repository.DocumentRepository;
import com.example.demo.utils.HashFunction;

@Service
public class DocumentService {

	@Autowired
	private DocumentRepository documentRepository;
	
	@Autowired
	private DocumentConverter documentConverter;
	
	@Autowired
	private HashFunction hashFunction;
	
	public DocumentShortModel save(MultipartFile file, String typeHash) throws Exception {
		DocumentShortModel document;
		Document doc = new Document();
		doc.setFileName(file.getOriginalFilename());
		doc.setHashSha256(hashFunction.newHashByByte("SHA-256", file.getBytes()));
		doc.setHashSha512(hashFunction.newHashByByte("SHA-512", file.getBytes()));
		Document docDB = documentRepository.findByHashSha256(doc.getHashSha256());
		if(docDB != null) {
			docDB.setLastUpload(new Date());
			document = documentConverter.entityToShortModel(documentRepository.save(docDB));
		} else {
			document = documentConverter.entityToShortModel(documentRepository.save(doc));
		}
		document.setHash(typeHash.equals("SHA-256") ? doc.getHashSha256(): doc.getHashSha512());
		return document;
	}
	
	@Transactional
	public Map<String, Object> saveList(List<MultipartFile> files, String typeHash) {
		Map<String, Object> response = new HashMap<>();
		if(files.isEmpty()) {
			ErrorModel error = new ErrorModel();
			error.setStatus("400");
			error.setMessage("No se subieron archivos o se pasó mal el parámetro");
			error.setPath("/api/hash");
			error.setTimestamp(new Timestamp(System.currentTimeMillis()).getTime());
			response.put("error", error);
			return response;
		}
		
		List<DocumentShortModel> docs = new ArrayList<>();
		
		for(MultipartFile file : files) {
			try {
				docs.add(save(file, typeHash));
			} catch (Exception e) {
				ErrorModel error = new ErrorModel();
				error.setStatus("400");
				error.setMessage("No se subieron archivos o se pasó mal el parámetro");
				error.setPath("/api/hash");
				error.setTimestamp(new Timestamp(System.currentTimeMillis()).getTime());
				response.put("error", error);
				return response;
			}
		}
		response.put("algorithm", typeHash);
		response.put("documents", docs);
		return response;
	}
	
	public List<DocumentModel> listAll() {
		List<Document> entities = documentRepository.findAll();
		return entities.stream().map(e -> documentConverter.entityToModel(e)).collect(Collectors.toList());
	}
	
	public DocumentShortModel findByHash(String typeHash, String hash) {
		Document entity;
		DocumentShortModel doc = null;
		if(typeHash.equalsIgnoreCase("SHA-256")) {
			entity = documentRepository.findByHashSha256(hash);
			if(entity == null)
				return null;
			doc = documentConverter.entityToShortModel(entity);
			doc.setHash(entity.getHashSha256());
		} else if(typeHash.equalsIgnoreCase("SHA-512")) {
			entity = documentRepository.findByHashSha512(hash);
			if(entity == null)
				return null;
			doc = documentConverter.entityToShortModel(entity);
			doc.setHash(entity.getHashSha512());
		}
		return doc;
	}
}
