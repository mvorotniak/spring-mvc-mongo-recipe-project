package com.mvoro.developer.springmvcrecipeproject.controllers;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.mvoro.developer.springmvcrecipeproject.commands.RecipeCommand;
import com.mvoro.developer.springmvcrecipeproject.services.ImageService;
import com.mvoro.developer.springmvcrecipeproject.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;

@Slf4j
@Controller
public class ImageController {

    private final ImageService imageService;

    private final RecipeService recipeService;

    public ImageController(
        ImageService imageService,
        RecipeService recipeService
    ) {
        this.imageService = imageService;
        this.recipeService = recipeService;
    }

    @GetMapping("recipes/{id}/image")
    public String showImageUploadForm(@PathVariable final String id, final Model model) {
        model.addAttribute("recipe", this.recipeService.findCommandById(id).block());

        return "recipe/imageUploadForm";
    }

    @PostMapping("recipes/{id}/image")
    public String saveImage(@PathVariable final String id, @RequestParam("imageFile") final MultipartFile file) {
        this.imageService.uploadImageForRecipeId(id, file).block();

        return "redirect:/recipes/" + id + "/show";
    }

    @GetMapping("recipes/{id}/recipeImage")
    public void loadImage(@PathVariable final String id, final HttpServletResponse response) throws IOException {
        final RecipeCommand recipeCommand = this.recipeService.findCommandById(id).block();

        if (recipeCommand.getImage() != null) {
            log.info("Loading image for recipe [{}]", id);
            final byte[] image = new byte[recipeCommand.getImage().length];

            int i = 0;
            for (Byte imageByte : recipeCommand.getImage()) {
                image[i++] = imageByte; // unboxing
            }

            response.setContentType("image/jpeg");
            final InputStream inputStream = new ByteArrayInputStream(image);
            IOUtils.copy(inputStream, response.getOutputStream());
            log.info("Successfully loaded image for recipe [{}]", id);
        } else {
            log.warn("The provided recipe with id [{}] does not have an image.", id);
        }
    }
}
