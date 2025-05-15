package com.realreview.service;

import java.util.Optional;

import com.realreview.entity.ImageMeta;

public interface ImageMetaService {
	
	ImageMeta save(ImageMeta meta);
	
	Optional<ImageMeta> findbyFileName(String fileName); 
	    
	

}
