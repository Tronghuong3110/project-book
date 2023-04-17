package com.bookstore.service;

import org.springframework.core.io.ByteArrayResource;

public interface IStorageService {
	ByteArrayResource readContentFile(String photo);
}
