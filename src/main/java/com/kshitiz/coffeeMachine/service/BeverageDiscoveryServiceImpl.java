package com.kshitiz.coffeeMachine.service;

import com.kshitiz.coffeeMachine.model.Beverage;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class BeverageDiscoveryServiceImpl implements BeverageDiscoveryService {
    private final Map<String, Beverage> beverageDefinitions;

    public BeverageDiscoveryServiceImpl(final Map<String, Map<String, Integer>> beverageConfiguration) {
        this.beverageDefinitions = learnBeverages(beverageConfiguration);
    }

    public Beverage find(final String beverageName) {
        return beverageDefinitions.get(beverageName);
    }

    public Map<String, Beverage> learnBeverages(final Map<String, Map<String, Integer>> beverageConfiguration) {
        final Map<String, Beverage> beverageDefinitionsLocal = new HashMap<>();

        beverageConfiguration.forEach((beverageName, compositionMap) -> {
            final Map<String, Integer> composition = new HashMap<>();
            compositionMap.forEach(composition::put);
            beverageDefinitionsLocal.put(beverageName, new Beverage(beverageName, composition));
        });

        return Collections.unmodifiableMap(beverageDefinitionsLocal);
    }
}
