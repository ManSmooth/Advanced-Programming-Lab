package view;

import model.item.BaseEquipment;
import controller.ImageHandler;
import controller.AllCustomHandler;
import javafx.scene.layout.*;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.*;
import javafx.geometry.Insets;
import java.util.ArrayList;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.DragEvent;

public class InventoryPane extends ScrollPane {
    private ArrayList<BaseEquipment> equipmentArray;

    public InventoryPane() {
    }

    private Pane getDetailsPane() {
        GridPane inventoryInfoPane = new GridPane();
        inventoryInfoPane.setBorder(null);
        inventoryInfoPane.setPadding(new Insets(10, 10, 10, 10));
        inventoryInfoPane.setHgap(10);
        inventoryInfoPane.setVgap(10);
        if (equipmentArray != null) {
            ImageView[] imageViewList = new ImageView[equipmentArray.size()];
            for (int i = 0; i < equipmentArray.size(); i++) {
                imageViewList[i] = new ImageView();
                imageViewList[i]
                        .setImage(new Image(ImageHandler.getImage(equipmentArray.get(i).getImagepath())));
                int finalI = i;
                imageViewList[i].setOnDragDetected(new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent event) {
                        AllCustomHandler.setSource("inventory");
                        AllCustomHandler.onDragDetected(event, equipmentArray.get(finalI), imageViewList[finalI]);
                    }
                });
                imageViewList[i].setOnDragDone(new EventHandler<DragEvent>() {
                    @Override
                    public void handle(DragEvent event) {
                        AllCustomHandler.onEquipDone(event);
                    }
                });
                inventoryInfoPane.add(imageViewList[i], i % 8, i / 8, 1, 1);
            }
        }
        inventoryInfoPane.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent e) {
                AllCustomHandler.onDragOver(e, null, "equipment");
            }
        });
        inventoryInfoPane.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent e) {
                AllCustomHandler.onUnequipDragDropped(e);
            }
        });
        return inventoryInfoPane;
    }

    public void drawPane(ArrayList<BaseEquipment> equipmentArray) {
        this.equipmentArray = equipmentArray;
        Pane invetoryInfo = getDetailsPane();
        this.setStyle("-fx-background-color:Black;");
        this.setContent(invetoryInfo);
    }
}
