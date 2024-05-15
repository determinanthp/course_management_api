package com.example.coursemanagementapi.controller;

import com.example.coursemanagementapi.persistence.entities.Media;
import com.example.coursemanagementapi.service.MediaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/medias")
public class MediaController {
    private  final MediaService mediaService;

    @PostMapping
    public List<Media> createMedia(MultipartFile[] files){
        return mediaService.createMedia(files);
    }
    @PutMapping("/file")
    public Media createMedia(MultipartFile file){
        return mediaService.createMedia(file);
    }

    @GetMapping("/video/{filename:.+}")
    public byte[] downloadVideo(@PathVariable String filename) throws IOException {
        return mediaService.downloadVideo(filename);
    }
    @DeleteMapping("/id")
    public ResponseEntity<Void> deleteMedia(Long id){
        mediaService.deleteMedia(id);
        return ResponseEntity.ok().build();
    }
}
