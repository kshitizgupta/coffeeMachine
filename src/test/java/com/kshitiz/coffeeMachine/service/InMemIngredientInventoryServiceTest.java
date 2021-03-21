package com.kshitiz.coffeeMachine.service;

import static org.junit.Assert.*;

import com.kshitiz.coffeeMachine.model.ReserveIngredientResponse;
import com.kshitiz.coffeeMachine.model.ReserveIngredientStatus;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

public class InMemIngredientInventoryServiceTest {
    private List<String> ingredients = Arrays.asList("milk", "water");
    private IngredientInventoryService service;

    @Before
    public void setUp() throws Exception {
        service = new InMemIngredientInventoryService(ingredients);
    }

    @Test
    public void testWhenIngredientsAreEmpty() {
        ReserveIngredientResponse response = service.reserve(Map.of(
            "milk", 10
        ));

        assertEquals(ReserveIngredientStatus.OUT_OF_STOCK, response.getStatus());
        assertEquals(0, response.getNotAvailableIngredients().size());
        assertEquals(1, response.getNotSufficientIngredients().size());
        assertSame("milk", response.getNotSufficientIngredients().get(0));
    }

    @Test
    public void testWhenIngredientsArePresent() {
        service.refillIngredient("milk", 10);

        ReserveIngredientResponse response = service.reserve(Map.of(
            "milk", 10
        ));

        assertEquals(ReserveIngredientStatus.OK, response.getStatus());
        assertEquals(0, response.getNotAvailableIngredients().size());
        assertEquals(0, response.getNotSufficientIngredients().size());
    }
}