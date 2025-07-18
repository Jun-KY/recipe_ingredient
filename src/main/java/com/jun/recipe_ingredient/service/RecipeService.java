package com.jun.recipe_ingredient.service;
import com.jun.recipe_ingredient.dto.RecipeDto;
import com.jun.recipe_ingredient.model.Ingredient;
import com.jun.recipe_ingredient.model.Recipe;
import com.jun.recipe_ingredient.model.RecipeIngredient;
import com.jun.recipe_ingredient.repository.IngredientRepository;
import com.jun.recipe_ingredient.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service @RequiredArgsConstructor @Transactional
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final IngredientRepository ingredientRepository;
    private final RecipeIngredientRepository recipeIngredientRepository;

    public List<Recipe> findAll() {
        return recipeRepository.findAll();
    }

    public Recipe findById(Long id){
        return recipeRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Recipe not exist"));
    }

    public Recipe saveRecipe (Recipe recipe){
        return recipeRepository.save(recipe);
    }

    public void deleteRecipe (Long id){
        recipeRepository.deleteById(id);
    }

    public void  updateRecipe (Long id, RecipeDto recipeDto){
        Recipe existRecipe = recipeRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Recipe not found"));

        existRecipe.setTitle(recipeDto.getTitle());
        existRecipe.setDescription(recipeDto.getDescription());
        recipeRepository.save(existRecipe);
    }

    @Transactional
    public void addIngredient(Long recipeId, String ingredientName, Double amount, String unit){
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new NoSuchElementException("Recipe not found"));

        Ingredient ingredient = ingredientRepository.findByName(ingredientName)
                .orElseGet(() -> ingredientRepository.save(Ingredient.builder().name(ingredientName).build()));

        boolean alreadyExists = recipe.getRecipeIngredients().stream()
                .anyMatch(ri -> ri.getIngredient().getName().equalsIgnoreCase(ingredientName));
        if (alreadyExists){
            throw new IllegalArgumentException("Ingredient already exists in the recipe");
        }
        RecipeIngredient recipeIngredient = RecipeIngredient.builder()
                .recipe(recipe)
                .ingredient(ingredient)
                .amount(amount)
                .unit(unit)
                .build();
        recipe.getRecipeIngredients().add(recipeIngredient);
        recipeIngredientRepository.save(recipeIngredient);

//        recipeRepository.save(recipe);
    }

//    @Transactional
//    public void removeIngredient(Long recipeId, Long ingredientId){
//        Recipe recipe = findById(recipeId);
//
//        Ingredient ingredient = ingredientRepository.findById(ingredientId)
//                .orElseThrow(() -> new NoSuchElementException("No ingredient found"));
//        recipe.getIngredients().remove(ingredient);
////        recipeRepository.save(recipe);
//    }
    public void removeIngredient(Long recipeIngredientId){
        recipeIngredientRepository.deleteById(recipeIngredientId);
    }

}
