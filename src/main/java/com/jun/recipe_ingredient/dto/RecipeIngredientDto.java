package com.jun.recipe_ingredient.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RecipeIngredientDto {
    @NotNull(message = "Please choose an ingredient")
    private Long ingredientId;

    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.1", message = "Amount must be positive")
    private Double amount;

    @NotBlank(message = "Unit is required")
    private String unit;
}
