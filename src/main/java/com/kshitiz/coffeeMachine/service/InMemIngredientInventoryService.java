package com.kshitiz.coffeeMachine.service;

import com.kshitiz.coffeeMachine.model.ReserveIngredientResponse;
import com.kshitiz.coffeeMachine.model.ReserveIngredientStatus;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemIngredientInventoryService implements IngredientInventoryService {
    private final Map<String, Integer> ingredientQuantityMap;
    private final Object mutex = new Object();

    public InMemIngredientInventoryService(final List<String> ingredients) {
        this.ingredientQuantityMap = new HashMap<>();
        ingredients.forEach(ingredient -> ingredientQuantityMap.put(ingredient, 0));
    }

    @Override
    public ReserveIngredientResponse reserve(final Map<String, Integer> ingredientList) {
        final List<String> notAvailable = new ArrayList<>();
        final List<String> notSufficient = new ArrayList<>();

        synchronized (mutex) {
            for (Map.Entry<String, Integer> e : ingredientList.entrySet()) {
                if (!ingredientQuantityMap.containsKey(e.getKey())) {
                    notAvailable.add(e.getKey());
                } else {
                    if (ingredientQuantityMap.get(e.getKey()) < e.getValue()) {
                        notSufficient.add(e.getKey());
                    }
                }
            }

            if (notAvailable.isEmpty() && notSufficient.isEmpty()) {
                for (Map.Entry<String, Integer> e : ingredientList.entrySet()) {
                    final int stockAvailable = ingredientQuantityMap.get(e.getKey());
                    final int stockNeeded = e.getValue();
                    final int updatedQuantity = stockAvailable - stockNeeded;
                    ingredientQuantityMap.put(e.getKey(), updatedQuantity);
                }
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
        if (ingredient == null) {
            return;
        }
        synchronized (mutex) {
            if (ingredientQuantityMap.containsKey(ingredient)) {
                ingredientQuantityMap.put(ingredient, ingredientQuantityMap.get(ingredient) + qty);
            }
        }
    }
}
