package com.condor.rfid.ui.component;

import javafx.scene.control.ComboBox;

public class AppComboBox<T> extends ComboBox<T> {
    public AppComboBox() {
        super();
        this.getStyleClass().add("app-combo-box");
    }
}