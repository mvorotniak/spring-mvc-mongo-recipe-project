package com.mvoro.developer.springmvcrecipeproject.services;

import java.io.IOException;
import java.util.stream.IntStream;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.mvoro.developer.springmvcrecipeproject.domain.Recipe;
import com.mvoro.developer.springmvcrecipeproject.repositories.RecipeRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService {

    private final RecipeService recipeService;

    private final RecipeRepository recipeRepository;

    public ImageServiceImpl(final RecipeService recipeService, RecipeRepository recipeRepository) {
        this.recipeService = recipeService;
        this.recipeRepository = recipeRepository;
    }

    @Override
    @Transactional
    public void uploadImageForRecipeId(final String id, final MultipartFile file) {
        log.info("Received {} file for uploading...", file.getOriginalFilename());

        try {
            final Recipe recipe = recipeService.findById(id);
            Byte[] fileBytes = this.toObjects(file.getBytes());

            recipe.setImage(fileBytes);
            recipeRepository.save(recipe);
        } catch (IOException e) {
            log.error("Unable to save file {} to database.", file.getOriginalFilename());
            e.printStackTrace();
        }

        log.info("Successfully uploaded file {} to database.", file.getOriginalFilename());
    }

    private Byte[] toObjects(byte[] bytes) {
        return IntStream.range(0, bytes.length)
            .mapToObj(i -> bytes[i])
            .toArray(Byte[]::new);
    }
}
