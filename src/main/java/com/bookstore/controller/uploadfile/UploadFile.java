package com.bookstore.controller.uploadfile;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.bookstore.service.IBookService;
import com.bookstore.service.IStorageService;

@RestController
@CrossOrigin(origins = "*")
public class UploadFile {
	
	@Autowired
	private IBookService bookService;
	
	@Autowired
	private IStorageService storeService;
	
	@PostMapping("/admin/file")
	public String uploadFile(@RequestParam(name = "image") MultipartFile image) throws IOException {
		String pathStr = bookService.saveFile(image);
		// chuyển thành đường dẫn tới ảnh trong thư mục
		String urlImage = ServletUriComponentsBuilder.fromCurrentContextPath()
							.path(pathStr)
							.toUriString(); 
		return pathStr.substring(pathStr.lastIndexOf("/")+1);
	}
	
	@GetMapping("/image/{photo}")
	public ResponseEntity<ByteArrayResource> getImage(@PathVariable("photo") String photo) {
		ByteArrayResource byteArrayResource = storeService.readContentFile(photo);
		if(byteArrayResource == null) {
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.ok()
				.contentType(MediaType.IMAGE_PNG)
				.body(byteArrayResource);
	}
}
