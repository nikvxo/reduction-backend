package com.nik.reduction.service;
import com.nik.reduction.dto.SpoonacularRecipe;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Arrays;
import java.util.List;
@Service
public class SpoonacularService {
    @Value("${spoonacular.api.key}")
    private String apiKey;
    @Value("${spoonacular.api.base-url}")
    private String baseUrl;
    private final RestTemplate restTemplate = new RestTemplate();
    public List<SpoonacularRecipe> searchRecipesByIngredients(String ingredients, int number) {
        String url = baseUrl + "/recipes/findByIngredients?ingredients=" + ingredients
                + "&number=" + number + "&apiKey=" + apiKey;
        SpoonacularRecipe[] recipes = restTemplate.getForObject(url, SpoonacularRecipe[].class);
        return recipes == null ? List.of() : Arrays.asList(recipes);
    }
}
