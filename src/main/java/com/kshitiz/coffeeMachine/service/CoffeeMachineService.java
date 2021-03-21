package com.kshitiz.coffeeMachine.service;

import com.kshitiz.coffeeMachine.model.Beverage;
import java.util.Set;

public interface CoffeeMachineService {
    void makeBeverage(final Beverage beverage);
    void refill(final String ingredient, final int qty);
    Set<String> getLowIngredients();
}
