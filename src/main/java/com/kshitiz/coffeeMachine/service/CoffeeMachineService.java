package com.kshitiz.coffeeMachine.service;

import com.kshitiz.coffeeMachine.model.Beverage;
import java.util.Set;

/**
 * This service is responsible for
 * 1. Providing apis to make beverage, refill, get current low ingredients
 * 2. Takes care of no of outlets in the machine and parallely processing
 *    concurrent requests
 * 3. Maintains a view of ingredients which migh currently be in low levels
 */
public interface CoffeeMachineService {
    void makeBeverage(final Beverage beverage);
    void refill(final String ingredient, final int qty);
    Set<String> getLowIngredients();
    void shutdown();
}
