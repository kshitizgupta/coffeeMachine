package com.kshitiz.coffeeMachine.service;

/**
 * Represents control panel and offers apis for services to
 * deliver message on the machine's front panel
 */
public class ControlPanelImpl implements ControlPanel {
    @Override
    public void alert(final String message) {
        System.out.printf(message + "\n");
    }
}
