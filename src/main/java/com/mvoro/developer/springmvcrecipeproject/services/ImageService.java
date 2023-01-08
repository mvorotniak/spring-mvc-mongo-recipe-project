package com.mvoro.developer.springmvcrecipeproject.services;

import org.springframework.web.multipart.MultipartFile;

import reactor.core.publisher.Mono;

public interface ImageService {

    Mono<Void> uploadImageForRecipeId(String id, MultipartFile file);
}
