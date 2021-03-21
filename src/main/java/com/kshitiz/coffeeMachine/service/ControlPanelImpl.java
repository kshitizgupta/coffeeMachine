package com.kshitiz.coffeeMachine.service;

public class ControlPanelImpl implements ControlPanel {
    @Override
    public void alert(final String message) {
        System.out.printf(message + "\n");
    }
}
