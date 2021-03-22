package com.kshitiz.coffeeMachine;

import com.kshitiz.coffeeMachine.server.CoffeeMachineApplication;
import com.kshitiz.coffeeMachine.server.CoffeeMachineFactory;

public class CoffeeMachineApplicationTestFromJson {
    public static void main(String[] args) {
        CoffeeMachineApplication application  = CoffeeMachineFactory.getFromConfig("src/main/resources/machine-config.json");

        application.makeBeverage("hot_tea");
        application.makeBeverage("hot_coffee");
        application.makeBeverage("black_tea");
        application.makeBeverage("green_tea");
        application.makeBeverage("elaichee_tea");

    }
}
