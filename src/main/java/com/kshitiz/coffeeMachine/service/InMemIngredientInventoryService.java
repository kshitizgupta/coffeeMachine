package com.kshitiz.coffeeMachine.service;

import com.kshitiz.coffeeMachine.model.ReserveIngredientResponse;
import com.kshitiz.coffeeMachine.model.ReserveIngredientStatus;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class InMemIngredientInventoryService implements IngredientInventoryService {
    private final Map<String, Integer> ingredientQuantityMap;
    private final Set<String> validIngredients;
    private final Object mutex = new Object();

    public InMemIngredientInventoryService(final List<String> ingredients) {
        this.ingredientQuantityMap = new HashMap<>();
        ingredients.forEach(ingredient -> ingredientQuantityMap.put(ingredient, 0));
        validIngredients = Set.copyOf(ingredients);
    }

    @Override
    public ReserveIngredientResponse reserve(final Map<String, Integer> ingredientList) {
        final List<String> notAvailable = new ArrayList<>();
        final List<String> notSufficient = new ArrayList<>();

        synchronized (mutex) {
            //Find out all not available and not sufficient ingredient list
            ingredientList.forEach((ingredient, qtyRequired) -> {
                if (!validIngredients.contains(ingredient)) {
                    notAvailable.add(ingredient);
                } else if (ingredientQuantityMap.get(ingredient) < qtyRequired) {
                    notSufficient.add(ingredient);
                }
            });

            boolean possibleToReserve = notAvailable.isEmpty() && notSufficient.isEmpty();

            if (possibleToReserve) {
                ingredientList.forEach((ingredient, qtyRequired) -> {
                    //Updating inventory after reserving
                    final int stockAvailable = ingredientQuantityMap.get(ingredient);
                    final int stockNeeded = qtyRequired;
                    final int updatedQuantity = stockAvailable - stockNeeded;
                    ingredientQuantityMap.put(ingredient, updatedQuantity);
                });
            }
        }

        if (notAvailable.isEmpty() && notSufficient.isEmpty()) {
            return new ReserveIngredientResponse(ReserveIngredientStatus.OK);
        } else {
            return new ReserveIngredientResponse(ReserveIngredientStatus.OUT_OF_STOCK, notAvailable, notSufficient);
        }
    }

    @Override
    public void refillIngredient(final String ingredient, final int qty) {
        if (ingredient == null || !validIngredients.contains(ingredient) || qty <= 0) {
            return;
        }
        synchronized (mutex) {
            ingredientQuantityMap.put(ingredient, ingredientQuantityMap.get(ingredient) + qty);
        }
    }
}
