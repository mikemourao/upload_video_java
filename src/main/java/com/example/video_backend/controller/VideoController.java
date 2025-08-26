package com.example.video_backend.controller;

import com.example.video_backend.model.Video;
import com.example.video_backend.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/videos")
public class VideoController {

    @Autowired
    private VideoService videoService;

    @GetMapping("/list")
    public List<Video> getAllVideos() {
        return videoService.getAllVideos();
    }

    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    public Video uploadVideo(@RequestParam("file") MultipartFile file) {
        try {
            return videoService.saveVideo(file);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar vídeo: " + e.getMessage());
        }
    }
}
