package com.kshitiz.coffeeMachine;

import com.kshitiz.coffeeMachine.server.CoffeeMachineApplication;
import com.kshitiz.coffeeMachine.server.CoffeeMachineFactory;

/**
 * Reading json configuration provided by the client
 */
public class CafeCoffeeDayUsesConfiguration {
    public static void main(String[] args) {
        CoffeeMachineApplication coffeeMachine  = CoffeeMachineFactory.getFromConfig("src/main/resources/machine-config.json");

        coffeeMachine.makeBeverage("hot_tea");
        coffeeMachine.makeBeverage("hot_coffee");
        coffeeMachine.makeBeverage("black_tea");
        coffeeMachine.makeBeverage("green_tea");
        coffeeMachine.makeBeverage("elaichee_tea");

        coffeeMachine.shutdown();
    }
}
