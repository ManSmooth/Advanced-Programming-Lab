package controller.draw;

import model.*;
import java.util.concurrent.Callable;
import javafx.scene.layout.*;
import javafx.scene.chart.*;
import javafx.geometry.Insets;

public class DrawGraphTask implements Callable<VBox> {
    Currency currency;

    public DrawGraphTask(Currency currency) {
        this.currency = currency;
    }

    @Override
    public VBox call() throws Exception {
        VBox graphPane = new VBox(10);
        graphPane.setPadding(new Insets(0, 25, 5, 25));
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        yAxis.setAutoRanging(true);
        LineChart<String, Double> lineChart = new LineChart(xAxis, yAxis);
        lineChart.setLegendVisible(false);
        if (this.currency != null) {
            XYChart.Series<String, Double> series = new XYChart.Series<>();
            double min_y = Double.MAX_VALUE;
            double max_y = Double.MIN_VALUE;
            for (CurrencyEntity c : currency.getHistorical()) {
                series.getData().add(new XYChart.Data<String, Double>(c.getTimestamp(), c.getRate()));
                if (c.getRate() > max_y)
                    max_y = c.getRate();
                if (c.getRate() < min_y)
                    min_y = c.getRate();
            }
            yAxis.setAutoRanging(false);
            yAxis.setLowerBound(min_y - (max_y - min_y) / 2);
            yAxis.setUpperBound(max_y + (max_y - min_y) / 2);
            yAxis.setTickUnit((max_y - min_y) / 2);
            lineChart.getData().add(series);
        }
        graphPane.getChildren().add(lineChart);
        return graphPane;
    }
}
