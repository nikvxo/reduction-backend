package com.nik.reduction.controller;
import com.nik.reduction.dto.SpoonacularRecipe;
import com.nik.reduction.service.SpoonacularService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/api/spoonacular")
public class SpoonacularController {
    private final SpoonacularService spoonacularService;
    public SpoonacularController(SpoonacularService spoonacularService) {
        this.spoonacularService = spoonacularService;
    }
    @GetMapping("/search")
    public List<SpoonacularRecipe> searchByIngredients(
            @RequestParam String ingredients,
            @RequestParam(defaultValue = "5") int number) {
        return spoonacularService.searchRecipesByIngredients(ingredients, number);
    }
}
