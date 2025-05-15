package com.realreview.controller;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.realreview.entity.ImageMeta;
import com.realreview.service.ImageMetaService;

@RestController
@RequestMapping("/api/images")

public class ImageMetaController {
	
	@Autowired
	private ImageMetaService imageMetaService;
	
	
	@PostMapping("/upload")
	public ResponseEntity<String> uploadImageMetaData(
	        @RequestParam("file") MultipartFile file,
	        @RequestParam("user") String user,
	        @RequestParam("location") String location,
	        @RequestParam("timeStamp") String timeStampStr) {

	    try {
	        // Define a safer, absolute path (can also be externalized to application.properties)
	        String uploadDir = System.getProperty("user.dir") + File.separator + "uploads";

	        // Create the directory if it does not exist
	        File directory = new File(uploadDir);
	        if (!directory.exists()) {
	            directory.mkdirs();
	        }

	        // Build the complete file path
	        String fileName = file.getOriginalFilename();
	        File destination = new File(directory, fileName);

	        // Save the file to the destination
	        file.transferTo(destination);

	        // Parse timestamp string
	        LocalDateTime timeStamp = LocalDateTime.parse(timeStampStr);

	        // Save image metadata
	        ImageMeta meta = new ImageMeta();
	        meta.setFileName(fileName);
	        meta.setUser(user);
	        meta.setLocation(location);
	        meta.setTimeStamp(timeStamp);
	        meta.setFilePath(destination.getAbsolutePath()); //


	        imageMetaService.save(meta);

	        return ResponseEntity.ok("Image and metadata uploaded successfully");

	    } catch (IOException e) {
	        e.printStackTrace();
	        return ResponseEntity.internalServerError().body("Failed to upload file: " + e.getMessage());
	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.badRequest().body("Invalid request: " + e.getMessage());
	    }
	}
	
	@GetMapping("/metadata")
	public ResponseEntity<ImageMeta> getImageMetaData(@RequestParam("fileName") String fileName) {
	    Optional<ImageMeta> metaOpt = imageMetaService.findbyFileName(fileName);
	    return metaOpt.map(ResponseEntity::ok)
	                  .orElseGet(() -> ResponseEntity.notFound().build());
	}

	
//	@GetMapping("/view")
//	public ResponseEntity<byte[]> viewImage(@RequestParam("fileName")String fileName)throws IOException{
//		String uploadDir = System.getProperty("user.dir") + File.separator + "uploads";
//		File imageFile = new File(uploadDir , fileName);
//		
//		
//		if(!imageFile.exists()) {
//			return ResponseEntity.notFound().build();
//		}
//		
//		byte[] imageBytes = java.nio.file.Files.readAllBytes(imageFile.toPath());
//		
//		HttpHeaders  headers = new HttpHeaders();
//		headers.setContentType(MediaType.IMAGE_JPEG);
//		
//		return ResponseEntity.ok().headers(headers).body(imageBytes);
//		
//		
//	}
	
	@GetMapping("/view")
	public ResponseEntity<byte[]> viewImage(@RequestParam("fileName") String fileName) throws IOException {
	    String uploadDir = System.getProperty("user.dir") + File.separator + "uploads";
	    File imageFile = new File(uploadDir, fileName);

	    System.out.println("Looking for file in: " + imageFile.getAbsolutePath()); // Debug log

	    if (!imageFile.exists()) {
	        return ResponseEntity.notFound().build();
	    }

	    byte[] imageBytes = java.nio.file.Files.readAllBytes(imageFile.toPath());

	    HttpHeaders headers = new HttpHeaders();

	    // Dynamically detect the content type
	    String contentType = java.nio.file.Files.probeContentType(imageFile.toPath());
	    System.out.println("Detected content type: " + contentType);

	    if (contentType == null) {
	        contentType = "application/octet-stream"; // fallback content type
	    }

	    headers.setContentType(MediaType.parseMediaType(contentType));

	    return ResponseEntity.ok().headers(headers).body(imageBytes);
	}

	

	

}
