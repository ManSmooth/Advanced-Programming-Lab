package view;

import java.util.ArrayList;
import java.util.concurrent.FutureTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ExecutionException;
import javafx.geometry.Insets;
import javafx.scene.layout.FlowPane;
import controller.draw.DrawCurrencyInfoTask;
import model.*;

public class CurrencyParentPane extends FlowPane {
    public CurrencyParentPane(ArrayList<Currency> currencyList) throws ExecutionException, InterruptedException {
        this.setPadding(new Insets(0));
        refreshPane(currencyList);
    }

    public void refreshPane(ArrayList<Currency> currencyList) throws ExecutionException, InterruptedException {
        this.getChildren().clear();
        ExecutorService executor = Executors.newFixedThreadPool(10);
        ArrayList<FutureTask<CurrencyPane>> pool = new ArrayList<>();
        for (int i = 0; i < currencyList.size(); i++) {
            FutureTask<CurrencyPane> ft = new FutureTask<CurrencyPane>(
                    new DrawCurrencyInfoTask(currencyList.get(i)));
            pool.add(ft);
            executor.execute(ft);
        }
        pool.forEach((ft) -> {
            try {
                this.getChildren().add(ft.get());
            } catch (InterruptedException e) {
                System.out.println(e.getMessage()); 
            } catch (ExecutionException e) {
                System.out.println(e.getMessage()); 
            }
        });
    }
}
