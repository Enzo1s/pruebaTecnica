package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Document;

@Repository
public interface DocumentRepository extends JpaRepository<Document, String>{

	Document findByHashSha256(String hashSha256);
	
	Document findByHashSha512(String hashSha512);
	
	@Query("SELECT d FROM Document d WHERE d.hashSha256 IS NOT NULL")
	List<Document> listHashSha256();
	
	@Query("SELECT d FROM Document d WHERE d.hashSha512 IS NOT NULL")
	List<Document> listHashSha512();
}
