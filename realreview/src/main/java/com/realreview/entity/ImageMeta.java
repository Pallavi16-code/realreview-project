package com.realreview.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "image_meta")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImageMeta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user")
    private String user;

    @Column(name = "file_name")
    private String fileName;

    

    private String location;

    @Column(name = "time_stamp")
    private LocalDateTime timeStamp;

    @Column(name = "upload_time")
    private LocalDateTime uploadTime;

    @Column(name = "file_path")
    private String filePath;
}

