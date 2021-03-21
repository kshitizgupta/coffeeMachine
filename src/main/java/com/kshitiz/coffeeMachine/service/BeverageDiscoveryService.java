package com.kshitiz.coffeeMachine.service;

import com.kshitiz.coffeeMachine.model.Beverage;

public interface BeverageDiscoveryService {
    Beverage find(final String beverageName);
}
