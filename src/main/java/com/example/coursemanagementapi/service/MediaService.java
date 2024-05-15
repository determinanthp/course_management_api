package com.example.coursemanagementapi.service;

import com.example.coursemanagementapi.persistence.entities.Media;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

public interface MediaService {
    public List<Media> createMedia(MultipartFile[] files);

    public Media createMedia(MultipartFile file);

    public byte[] downloadVideo(String filename) throws MalformedURLException, IOException;

    public void deleteMedia(Long id);
}
