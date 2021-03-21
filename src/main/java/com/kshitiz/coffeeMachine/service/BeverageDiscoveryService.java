package com.kshitiz.coffeeMachine.service;

import com.kshitiz.coffeeMachine.model.Beverage;
import java.util.Map;

public interface BeverageDiscoveryService {
    Beverage find(final String beverageName);
    void learnBeverages(final Map<String, Map<String, Integer>> beverages);
}
