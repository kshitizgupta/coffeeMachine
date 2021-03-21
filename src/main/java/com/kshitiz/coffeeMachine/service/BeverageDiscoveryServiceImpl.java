package com.kshitiz.coffeeMachine.service;

import com.kshitiz.coffeeMachine.model.Beverage;
import java.util.HashMap;
import java.util.Map;

public class BeverageDiscoveryServiceImpl implements BeverageDiscoveryService {
    private final Map<String, Beverage> beverageDefinitions;

    public BeverageDiscoveryServiceImpl(final Map<String, Map<String, Integer>> beverages) {
        this.beverageDefinitions = new HashMap<>();
        learnBeverages(beverages);
    }

    public Beverage find(final String beverageName) {
        return beverageDefinitions.get(beverageName);
    }

    public void learnBeverages(final Map<String, Map<String, Integer>> beverages) {
        beverages.forEach((beverageName, compositionMap) -> {
            final Map<String, Integer> composition = new HashMap<>();
            compositionMap.forEach(composition::put);
            beverageDefinitions.put(beverageName, new Beverage(beverageName, composition));
        });
    }
}
