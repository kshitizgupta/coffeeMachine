package com.kshitiz.coffeeMachine.clientExamples;

import com.kshitiz.coffeeMachine.server.CoffeeMachineApplication;
import java.util.Map;

/**
 * Customized client with beverages hard coded
 */
public class ChaiPointCafeCustomizedClient {
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

    public static void main(String[] args) {
        final CoffeeMachineApplication coffeeMachine = new CoffeeMachineApplication(
            3,
            initStock,
            Map.of(
                "hot_tea", hotTea
                , "hot_coffee", hotCoffee
                , "black_tea", blackTea
                , "green_tea", greenTea
                , "elaichee_tea", elaicheeTea
            ));

        coffeeMachine.makeBeverage("hot_tea");
        coffeeMachine.makeBeverage("hot_coffee");
        coffeeMachine.makeBeverage("black_tea");
        coffeeMachine.makeBeverage("green_tea");
        coffeeMachine.makeBeverage("elaichee_tea");

        coffeeMachine.shutdown();
    }
}