package controller.draw;

import java.util.concurrent.Callable;
import model.Currency;
import view.CurrencyPane;

public class DrawCurrencyInfoTask implements Callable<CurrencyPane> {
    private Currency currency;
    public DrawCurrencyInfoTask(Currency currency) {
        this.currency = currency;
    }

    @Override
    public CurrencyPane call() throws Exception {
        return new CurrencyPane(currency);
    }
}
