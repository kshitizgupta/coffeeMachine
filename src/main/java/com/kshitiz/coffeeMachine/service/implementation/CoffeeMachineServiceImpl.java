package com.kshitiz.coffeeMachine.service.implementation;

import com.kshitiz.coffeeMachine.model.Beverage;
import com.kshitiz.coffeeMachine.model.ReserveIngredientResponse;
import com.kshitiz.coffeeMachine.service.CoffeeMachineService;
import com.kshitiz.coffeeMachine.service.ControlPanel;
import com.kshitiz.coffeeMachine.service.IngredientInventoryService;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CoffeeMachineServiceImpl implements CoffeeMachineService {
    private final IngredientInventoryService ingredientInventoryService;
    private final ControlPanel controlPanel;
    private final ExecutorService executorService;

    private final Set<String> lowStockIngredients;
    private final Object lowStockIngredientLock = new Object();

    public CoffeeMachineServiceImpl(final IngredientInventoryService ingredientInventoryService, final ControlPanel controlPanel, final int noOfOutlets) {
        this.ingredientInventoryService = ingredientInventoryService;
        this.controlPanel = controlPanel;

        if (noOfOutlets <= 0) {
            throw new IllegalArgumentException("No of outlets provided must be greater than 0");
        }

        this.executorService = Executors.newFixedThreadPool(noOfOutlets);
        lowStockIngredients = new HashSet<>();
    }

    @Override
    public void makeBeverage(final Beverage beverage) {
        executorService.submit(() -> {
            final ReserveIngredientResponse response = ingredientInventoryService.reserve(beverage.getComposition());

            /*
             update lowStockIngredients if any found in response. The approach Ive taken here is to record an ingredient
             running low only when there was a failure to reserve some stock. And further this ingredient will be removed
             from this list when a refill of this ingredient happens. For simplicity, I assumed that any refill will cause
             the ingredient to come out of low running indicator
             */
            if (response.getNotSufficientIngredients() != null && response.getNotSufficientIngredients().size() > 0) {
                addLow(response.getNotSufficientIngredients());
            }

            //Build an appropriate message to alert on control panel as per the response
            final String alertMessage = buildAlertMessage(beverage, response);

            controlPanel.alert(alertMessage);

        });
    }

    @Override
    public void refill(final String ingredient, final int qty) {
        ingredientInventoryService.refillIngredient(ingredient, qty);
        removeLow(ingredient);
    }

    @Override
    public Set<String> getLowIngredients() {
        return Collections.unmodifiableSet(lowStockIngredients);
    }

    @Override
    public void shutdown() {
        executorService.shutdown();
    }

    private void addLow(final List<String> notSufficientIngredients) {
        synchronized (lowStockIngredientLock) {
            lowStockIngredients.addAll(notSufficientIngredients);
        }
    }

    private void removeLow(final String ingredient) {
        if (lowStockIngredients.contains(ingredient)) {
            synchronized (lowStockIngredientLock) {
                lowStockIngredients.remove(ingredient);
            }
        }
    }
}
