package com.kshitiz.coffeeMachine.server;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Map;

public class CoffeeMachineFactory {
    public static CoffeeMachineApplication getFromConfig(final String configPath) {
        int noOfOutlets = 0;
        Map<String, Integer> initialStock = Collections.emptyMap();
        Map<String, Map<String, Integer>> beverageDef = Collections.emptyMap();
        try {
            String json = readFileAsString(configPath);

            // create object mapper instance
            ObjectMapper mapper = new ObjectMapper();
            final ObjectNode node = mapper.readValue(json, ObjectNode.class);

            final JsonNode machineNode = node.findValue("machine");

            noOfOutlets = machineNode.findValue("count_n").asInt();
            initialStock = mapper.convertValue(machineNode.get("total_items_quantity"), new TypeReference<>() {});
            beverageDef = mapper.convertValue(machineNode.get("beverages"), new TypeReference<>() {
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        CoffeeMachineApplication application = new CoffeeMachineApplication(noOfOutlets, initialStock, beverageDef);
        return application;
    }

    private static String readFileAsString(final String fileName)throws Exception
    {
        String data = "";
        data = new String(Files.readAllBytes(Paths.get(fileName)));
        return data;
    }
}
