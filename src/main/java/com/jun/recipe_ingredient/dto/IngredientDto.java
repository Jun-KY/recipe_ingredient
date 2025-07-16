package com.jun.recipe_ingredient.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class IngredientDto {
    private String name;

    @NotBlank(message = "Ingredient name required")
    private Long id;

}
