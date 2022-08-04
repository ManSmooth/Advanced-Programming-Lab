package view;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.geometry.Insets;
import controller.AllEventHandlers;
import controller.FetchData;
import javafx.event.*;

public class TopPane extends FlowPane {
    private Button refresh;
    private Button add;

    private Label update;

    public TopPane() {
        this.setPadding(new Insets(10));
        this.setHgap(10);
        this.setPrefSize(640, 20);
        add = new Button("Add");
        refresh = new Button("Refresh");
        refresh.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                AllEventHandlers.onRefresh();
            }
        });
        add.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                AllEventHandlers.onAdd();
            }
        });
        update = new Label();
        refreshPane();
        this.getChildren().addAll(refresh, add, update);
    }

    public void refreshPane() {
        update.setText(String.format("Last update: %s", FetchData.getLastUpdate()));
    }
}
