package com.kshitiz.coffeeMachine.model;

import java.util.Collections;
import java.util.Map;

public class Beverage {
    private final String name;
    private final Map<String, Integer> composition;

    public Beverage(final String name, final Map<String, Integer> composition) {
        this.name = name;
        if(composition == null || composition.size() == 0) {
            throw new IllegalArgumentException("Composition cant be empty");
        }
        this.composition = Collections.unmodifiableMap(composition);
    }

    public String getName() {
        return name;
    }

    public Map<String, Integer> getComposition() {
        return composition;
    }
}
