package com.example.demo.utils;

import org.springframework.stereotype.Component;

import com.google.common.hash.Hashing;

@Component
public class HashFunction {

	public String newHashByByte(String typeHash, byte[] byteArray) throws Exception {
		switch (typeHash) {
		case "SHA-256":
			return Hashing.sha256()
					.hashBytes(byteArray)
					.toString();
		case "SHA-512":
			return Hashing.sha512()
					.hashBytes(byteArray)
					.toString();

		default:
			throw new Exception("Unsupported hash type");
		}

	}
}
