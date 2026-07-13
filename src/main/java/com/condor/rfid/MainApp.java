package com.condor.rfid;

import com.condor.rfid.agent.CondorAgent;
import com.condor.rfid.config.I18nManager;
import com.condor.rfid.config.ReaderConfig;
import com.condor.rfid.config.ReaderConfigManager;
import com.condor.rfid.service.ReaderSetupService;
import com.condor.rfid.ui.dialog.MessageDialog;
import com.condor.rfid.ui.view.HomeView;
import com.condor.rfid.ui.view.SetupView;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    private final I18nManager i18n = new I18nManager();
    private final ReaderConfigManager configManager = new ReaderConfigManager();
    private final ReaderSetupService setupService = new ReaderSetupService();
    private final CondorAgent agent = new CondorAgent();
    
    private Stage stage;

    @Override
    public void start(Stage stage) {
        this.stage = stage;
        try {
            if (configManager.exists()) {
                ReaderConfig config = configManager.load();
                openHome(config);
            } else {
                openSetup();
            }
            stage.setResizable(false);
            stage.show();
        } catch (Exception e) {
            MessageDialog.error(i18n.get("app.title"), e.getMessage());
        }
    }

    private void openSetup() {
        SetupView root = new SetupView(i18n);
        root.getConnectButton().setOnAction(event -> {
            try {
                String readerIp = root.getReaderIp().getText();
                if (readerIp == null || readerIp.isBlank()) {
                    MessageDialog.error(i18n.get("setup.title"), i18n.get("message.required.reader.ip"));
                    return;
                }
                ReaderConfig config = setupService.createConfiguration(root.getReaderBrand().getValue().name(), readerIp.trim());
                MessageDialog.info(
                        i18n.get("message.configuration.created"),
                        "Brand: " + config.getReaderBrand()
                                + "\nIP: " + config.getReaderIp()
                                + "\nModel: " + config.getReaderModel()
                                + "\nModel Number: " + config.getReaderModelNumber()
                                + "\nFirmware: " + config.getReaderFirmware()
                                + "\nMax Antennas: " + config.getReaderMaxAntennas()
                );
                openHome(config);
            } catch (Exception e) {
                MessageDialog.error(i18n.get("setup.title"), e.getMessage());
            }
        });
        Scene scene = new Scene(root, 520, 180);
        applyScene(scene);
        stage.setTitle(i18n.get("setup.title"));
        stage.setScene(scene);
    }

    private void openHome(ReaderConfig config) throws Exception {
        startReaderService(config);
        HomeView root = new HomeView(i18n, config);
        Scene scene = new Scene(root, 520, 260);
        applyScene(scene);
        stage.setTitle(i18n.get("app.title"));
        stage.setScene(scene);
    }

    private void startReaderService(ReaderConfig config) throws Exception {
        if (agent.getRfidReaderService().isConnected()) { return; }
        System.out.println("Iniciando servicio RFID...");
        System.out.println("IP lector: " + config.getReaderIp());
        agent.getRfidReaderService().connect(
                config.getReaderBrand(),
                config.getReaderIp());
        agent.start();
        System.out.println("Servicio RFID iniciado.");
    }

    private void applyScene(Scene scene) {
        scene.getStylesheets().add(getClass().getResource("/theme/condor.css").toExternalForm());
    }

    @Override
    public void stop() throws Exception {
        agent.stop();
        super.stop();
    }

    public static void main(String[] args) {
        launch(args);
    }
}