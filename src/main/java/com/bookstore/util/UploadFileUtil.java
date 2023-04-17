package com.bookstore.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

public class UploadFileUtil {
	

	public static Path save(MultipartFile file, String uploadDir) throws IOException {
		 Path path = Paths.get(uploadDir);
		 
		 // rename file
		 // lấy ra đuôi file
		 String fileExtention = FilenameUtils.getExtension(file.getOriginalFilename());
		 String nameGenerated = UUID.randomUUID().toString().replace("-", "");
		 
		 // tên file mới sau khi đổi tên
		 nameGenerated += "." + fileExtention;
		 // copy file
		 try {
			 InputStream inputStream = file.getInputStream();
			 // tạo đường dẫn từ uploadDir tới file
			 Path pathFile = path.resolve(nameGenerated.toLowerCase());
			 
			 // sao chép dữ liệu của file ban đầu và truyền vào file có tên mới để lưu
			 Files.copy(inputStream, pathFile, StandardCopyOption.REPLACE_EXISTING);
			 
			 return pathFile;
		 }
		 catch (IOException e) {
			throw new IOException("can not save file: " + file.getOriginalFilename(), e);
		}
	}
}
