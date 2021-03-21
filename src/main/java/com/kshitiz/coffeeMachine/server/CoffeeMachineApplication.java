package com.kshitiz.coffeeMachine.server;

import com.kshitiz.coffeeMachine.model.Beverage;
import com.kshitiz.coffeeMachine.model.MakeBeverageResponse;
import com.kshitiz.coffeeMachine.service.BeverageDiscoveryService;
import com.kshitiz.coffeeMachine.service.BeverageDiscoveryServiceImpl;
import com.kshitiz.coffeeMachine.service.CoffeeMachineService;
import com.kshitiz.coffeeMachine.service.CoffeeMachineServiceImpl;
import com.kshitiz.coffeeMachine.service.ControlPanel;
import com.kshitiz.coffeeMachine.service.ControlPanelImpl;
import com.kshitiz.coffeeMachine.service.InMemIngredientInventoryService;
import com.kshitiz.coffeeMachine.service.IngredientInventoryService;
import java.util.ArrayList;
import java.util.Map;

public class CoffeeMachineApplication {
    private final CoffeeMachineService coffeeMachineService;
    private final BeverageDiscoveryService beverageDiscoveryService;
    private final IngredientInventoryService ingredientInventoryService;
    private final ControlPanel controlPanel;


    public CoffeeMachineApplication(final int noOfOutlets, final Map<String, Map<String, Integer>> beverageConfigurations) {
        this.ingredientInventoryService = new InMemIngredientInventoryService(new ArrayList<>(beverageConfigurations.keySet()));
        this.beverageDiscoveryService = new BeverageDiscoveryServiceImpl(beverageConfigurations);
        this.controlPanel = new ControlPanelImpl();
        this.coffeeMachineService = new CoffeeMachineServiceImpl(ingredientInventoryService, controlPanel, noOfOutlets);

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
}
