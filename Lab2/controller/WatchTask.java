package controller;

import java.util.concurrent.Callable;
import java.util.ArrayList;
import javafx.scene.control.Alert;
import model.*;
import core.Launcher;

class WatchTask implements Callable<Void> {
    @Override
    public Void call() {
        ArrayList<Currency> allCurrency = Launcher.getCurrencyList();
        String found = allCurrency.stream().filter(Currency::getWatch)
                .filter((curr) -> curr.getWatchRate() > curr.getCurrent().getRate()).map((elem) -> elem.getShortCode().toUpperCase())
                .reduce("", (identity, accumulator) -> identity += String.format("%s ", accumulator)).strip();
        found = found.replaceAll("\\s", " and ");
        if (!found.equals("")) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle(null);
            alert.setHeaderText(null);
            if (found.length() > 3) {
                alert.setContentText(String.format("%s have become lower than the watchrate!", found));
            } else {
                alert.setContentText(String.format("%s has become lower than the watchrates!", found));
            }
            alert.showAndWait();
        }
        return null;
    }
}
