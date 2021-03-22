package com.kshitiz.coffeeMachine.service;

import com.kshitiz.coffeeMachine.service.implementation.CoffeeMachineServiceImpl;
import org.junit.Before;
import org.junit.Test;

public class CoffeeMachineServiceImplTest {
    private CoffeeMachineServiceImpl service;

    @Before
    public void setUp() throws Exception {
        service = new CoffeeMachineServiceImpl(null, null, 2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidCreation(){
        CoffeeMachineService service = new CoffeeMachineServiceImpl(null, null, 0);
    }


}