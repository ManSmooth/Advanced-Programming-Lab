package view;

import model.item.*;
import controller.AllCustomHandler;
import controller.ImageHandler;
import core.Launcher;
import javafx.scene.layout.*;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Label;
import javafx.scene.image.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.input.DragEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

public class EquipmentPane extends ScrollPane {
    private GenericEquipment equippedWeapon, equippedArmor;

    public EquipmentPane() {
    }

    private Pane getDetailsPane() {
        Pane equipmentInfoPane = new VBox(10);
        equipmentInfoPane.setBorder(null);
        ((VBox) equipmentInfoPane).setAlignment(Pos.CENTER);
        equipmentInfoPane.setPadding(new Insets(25, 25, 25, 25));
        Label weaponLbl, armorLbl;
        StackPane weaponImgGroup = new StackPane();
        StackPane armorImgGroup = new StackPane();
        ImageView bg1 = new ImageView();
        ImageView bg2 = new ImageView();
        ImageView weaponImg = new ImageView();
        ImageView armorImg = new ImageView();
        bg1.setImage(new Image(ImageHandler.getImage("/assets/blank.png")));
        bg2.setImage(new Image(ImageHandler.getImage("/assets/blank.png")));
        weaponImgGroup.getChildren().add(bg1);
        armorImgGroup.getChildren().add(bg2);
        Button uneq_w = new Button();
        uneq_w.setText("Unequip Weapon");
        uneq_w.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                AllCustomHandler.Unequip("weapon");
                Launcher.refreshPane();
            }
        });
        Button uneq_a = new Button();
        uneq_a.setText("Unequip Armor");
        uneq_a.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                AllCustomHandler.Unequip("armor");
                Launcher.refreshPane();
            }
        });

        if (equippedWeapon != null) {
            weaponLbl = new Label("Weapon:\n" + equippedWeapon.getName());
            weaponImg.setImage(new Image(ImageHandler.getImage(equippedWeapon.getImagepath())));
            weaponImgGroup.getChildren().add(weaponImg);
            uneq_w.setVisible(true);
            weaponImgGroup.setOnDragDetected(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {
                    AllCustomHandler.setSource("equipment");
                    AllCustomHandler.onDragDetected(event, equippedWeapon, weaponImg);
                }
            });
        } else {
            weaponLbl = new Label("Weapon:");
            weaponImg.setImage(new Image(ImageHandler.getImage("/assets/blank.png")));
            uneq_w.setVisible(false);
        }
        if (equippedArmor != null) {
            armorLbl = new Label("Armor: \n" + equippedArmor.getName());
            armorImg.setImage(new Image(ImageHandler.getImage(equippedArmor.getImagepath())));
            armorImgGroup.getChildren().add(armorImg);
            uneq_a.setVisible(true);
            armorImgGroup.setOnDragDetected(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {
                    AllCustomHandler.setSource("equipment");
                    AllCustomHandler.onDragDetected(event, equippedArmor, armorImg);
                }
            });
        } else {
            armorLbl = new Label("Armor:");
            armorImg.setImage(new Image(ImageHandler.getImage("/assets/blank.png")));
            uneq_a.setVisible(false);
        }
        weaponImgGroup.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent e) {
                AllCustomHandler.onDragOver(e, EquipType.weapon, "inventory");
            }
        });
        armorImgGroup.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent e) {
                AllCustomHandler.onDragOver(e, EquipType.armor, "inventory");
            }
        });
        weaponImgGroup.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent e) {
                AllCustomHandler.onDragDropped(e, weaponLbl, weaponImgGroup);
            }
        });
        armorImgGroup.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent e) {
                AllCustomHandler.onDragDropped(e, armorLbl, armorImgGroup);
            }
        });
        equipmentInfoPane.getChildren().addAll(weaponLbl, weaponImgGroup, uneq_w, armorLbl,
                armorImgGroup, uneq_a);
        return equipmentInfoPane;
    }

    public void drawPane(GenericEquipment equippedWeapon, GenericEquipment equippedArmor) {
        this.equippedWeapon = equippedWeapon;
        this.equippedArmor = equippedArmor;
        Pane equipmentInfo = getDetailsPane();
        this.setStyle("-fx-background-color:Black;");
        this.setContent(equipmentInfo);
    }
}
