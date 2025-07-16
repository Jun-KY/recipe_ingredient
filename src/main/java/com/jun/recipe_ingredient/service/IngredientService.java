package com.jun.recipe_ingredient.service;

import com.jun.recipe_ingredient.model.Ingredient;
import com.jun.recipe_ingredient.repository.IngredientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service @Transactional @RequiredArgsConstructor
public class IngredientService {

    private final IngredientRepository ingredientRepository;

    public List<Ingredient> findAll(){
        return ingredientRepository.findAll();
    }

    public Ingredient findById(Long id){
        return ingredientRepository.findById(id).orElseThrow(() -> new NoSuchElementException("No ingredient found"));
    }

    public Ingredient saveIngredient(Ingredient ingredient){
        return ingredientRepository.save(ingredient);
    }

    public void deleteIngredient(Long id){
        ingredientRepository.deleteById(id);
    }
}
