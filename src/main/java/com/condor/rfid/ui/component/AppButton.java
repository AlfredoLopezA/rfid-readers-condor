package com.condor.rfid.ui.component;

import javafx.scene.control.Button;

public class AppButton extends Button {
    public AppButton(String text) {
        super(text);
        this.getStyleClass().add("app-button");
    }
}