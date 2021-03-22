package com.kshitiz.coffeeMachine;

import static org.junit.Assert.*;

import com.kshitiz.coffeeMachine.model.Beverage;
import com.kshitiz.coffeeMachine.model.ReserveIngredientResponse;
import com.kshitiz.coffeeMachine.model.ReserveIngredientStatus;
import java.util.Collections;
import java.util.Map;
import org.junit.Test;

public class MessagingUtilTest {

    @Test
    public void buildAlertMessage() {
        Beverage beverage = new Beverage("xyz", Map.of(
            "ingr1", 2
        ));
        ReserveIngredientResponse response = new ReserveIngredientResponse(ReserveIngredientStatus.OK);

        String alertMessage = MessagingUtil.buildAlertMessage(beverage, response);

        assertTrue(alertMessage.contains("xyz is prepared"));
    }
}