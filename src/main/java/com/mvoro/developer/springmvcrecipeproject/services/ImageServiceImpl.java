package com.mvoro.developer.springmvcrecipeproject.services;

import java.io.IOException;
import java.util.Optional;
import java.util.stream.IntStream;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.mvoro.developer.springmvcrecipeproject.domain.Recipe;
import com.mvoro.developer.springmvcrecipeproject.repositories.reactive.RecipeReactiveRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@AllArgsConstructor
@Service
public class ImageServiceImpl implements ImageService {

    private final RecipeReactiveRepository recipeReactiveRepository;

    @Override
    @Transactional
    public Mono<Void> uploadImageForRecipeId(final String id, final MultipartFile file) {
        log.info("Received {} file for uploading...", file.getOriginalFilename());

        try {
            final Optional<Recipe> recipeOptional = this.recipeReactiveRepository.findById(id).blockOptional();
            Byte[] fileBytes = this.toObjects(file.getBytes());

            if (recipeOptional.isPresent()) {
                Recipe recipe = recipeOptional.get();
                recipe.setImage(fileBytes);
                this.recipeReactiveRepository.save(recipe).block();
            }
        } catch (IOException e) {
            log.error("Unable to save file {} to database.", file.getOriginalFilename());
            e.printStackTrace();
        }

        log.info("Successfully uploaded file {} to database.", file.getOriginalFilename());

        return Mono.empty();
    }

    private Byte[] toObjects(byte[] bytes) {
        return IntStream.range(0, bytes.length)
            .mapToObj(i -> bytes[i])
            .toArray(Byte[]::new);
    }
}
