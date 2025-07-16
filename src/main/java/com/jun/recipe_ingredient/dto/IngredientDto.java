package com.jun.recipe_ingredient.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class IngredientDto {
    private Long id;

    @NotBlank(message = "Ingredient name required")
    private String name;


}
