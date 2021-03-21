package com.kshitiz.coffeeMachine.service;

import com.kshitiz.coffeeMachine.model.Beverage;
import com.kshitiz.coffeeMachine.model.MakeBeverageResponse;
import java.util.Set;

public interface CoffeeMachineService {
    MakeBeverageResponse makeBeverage(final Beverage beverage);
    void refill(final String ingredient, final int qty);
    Set<String> getLowIngredients();
}
