package com.example.coursemanagementapi.service.impl;

import com.example.coursemanagementapi.persistence.entities.Media;
import com.example.coursemanagementapi.persistence.repository.MediaRepository;
import com.example.coursemanagementapi.service.MediaService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.tomcat.util.file.ConfigurationSource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MediaServiceImpl implements MediaService {

    private final MediaRepository mediaRepository;

    @Override
    public List<Media> createMedia(MultipartFile[] files) {
        if (files.length < 1) {
            throw new RuntimeException("File not found");
        }
        List<Media> mediaList = new ArrayList<>();
        try {
            for (MultipartFile file : files) {
                String filename = file.getOriginalFilename();
                String contentType = file.getContentType();
                List<String> contentTypes = List.of("audio/x-wav", "audio/wav", "audio/mpeg", "audio/mp3", "image/jpeg", "image/png", "image/gif", "application/pdf");

                if (!contentTypes.contains(contentType)) {
                    throw new RuntimeException("Invalid file type");
                }

                if (file.getSize() > (5L * 1000000 * 2048)) {
                    throw new RuntimeException("File is too large");
                }

                if (filename.contains("..")) {
                    throw new RuntimeException("Invalid file format");
                }
                File file1 = new File(filename);
                FileUtils.writeByteArrayToFile(file1, file.getBytes());
                Media media = Media.builder()
                        .filename(filename)
                        .fileSize(Math.toIntExact(file.getSize()))
                        .contentType(contentType)
                        .extension(FilenameUtils.getExtension(filename))
                        .url("https://coursemanagement.com/files/" + filename)
                        .build();
                media = mediaRepository.save(media);
                mediaList.add(media);
            }
            return mediaList;
        } catch (IOException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    @Override
    public Media createMedia(MultipartFile file) {
        try {
            String originalFilename = file.getOriginalFilename();
            String safeFilename = FilenameUtils.getName(originalFilename);  // Sanitize filename
            String contentType = file.getContentType();
            List<String> allowedContentTypes = List.of("audio/x-wav", "audio/wav", "audio/mpeg", "audio/mp3", "image/jpeg", "image/png", "image/gif", "application/pdf");

            if (!allowedContentTypes.contains(contentType)) {
                throw new IllegalArgumentException("Invalid file type");
            }

            long maxFileSize = 5L * 1024 * 1024 * 1024;  // 5 GB
            if (file.getSize() > maxFileSize) {
                throw new IllegalArgumentException("File is too large");
            }

            if (safeFilename.contains("..")) {
                throw new IllegalArgumentException("Invalid file format");
            }

            File serverFile = new File("path/to/save/files", safeFilename);  // Use a safe directory path
            FileUtils.writeByteArrayToFile(serverFile, file.getBytes());

            Media media = Media.builder()
                    .filename(safeFilename)
                    .fileSize(Math.toIntExact(file.getSize()))
                    .contentType(contentType)
                    .extension(FilenameUtils.getExtension(safeFilename))
                    .url("https://coursemanagement.com/files/" + safeFilename)
                    .build();

            return mediaRepository.save(media);

        } catch (IOException ex) {
            throw new RuntimeException("Failed to store file: " + ex.getMessage(), ex);
        }
    }

    @Override
    public byte[] downloadVideo(String filename) throws IOException {
            Path path = Paths.get("path/to/videos/" + filename);
        if (!Files.exists(path)) {
                throw new IOException("File not found " + filename);
            }
            return Files.readAllBytes(path);

    }

    @Override
    public void deleteMedia(Long id) {
        mediaRepository.findById(id).ifPresent(mediaRepository::delete);
    }
}
