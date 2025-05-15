package com.realreview.repository;



import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.realreview.entity.ImageMeta;

public interface ImageMetaRepository extends JpaRepository<ImageMeta , Long > {
	
	Optional<ImageMeta> findByFileName(String fileName);

}
