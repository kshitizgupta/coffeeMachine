package com.kshitiz.coffeeMachine.server;

import com.kshitiz.coffeeMachine.model.Beverage;
import com.kshitiz.coffeeMachine.model.MakeBeverageResponse;
import com.kshitiz.coffeeMachine.service.BeverageDiscoveryService;
import com.kshitiz.coffeeMachine.service.CoffeeMachineService;

public class CoffeeMachineApplication {
    private final CoffeeMachineService coffeeMachineService;
    private final BeverageDiscoveryService beverageDiscoveryService;

    public CoffeeMachineApplication(final CoffeeMachineService coffeeMachineService, final BeverageDiscoveryService beverageDiscoveryService) {
        this.coffeeMachineService = coffeeMachineService;
        this.beverageDiscoveryService = beverageDiscoveryService;
    }

    public MakeBeverageResponse makeBeverage(final String beverageName) {
        final Beverage beverage = beverageDiscoveryService.find(beverageName);

        MakeBeverageResponse response = coffeeMachineService.makeBeverage(beverage);

        return response;
    }

    public void refill(final String ingredientName, final int qty) {
        coffeeMachineService.refill(ingredientName, qty);
    }
}
