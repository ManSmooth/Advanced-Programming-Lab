package controller;

import core.Launcher;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert;
import java.util.Optional;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import model.*;

public class AllEventHandlers {
    public static void onRefresh() {
        try {
            FetchData.refreshData();
            Launcher.refreshPane();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void onAdd() {
        try {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Add Currency");
            dialog.setContentText("Currency code:");
            dialog.setHeaderText(null);
            dialog.setGraphic(null);
            Optional<String> code = dialog.showAndWait();
            if (!code.isPresent()) return;
            if (FetchData.addToFetch(code.get())) {
                ArrayList<Currency> currency_list = Launcher.getCurrencyList();
                Currency c = new Currency(code.get());
                ArrayList<CurrencyEntity> c_list = FetchData.fetch_range(c.getShortCode(), 14);
                c.setHistorical(c_list);
                c.setCurrent(c_list.get(c_list.size() - 1));
                currency_list.add(c);
                Launcher.setCurrencyList(currency_list);
                Launcher.refreshPane();
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle(null);
                alert.setHeaderText(null);
                alert.setContentText("Invalid Code.");
                alert.showAndWait();
                onAdd();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public static void onDelete(String code) {
        try {
            ArrayList<Currency> currency_list = Launcher.getCurrencyList();
            int i = -1;
            if ((i = FetchData.remove(code)) >= 0) {
                Launcher.getCurrencyList().remove(i);
                Launcher.setCurrencyList(currency_list);
                Launcher.refreshPane();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public static void onWatch(String code) {
        try {
            ArrayList<Currency> currency_list = Launcher.getCurrencyList();
            int index = -1;
            for (int i = 0; i < currency_list.size(); i++) {
                if (currency_list.get(i).getShortCode().equals(code)) {
                    index = i;
                    break;
                }
            }
            if (index != -1) {
                TextInputDialog dialog = new TextInputDialog();
                dialog.setTitle("Add Watch");
                dialog.setContentText("Rate:");
                dialog.setHeaderText(null);
                dialog.setGraphic(null);
                Optional<String> retrievedRate = dialog.showAndWait();
                if (retrievedRate.isPresent()) {
                    double rate = Double.parseDouble(retrievedRate.get());
                    currency_list.get(index).setWatch(true);
                    currency_list.get(index).setWatchRate(rate);
                }
                Launcher.setCurrencyList(currency_list);
                Launcher.refreshPane();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public static void onUnwatch(String code) {
        try {
            ArrayList<Currency> currency_list = Launcher.getCurrencyList();
            int index = -1;
            for (int i = 0; i < currency_list.size(); i++) {
                if (currency_list.get(i).getShortCode().equals(code)) {
                    index = i;
                    break;
                }
            }
            if (index != -1) {
                currency_list.get(index).setWatch(false);
                Launcher.setCurrencyList(currency_list);
                Launcher.refreshPane();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
