package com.noviantoanggoro.youtubeclone.repository;

import com.noviantoanggoro.youtubeclone.model.Video;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface VideoRepository extends MongoRepository<Video, String> {
}
