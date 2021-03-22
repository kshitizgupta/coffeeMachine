package com.kshitiz.coffeeMachine.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.when;

import com.kshitiz.coffeeMachine.model.Beverage;
import com.kshitiz.coffeeMachine.model.ReserveIngredientResponse;
import com.kshitiz.coffeeMachine.service.implementation.CoffeeMachineServiceImpl;
import com.kshitiz.coffeeMachine.service.implementation.InMemIngredientInventoryService;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CoffeeMachineServiceImplTest {
    private CoffeeMachineServiceImpl service;

    @Mock
    private IngredientInventoryService ingredientInventoryService;

    @Mock
    ReserveIngredientResponse response;

    @Mock
    Beverage beverage;

    @Mock
    private ControlPanel controlPanel;

    @Before
    public void setUp() throws Exception {
        service = new CoffeeMachineServiceImpl(ingredientInventoryService, controlPanel, 2);
        when(ingredientInventoryService.reserve(anyMap())).thenReturn(response);
        when(beverage.getName()).thenReturn("GoodDrink");
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenIllegalNoOfOutletsProvided() {
        new CoffeeMachineServiceImpl(null, null, 0);
    }

    @Test
    public void testWhenInsufficientIngredientsLowIngredientsGetsUpdated() throws InterruptedException {
        when(response.getNotSufficientIngredients()).thenReturn(List.of("milk"));

        service.makeBeverage(beverage);
        Thread.sleep(2000);   //Not an ideal way of doing this, should find a better way

        assertEquals(1, service.getLowIngredients().size());
        assertTrue(service.getLowIngredients().contains("milk"));
    }
}