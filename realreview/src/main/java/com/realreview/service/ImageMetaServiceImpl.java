package com.realreview.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.realreview.entity.ImageMeta;
import com.realreview.repository.ImageMetaRepository;

@Service
public  class ImageMetaServiceImpl implements ImageMetaService {
	
	@Autowired
	private ImageMetaRepository imageMetaRepository;

	@Override
	public ImageMeta save(ImageMeta meta) {
		return imageMetaRepository.save(meta);
	}

	@Override
	public Optional<ImageMeta> findbyFileName(String fileName) {
	    return imageMetaRepository.findByFileName(fileName);
	}

	
	
	
	

}
