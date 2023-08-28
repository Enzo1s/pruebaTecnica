package com.example.demo.controller;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.DocumentModel;
import com.example.demo.model.DocumentShortModel;
import com.example.demo.model.ErrorModel;
import com.example.demo.service.DocumentService;

@RestController
@RequestMapping("/api/documents")
public class DocumentRestController {

	@Autowired
	private DocumentService documentService;
	
	@PostMapping("/hash")
	public ResponseEntity<Map<String, Object>> save(
			@RequestParam List<MultipartFile> documents, 
			@RequestParam String hashType) {
		Map<String, Object> response = documentService.saveList(documents, hashType);
		if(response.containsKey("error")) {
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
	@GetMapping
	public ResponseEntity<List<DocumentModel>> list() {
		return new ResponseEntity<>(documentService.listAll(), HttpStatus.OK);
	}
	
	@GetMapping("/{hashType}")
	public ResponseEntity<?> findByHash(@PathVariable("hashType") String hashType, @RequestParam String hash) {
		DocumentShortModel doc = documentService.findByHash(hashType, hash);
		if(doc == null) {
			Map<String, Object> response = new HashMap<>();
			ErrorModel error = new ErrorModel();
			error.setStatus("400");
			error.setMessage("No hay ningún documento con ese nombre");
			error.setPath("/api/hash");
			error.setTimestamp(new Timestamp(System.currentTimeMillis()).getTime());
			response.put("error", error);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(doc, HttpStatus.OK);
			
	}
}
