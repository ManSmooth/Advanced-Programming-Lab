package core;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.layout.*;
import javafx.scene.Scene;
import java.util.concurrent.ExecutionException;
import java.util.ArrayList;
import model.*;
import view.*;
import controller.*;

public class Launcher extends Application {
    private static Stage primaryStage;
    private static Scene mainScene;
    private static FlowPane mainPane;
    private static TopPane topPane;
    private static CurrencyParentPane currencyParentPane;
    private static ArrayList<Currency> currencyList;

    @Override
    public void start(Stage _primaryStage) throws ExecutionException, InterruptedException {
        primaryStage = _primaryStage;
        primaryStage.setTitle("Currency Watcher");
        primaryStage.setResizable(false);
        Initialize.initialize_app();
        initMainPane();
        mainScene = new Scene(mainPane);
        primaryStage.setScene(mainScene);
        primaryStage.show();
        RefreshTask r = new RefreshTask();
        Thread th = new Thread(r);
        th.setDaemon(true);
        th.start();
    }

    public void initMainPane() throws ExecutionException, InterruptedException {
        mainPane = new FlowPane();
        topPane = new TopPane();
        currencyParentPane = new CurrencyParentPane(currencyList);
        mainPane.getChildren().add(topPane);
        mainPane.getChildren().add(currencyParentPane);
    }

    public static void setCurrencyList(ArrayList<Currency> currencyList) {
        Launcher.currencyList = currencyList;
    }

    public static ArrayList<Currency> getCurrencyList() {
        return currencyList;
    }

    public static void refreshPane() throws ExecutionException, InterruptedException {
        topPane.refreshPane();
        currencyParentPane.refreshPane(currencyList);
        primaryStage.sizeToScene();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
