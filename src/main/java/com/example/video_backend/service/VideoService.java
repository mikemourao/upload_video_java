package com.example.video_backend.service;

import com.example.video_backend.model.Video;
import com.example.video_backend.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
@Service
public class VideoService {

    // Caminho absoluto seguro
    private static final String UPLOAD_DIR = "C:/Users/Mike Silva/Videos/uploads/";

    @Autowired
    private VideoRepository videoRepository;

    public List<Video> getAllVideos() {
        return videoRepository.findAll();
    }

    public Video saveVideo(MultipartFile file) throws IOException {
        // Cria a pasta se não existir
        Path uploadPath = Paths.get(UPLOAD_DIR);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Caminho completo do arquivo
        Path filePath = uploadPath.resolve(file.getOriginalFilename());

        // Copia o arquivo para o destino
        Files.copy(file.getInputStream(), filePath);

        // Salva info no banco
        Video video = new Video();
        video.setFileName(file.getOriginalFilename());
        video.setFileType(file.getContentType());
        video.setFilePath(filePath.toString());

        return videoRepository.save(video);
    }
}
