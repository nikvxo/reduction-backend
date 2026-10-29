package com.nik.reduction.controller;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.Matchers.startsWith;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.queryParam;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@SpringBootTest(properties = "spoonacular.api.key=test-api-key")
@AutoConfigureMockMvc
class SpoonacularControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private RestTemplate restTemplate;
    private MockRestServiceServer mockServer;
    @BeforeEach
    void setUp() {
        mockServer = MockRestServiceServer.bindTo(restTemplate).ignoreExpectOrder(true).build();
    }
    @Test
    void searchReturnsRecipes() throws Exception {
        mockServer.expect(requestTo(startsWith("https://api.spoonacular.com/recipes/findByIngredients")))
                .andExpect(method(HttpMethod.GET))
                .andExpect(queryParam("ingredients", "tomato,cheese"))
                .andExpect(queryParam("number", "1"))
                .andExpect(queryParam("apiKey", "test-api-key"))
                .andRespond(withSuccess("""
                        [{
                          "id": 648368,
                          "title": "Jalapeno Queso With Goat Cheese",
                          "image": "https://img.spoonacular.com/recipes/648368-312x231.jpg",
                          "imageType": "jpg",
                          "usedIngredientCount": 2,
                          "missedIngredientCount": 2,
                          "usedIngredients": [],
                          "missedIngredients": []
                        }]
                        """, MediaType.APPLICATION_JSON));
        mockMvc.perform(get("/api/spoonacular/search")
                        .param("ingredients", "tomato,cheese")
                        .param("number", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(648368))
                .andExpect(jsonPath("$[0].title").value("Jalapeno Queso With Goat Cheese"));
        mockServer.verify();
    }
    @Test
    void searchRejectsBlankIngredients() throws Exception {
        mockMvc.perform(get("/api/spoonacular/search")
                        .param("ingredients", "   "))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Ingredients are required"));
    }
    @Test
    void searchReturnsBadGatewayWhenSpoonacularFails() throws Exception {
        mockServer.expect(requestTo(startsWith("https://api.spoonacular.com/recipes/findByIngredients")))
                .andExpect(method(HttpMethod.GET))
                .andExpect(queryParam("ingredients", "tomato"))
                .andExpect(queryParam("number", "1"))
                .andExpect(queryParam("apiKey", "test-api-key"))
                .andRespond(withStatus(org.springframework.http.HttpStatus.SERVICE_UNAVAILABLE));
        mockMvc.perform(get("/api/spoonacular/search")
                        .param("ingredients", "tomato")
                        .param("number", "1"))
                .andExpect(status().isBadGateway())
                .andExpect(jsonPath("$.message").value("Failed to fetch recipes from Spoonacular"));
        mockServer.verify();
    }
}
