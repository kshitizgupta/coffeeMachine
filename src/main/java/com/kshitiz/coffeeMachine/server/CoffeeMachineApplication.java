package com.kshitiz.coffeeMachine.server;

import com.kshitiz.coffeeMachine.model.Beverage;
import com.kshitiz.coffeeMachine.service.BeverageDiscoveryService;
import com.kshitiz.coffeeMachine.service.implementation.BeverageDiscoveryServiceImpl;
import com.kshitiz.coffeeMachine.service.CoffeeMachineService;
import com.kshitiz.coffeeMachine.service.implementation.CoffeeMachineServiceImpl;
import com.kshitiz.coffeeMachine.service.ControlPanel;
import com.kshitiz.coffeeMachine.service.implementation.ControlPanelImpl;
import com.kshitiz.coffeeMachine.service.implementation.InMemIngredientInventoryService;
import com.kshitiz.coffeeMachine.service.IngredientInventoryService;
import java.util.ArrayList;
import java.util.Map;

/**
 * This is entry point and the front end of the application. Responsible for
 * 1. Initializing various components of machine
 * 2. Acts as a orchestrator between different components
 * 3. Provides apis to make beverage, refilling ingredient, and shutdown
 *
 */
public class CoffeeMachineApplication {
    private final CoffeeMachineService coffeeMachineService;
    private final BeverageDiscoveryService beverageDiscoveryService;
    private final IngredientInventoryService ingredientInventoryService;
    private final ControlPanel controlPanel;


    public CoffeeMachineApplication(
        final int noOfOutlets,
        final Map<String, Integer> initialStock,
        final Map<String, Map<String, Integer>> beverageConfigurations
    ) {
        this.controlPanel = new ControlPanelImpl();
        this.beverageDiscoveryService = new BeverageDiscoveryServiceImpl(beverageConfigurations);
        this.ingredientInventoryService = new InMemIngredientInventoryService(new ArrayList<>(initialStock.keySet()));
        this.coffeeMachineService = new CoffeeMachineServiceImpl(ingredientInventoryService, controlPanel, noOfOutlets);
        initialStock.forEach(this::refill);
    }

    public void makeBeverage(final String beverageName) {
        final Beverage beverage = beverageDiscoveryService.find(beverageName);
        coffeeMachineService.makeBeverage(beverage);
    }

    public void refill(final String ingredientName, final int qty) {
        coffeeMachineService.refill(ingredientName, qty);
    }

    public void alertLowIngredients() {
        controlPanel.alert("Want to refill these items? " + String.join(",", coffeeMachineService.getLowIngredients()));
    }

    public void shutdown() {
        coffeeMachineService.shutdown();
    }

}
