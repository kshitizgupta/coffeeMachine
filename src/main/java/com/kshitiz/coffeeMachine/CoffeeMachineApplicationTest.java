package com.kshitiz.coffeeMachine;

import com.kshitiz.coffeeMachine.server.CoffeeMachineApplication;
import java.util.Map;

public class CoffeeMachineApplicationTest {
    private static Map<String, Integer> initStock = Map.of(
        "hot_water", 500
        , "hot_milk", 500
        , "ginger_syrup", 100
        , "sugar_syrup", 100
        , "tea_leaves_syrup", 100
    );

    private static final Map<String, Integer> hotTea = Map.of(
        "hot_water", 200
        , "hot_milk", 100
        , "ginger_syrup", 10
        , "sugar_syrup", 10
        , "tea_leaves_syrup", 30);

    private static final Map<String, Integer> hotCoffee = Map.of(
        "hot_water", 100
        , "hot_milk", 400
        , "ginger_syrup", 30
        , "sugar_syrup", 50
        , "tea_leaves_syrup", 30
    );

    private static final Map<String, Integer> blackTea = Map.of(
        "hot_water", 300
        , "ginger_syrup", 30
        , "sugar_syrup", 50
        , "tea_leaves_syrup", 30
    );

    private static final Map<String, Integer> greenTea = Map.of(
        "hot_water", 100
        , "ginger_syrup", 30
        , "sugar_syrup", 50
        , "tea_leaves_syrup", 30
    );

    private static final Map<String, Integer> elaicheeTea = Map.of(
        "hot_water", 100
        , "elaichee", 100
        , "ginger_syrup", 30
        , "sugar_syrup", 50
        , "tea_leaves_syrup", 30
    );

    public static CoffeeMachineApplication init() {

        CoffeeMachineApplication application = new com.kshitiz.coffeeMachine.server.CoffeeMachineApplication(3, Map.of(
            "hot_tea", hotTea
            , "hot_coffee", hotCoffee
            , "black_tea", blackTea
            , "green_tea", greenTea
            , "elaichee_tea", elaicheeTea
        ));

        initStock.forEach(application::refill);

        return application;
    }

    public static void main(String[] args) {
        final CoffeeMachineApplication application = init();
        application.makeBeverage("hot_tea");
        application.makeBeverage("hot_coffee");
        application.makeBeverage("black_tea");
        application.makeBeverage("green_tea");
        application.makeBeverage("elaichee_tea");
    }
}
