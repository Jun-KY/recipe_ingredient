package com.jun.recipe_ingredient.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RecipeIngredientDto {
    @NotBlank(message = "Ingredient name required")
    private String ingredientName;

    @NotNull(message = "Amount is required")
    private Double amount;

    @NotBlank(message = "Unit is required")
    private String unit;
}
