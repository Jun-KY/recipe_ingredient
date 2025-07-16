package com.jun.recipe_ingredient.controller;

import com.jun.recipe_ingredient.dto.IngredientDto;
import com.jun.recipe_ingredient.model.Ingredient;
import com.jun.recipe_ingredient.service.IngredientService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController @RequiredArgsConstructor @RequestMapping("/ingredients")
public class IngredientController {
    private final IngredientService ingredientService;

    @GetMapping
    public String list(Model model){
        model.addAttribute("ingredients", ingredientService.findAll());
        return "ingredients/list";
    }

    @GetMapping("/new")
    public String createForm(Model model){
        model.addAttribute("ingredientDto", new IngredientDto());
        return "ingredients/form";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute("ingredientDto") IngredientDto ingredientDto, BindingResult bindingResult){
        if (bindingResult.hasErrors()) return "ingredients/form";

        Ingredient ingredient = Ingredient.builder().name(ingredientDto.getName()).build();
        ingredientService.saveIngredient(ingredient);
        return "redirect:/ingredients";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id){
        ingredientService.deleteIngredient(id);
        return "redirect:/ingredients";
    }
}
