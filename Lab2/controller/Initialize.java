package controller;

import model.*;
import java.util.ArrayList;
import core.Launcher;

public class Initialize {
    public static void initialize_app() {
        FetchData.getAllShorthands();
        Currency c = new Currency("usd");
        FetchData.addToFetch(c.getShortCode());
        ArrayList<CurrencyEntity> c_list = FetchData.fetch_range(c.getShortCode(), 14);
        c.setHistorical(c_list);
        c.setCurrent(c_list.get(c_list.size() - 1));
        ArrayList<Currency> currencyList = new ArrayList<>();
        currencyList.add(c);
        Launcher.setCurrencyList(currencyList);
    }
}
