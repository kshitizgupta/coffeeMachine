package com.kshitiz.coffeeMachine.service;

import com.kshitiz.coffeeMachine.model.ReserveIngredientResponse;
import java.util.Map;

public interface IngredientInventoryService {
    ReserveIngredientResponse reserve(final Map<String, Integer> ingredientList);
    void refillIngredient(final String ingredient, final int qty);
}
