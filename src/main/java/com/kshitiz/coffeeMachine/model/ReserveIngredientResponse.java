package com.kshitiz.coffeeMachine.model;

import java.util.Collections;
import java.util.List;

public class ReserveIngredientResponse {
    private final ReserveIngredientStatus status;
    private final List<String> notAvailableIngredients;
    private final List<String> notSufficientIngredients;

    public ReserveIngredientResponse(final ReserveIngredientStatus status, final List<String> notAvailableIngredients, final List<String> notSufficientIngredients) {
        this.status = status;
        this.notAvailableIngredients = Collections.unmodifiableList(notAvailableIngredients);
        this.notSufficientIngredients = Collections.unmodifiableList(notSufficientIngredients);
    }

    public ReserveIngredientStatus getStatus() {
        return status;
    }

    public List<String> getNotAvailableIngredients() {
        return notAvailableIngredients;
    }

    public List<String> getNotSufficientIngredients() {
        return notSufficientIngredients;
    }
}
