package com.kshitiz.coffeeMachine.service;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class CoffeeMachineServiceImplTest {
    private CoffeeMachineServiceImpl service;

    @Before
    public void setUp() throws Exception {
        service = new CoffeeMachineServiceImpl(null, null, 2);
    }


}