package com.example.demo.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

@Data
@Entity
public class Document implements Serializable {

	private static final long serialVersionUID = -5081947554423301943L;
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;
	
	@Column(name = "file_name")
	private String fileName;
	
	@Column(name = "hash_sha_256")
	private String hashSha256;
	
	@Column(name = "hash_sha_512")
	private String hashSha512;
	
	@Column(name = "last_upload")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastUpload;

}