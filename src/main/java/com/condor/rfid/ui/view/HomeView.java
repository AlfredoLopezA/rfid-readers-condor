package com.condor.rfid.ui.view;

import com.condor.rfid.config.I18nManager;
import com.condor.rfid.config.ReaderConfig;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class HomeView extends VBox {

    public HomeView(
            I18nManager i18n,
            ReaderConfig config
    ) {

        setPadding(new Insets(20));
        setSpacing(10);

        Label title =
                new Label(i18n.get("app.title"));

        Label status =
                new Label(i18n.get("message.configuration.loaded"));

        Label reader =
                new Label(
                        "Brand: " + config.getReaderBrand()
                        + "\nIP: " + config.getReaderIp()
                        + "\nModel: " + config.getReaderModel()
                        + "\nFirmware: " + config.getReaderFirmware()
                );

        getChildren().addAll(
                title,
                status,
                reader
        );
    }
}