package com.kshitiz.coffeeMachine.service;

import com.kshitiz.coffeeMachine.model.Beverage;
import java.util.Map;

/**
 * This is a repository of beverage definitions i.e. composition and names for beverage which the
 * machine is capable of producing
 */
public interface BeverageDiscoveryService {
    Beverage find(final String beverageName);
    Map<String, Beverage> learnBeverages(final Map<String, Map<String, Integer>> beverages);
}
