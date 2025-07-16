package com.jun.recipe_ingredient.dto;

import com.jun.recipe_ingredient.model.Recipe;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class RecipeDto {
    private Long id;
    @NotBlank (message = "Input Title")
    private String title;
    private String description;

}
