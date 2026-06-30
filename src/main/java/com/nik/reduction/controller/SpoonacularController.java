package com.nik.reduction.controller;
import com.nik.reduction.dto.SpoonacularRecipe;
import com.nik.reduction.service.SpoonacularService;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
        if (!StringUtils.hasText(ingredients)) {
            throw new IllegalArgumentException("Ingredients are required");
        }
        if (number < 1) {
            throw new IllegalArgumentException("Number must be at least 1");
        }
        return spoonacularService.searchRecipesByIngredients(ingredients, number);
    }
}
