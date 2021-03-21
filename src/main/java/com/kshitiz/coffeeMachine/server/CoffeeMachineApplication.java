package com.kshitiz.coffeeMachine.server;

import com.kshitiz.coffeeMachine.model.Beverage;
import com.kshitiz.coffeeMachine.model.MakeBeverageResponse;
import com.kshitiz.coffeeMachine.service.BeverageDiscoveryService;
import com.kshitiz.coffeeMachine.service.BeverageDiscoveryServiceImpl;
import com.kshitiz.coffeeMachine.service.CoffeeMachineService;
import com.kshitiz.coffeeMachine.service.InMemIngredientInventoryService;
import com.kshitiz.coffeeMachine.service.IngredientInventoryService;
import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;

public class CoffeeMachineApplication {
    private final CoffeeMachineService coffeeMachineService;
    private final BeverageDiscoveryService beverageDiscoveryService;
    private final IngredientInventoryService ingredientInventoryService;


    public CoffeeMachineApplication(final int noOfOutlets, final Map<String, Map<String, Integer>> beverageConfigurations) {
        this.ingredientInventoryService = new InMemIngredientInventoryService(new ArrayList<>(beverageConfigurations.keySet()));
        this.beverageDiscoveryService = new BeverageDiscoveryServiceImpl(beverageConfigurations);
        this.coffeeMachineService = null;
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
