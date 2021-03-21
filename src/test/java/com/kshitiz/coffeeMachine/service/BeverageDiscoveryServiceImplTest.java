package com.kshitiz.coffeeMachine.service;

import static org.junit.Assert.*;

import com.kshitiz.coffeeMachine.model.Beverage;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

public class BeverageDiscoveryServiceImplTest {
    private BeverageDiscoveryService service;
    private Map<String, Map<String, Integer>> beverageConfiguration;
    final Map<String, Integer> hotTea = Map.of(
        "hot_water", 200
        , "hot_milk", 100
        , "ginger_syrup", 10
        , "sugar_syrup", 10
        , "tea_leaves_syrup", 30);
    final Map<String, Integer> hotCoffee = Map.of(
        "hot_water", 100
        , "hot_milk", 400
        , "ginger_syrup", 30
        , "sugar_syrup", 50
        , "tea_leaves_syrup", 30
    );
    final Map<String, Integer> blackTea = Map.of(
        "hot_water", 300
        , "ginger_syrup", 30
        , "sugar_syrup", 50
        , "tea_leaves_syrup", 30
    );

    {
        beverageConfiguration = Map.of(
            "hot_tea", hotTea
            , "hot_coffee", hotCoffee
            , "black_tea", blackTea
        );
    }

    @Before
    public void setUp() throws Exception {
        service = new BeverageDiscoveryServiceImpl(beverageConfiguration);
    }

    @Test
    public void test() {
        assertTrue(areEqual(hotTea, service.find("hot_tea").getComposition()));
        assertTrue(areEqual(hotCoffee, service.find("hot_coffee").getComposition()));
    }

    private boolean areEqual(Map<String, Integer> first, Map<String, Integer> second) {
        if (first.size() != second.size()) {
            return false;
        }

        return first.entrySet().stream()
            .allMatch(e -> e.getValue().equals(second.get(e.getKey())));
    }
}