package com.bookstore.service.impl;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;

import com.bookstore.service.IStorageService;

@Service
public class StorageService implements IStorageService{

	@Override
	public ByteArrayResource readContentFile(String photo) {
		if(!photo.equals("") || photo != null) {
			try {
				Path fileName = Paths.get("uploads/book", photo);
				byte[] buffer = Files.readAllBytes(fileName);
				ByteArrayResource byteArrayResource = new ByteArrayResource(buffer);
				return byteArrayResource;
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

}
