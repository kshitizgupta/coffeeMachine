package com.kshitiz.coffeeMachine.service;

import com.kshitiz.coffeeMachine.model.Beverage;
import com.kshitiz.coffeeMachine.model.ReserveIngredientResponse;
import com.kshitiz.coffeeMachine.model.ReserveIngredientStatus;
import java.util.ArrayList;
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
    private final Set<String> lowQtyIngredients;
    private final static String BEVERAGE_PREPARED = "is prepared";
    private final static String BEVERAGE_NOT_PREPARED = "cannot be prepared because";
    private final static String NOT_AVAILABLE_STR = "is not available";
    private final static String NOT_SUFFICIENT_STR = "is not sufficient";


    public CoffeeMachineServiceImpl(final IngredientInventoryService ingredientInventoryService, final ControlPanel controlPanel, final int noOfOutlets) {
        this.ingredientInventoryService = ingredientInventoryService;
        this.controlPanel = controlPanel;

        if (noOfOutlets <= 0) {
            throw new IllegalArgumentException("No of outlets provided must be greater than 0");
        }

        this.executorService = Executors.newFixedThreadPool(noOfOutlets);
        lowQtyIngredients = new HashSet<>();
    }

    @Override
    public void makeBeverage(final Beverage beverage) {
        executorService.submit(() -> {
            final ReserveIngredientResponse response = ingredientInventoryService.reserve(beverage.getComposition());

            final StringBuilder alertMessage = new StringBuilder();

            evaluateResult(beverage, response, alertMessage);

            controlPanel.alert(alertMessage.toString());

        });
    }

    @Override
    public void refill(final String ingredient, final int qty) {
        ingredientInventoryService.refillIngredient(ingredient, qty);
        removeLow(ingredient);
    }

    @Override
    public Set<String> getLowIngredients() {
        return Collections.unmodifiableSet(lowQtyIngredients);
    }

    @Override
    public void shutdown() {
        executorService.shutdown();
    }

    private void evaluateResult(final Beverage beverage, final ReserveIngredientResponse response, final StringBuilder message) {
        final List<String> messages = new ArrayList<>();
        messages.add(beverage.getName());
        if (ReserveIngredientStatus.OK.equals(response.getStatus())) {
            messages.add(BEVERAGE_PREPARED);
        } else {
            messages.add(BEVERAGE_NOT_PREPARED);

            //Record all the ingredients which are not available at all
            if (!response.getNotAvailableIngredients().isEmpty()) {
                messages.add(String.join(", ", response.getNotAvailableIngredients()));
                messages.add(NOT_AVAILABLE_STR);
            }

            //Record all the ingredients which are less in quantity
            if (!response.getNotSufficientIngredients().isEmpty()) {
                messages.add(String.join(", ", response.getNotSufficientIngredients()));
                addLow(response.getNotSufficientIngredients());
                messages.add(NOT_SUFFICIENT_STR);
            }
        }
        messages.forEach(str -> message.append(str).append(" "));
    }

    synchronized private void addLow(final List<String> notSufficientIngredients) {
        lowQtyIngredients.addAll(notSufficientIngredients);
    }
    synchronized private void removeLow(final String ingredient) {
        lowQtyIngredients.remove(ingredient);
    }
}
