package com.kshitiz.coffeeMachine;

import com.kshitiz.coffeeMachine.model.Beverage;
import com.kshitiz.coffeeMachine.model.ReserveIngredientResponse;
import com.kshitiz.coffeeMachine.model.ReserveIngredientStatus;
import java.util.ArrayList;
import java.util.List;

public class MessagingUtil {
    private final static String BEVERAGE_PREPARED = "is prepared";
    private final static String BEVERAGE_NOT_PREPARED = "cannot be prepared because";
    private final static String NOT_AVAILABLE_STR = "is not available";
    private final static String NOT_SUFFICIENT_STR = "is not sufficient";

    public static String buildAlertMessage(final Beverage beverage, final ReserveIngredientResponse response) {
        final List<String> messages = new ArrayList<>();

        //Add beverage name to output
        messages.add(beverage.getName());

        //Add further messaging based on response
        if (ReserveIngredientStatus.OK.equals(response.getStatus())) {
            messages.add(BEVERAGE_PREPARED);
        } else {
            messages.add(BEVERAGE_NOT_PREPARED);

            //Record all the ingredients which are not available at all
            if (!response.getNotAvailableIngredients().isEmpty()) {
                messages.add(String.join(", ", response.getNotAvailableIngredients()));
                messages.add(NOT_AVAILABLE_STR);
            }

            //Record all the ingredients which are less in quantity
            if (!response.getNotSufficientIngredients().isEmpty()) {
                messages.add(String.join(", ", response.getNotSufficientIngredients()));
                messages.add(NOT_SUFFICIENT_STR);
            }
        }

        final StringBuilder alertMessage = new StringBuilder();
        messages.forEach(str -> alertMessage.append(str).append(" "));

        return alertMessage.toString();
    }
}
