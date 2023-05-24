package com.noviantoanggoro.youtubeclone.service;

import com.noviantoanggoro.youtubeclone.dto.UploadVideoResponse;
import com.noviantoanggoro.youtubeclone.dto.VideoDto;
import com.noviantoanggoro.youtubeclone.model.Video;
import com.noviantoanggoro.youtubeclone.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class VideoService {

    private final S3Service s3Service;
    private final VideoRepository videoRepository;

    public UploadVideoResponse uploadVideo(MultipartFile multipartFile){
        // upload file ke AWS S3
        // save video data ke database
        String videoUrl = s3Service.uploadFile(multipartFile);
        var video = new Video();
        video.setVideoUrl(videoUrl);

        var saveVideo = videoRepository.save(video);
        return new UploadVideoResponse(saveVideo.getId(), saveVideo.getVideoUrl());
    }

    public VideoDto editVideo(VideoDto videoDto) {
        // find video by videoId
        var saveVideo = getVideoById(videoDto.getId());
        // map videoDTO field ke video
        saveVideo.setTitle(videoDto.getTitle());
        saveVideo.setDescription(videoDto.getDescription());
        saveVideo.setTags(videoDto.getTags());
        saveVideo.setThumbnailUrl(videoDto.getThumbnailUrl());
        saveVideo.setVideoStatus(videoDto.getVideoStatus());
        // save video ke database
        videoRepository.save(saveVideo);
        return videoDto;
    }

    public String uploadThumbnail(MultipartFile file, String videoId) {
        var saveVideo = getVideoById(videoId);
        String thumbnailUrl = s3Service.uploadFile(file);
        saveVideo.setVideoUrl(thumbnailUrl);

        videoRepository.save(saveVideo);
        return thumbnailUrl;
    }

    Video getVideoById(String videoId){
        return videoRepository.findById(videoId)
                .orElseThrow(() -> new IllegalArgumentException("Tidak dapat menemukan video by id " + videoId));
    }
}
