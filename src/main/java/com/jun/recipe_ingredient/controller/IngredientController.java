package com.jun.recipe_ingredient.controller;

import com.jun.recipe_ingredient.dto.IngredientDto;
import com.jun.recipe_ingredient.model.Ingredient;
import com.jun.recipe_ingredient.service.IngredientService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hibernate.engine.spi.SelfDirtinessTracker;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor @RequestMapping("/ingredients")
public class IngredientController {
    private final IngredientService ingredientService;

    @GetMapping
    public String list(@RequestParam(value = "q", required = false) String query, Model model){
        if (query != null && !query.isBlank()){
            model.addAttribute("ingredients", ingredientService.search(query));
        }else{
            model.addAttribute("ingredients", ingredientService.findAll());
        }
        model.addAttribute("query", query);
        return "ingredients";
    }

    @GetMapping("/new")
    public String createForm(Model model){
        model.addAttribute("ingredientDto", new IngredientDto());
        return "ingredient-form";
    }

//    @GetMapping("/new")
//    public String addForm(Model model){
//        model.addAttribute("ingredient", new Ingredient());
//        return "ingredient-form";
//    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model){
        Ingredient ingredient = ingredientService.findById(id);
        IngredientDto ingredientDto = new IngredientDto();
        ingredientDto.setName(ingredient.getName());
        model.addAttribute("ingredientDto", ingredientDto);
        model.addAttribute("ingredientId", id);
        return "ingredient-form";
    }

    @PostMapping("/new")
    public String create(@Valid @ModelAttribute("ingredientDto") IngredientDto ingredientDto,
                         BindingResult bindingResult){
        if (ingredientService.existsByName(ingredientDto.getName())){
            bindingResult.rejectValue("name", "duplicate", "Ingredient name already exist");
        }
        if(bindingResult.hasErrors()) return "ingredient-form";

        Ingredient ingredient = Ingredient.builder().name(ingredientDto.getName()).build();
        ingredientService.saveIngredient(ingredient);
        return "redirect:/ingredients";
    }

//    @PostMapping("/new")
//    public String add(@ModelAttribute("ingredient") @Valid Ingredient ingredient,
//                      BindingResult bindingResult,
//                      Model model){
//        if (bindingResult.hasErrors()){
//            return "ingredient-form";
//        }
//        ingredientService.saveIngredient(ingredient);
//        return "redirect:/ingredients";
//    }

    @PostMapping("/{id}/edit")
    public String edit(@PathVariable Long id,
                       @ModelAttribute("ingredientDto") @Valid IngredientDto ingredientDto,
                       BindingResult bindingResult,
                       Model model){
        if (ingredientService.existsByName(ingredientDto.getName(), id)){
            bindingResult.rejectValue("name", "duplicate", "Ingredient name already exist");
        }
        if(bindingResult.hasErrors()){
            model.addAttribute("ingredientId", id);
            return "ingredient-form";
        }
        ingredientService.update(id, ingredientDto.getName());
        return "redirect:/ingredients";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id,
                         Model model, RedirectAttributes redirectAttributes){
        try{
            ingredientService.deleteIngredient(id);
            return "redirect:/ingredients";
        }catch (IllegalStateException e){
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/ingredients";
        }
    }
}
