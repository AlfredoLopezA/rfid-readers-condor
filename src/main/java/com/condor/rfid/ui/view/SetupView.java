package com.condor.rfid.ui.view;

import com.condor.rfid.config.I18nManager;
import com.condor.rfid.config.ReaderBrand;
import com.condor.rfid.ui.component.AppButton;
import com.condor.rfid.ui.component.AppComboBox;
import com.condor.rfid.ui.component.AppTextField;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class SetupView extends GridPane {
    private final AppComboBox<ReaderBrand> cmbReaderBrand;
    private final AppTextField txtReaderIp;
    private final AppButton btnConnect;
    public SetupView(I18nManager i18n) {
        setPadding(new Insets(20));
        setHgap(10);
        setVgap(15);
        Label lblBrand = new Label(i18n.get("setup.reader.brand"));
        Label lblIp = new Label(i18n.get("setup.reader.ip"));
        cmbReaderBrand = new AppComboBox<>();
        cmbReaderBrand.getItems().addAll(ReaderBrand.values());
        cmbReaderBrand.getSelectionModel().selectFirst();
        txtReaderIp = new AppTextField();
        btnConnect = new AppButton(i18n.get("setup.button.connect"));
        add(lblBrand, 0, 0);
        add(cmbReaderBrand, 1, 0);
        add(lblIp, 0, 1);
        add(txtReaderIp, 1, 1);
        add(btnConnect, 1, 2);
    }

    public AppComboBox<ReaderBrand> getReaderBrand() {
        return cmbReaderBrand;
    }

    public AppTextField getReaderIp() {
        return txtReaderIp;
    }

    public AppButton getConnectButton() {
        return btnConnect;
    }
}