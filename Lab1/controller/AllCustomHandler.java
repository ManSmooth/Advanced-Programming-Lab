package controller;

import core.Launcher;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.scene.input.*;
import model.item.BaseEquipment;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;
import model.item.*;
import javafx.scene.layout.StackPane;
import javafx.scene.image.Image;
import model.character.BaseCharacter;

import java.util.ArrayList;
import java.util.Arrays;

public class AllCustomHandler {
    private static boolean equip_handshake = false;
    private static String source = "";

    public static class GenCharacterHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            Launcher.setMainCharacter(GenCharacter.setUpCharacter());
            AllCustomHandler.Unequip("weapon");
            AllCustomHandler.Unequip("armor");
            Launcher.refreshPane();
        }
    }

    public static void onDragDetected(MouseEvent event, BaseEquipment equipment, ImageView imgView) {
        Dragboard db = imgView.startDragAndDrop(TransferMode.ANY);
        db.setDragView(imgView.getImage());
        ClipboardContent content = new ClipboardContent();
        content.put(BaseEquipment.DATA_FORMAT, equipment);
        db.setContent(content);
        event.consume();
    }

    public static void onDragOver(DragEvent event, EquipType type, String drag_source) {
        Dragboard dragboard = event.getDragboard();
        BaseCharacter character = Launcher.getMainCharacter();
        BaseEquipment retrievedEquipment = (BaseEquipment) dragboard.getContent(
                BaseEquipment.DATA_FORMAT);
        if (dragboard.hasContent(BaseEquipment.DATA_FORMAT)
                && (retrievedEquipment.getType() == type || type == null)
                && source.equals(drag_source))
            if (type == null)
                event.acceptTransferModes(TransferMode.MOVE);
            else if (type == EquipType.armor && Arrays.binarySearch(character.getAllow_armor(),
                    retrievedEquipment.getDamageType().getValue()) >= 0)
                event.acceptTransferModes(TransferMode.MOVE);
            else if (type == EquipType.weapon && Arrays.binarySearch(character.getAllow_weapon(),
                    retrievedEquipment.getDamageType().getValue()) >= 0)
                event.acceptTransferModes(TransferMode.MOVE);
    }

    public static void onDragDropped(DragEvent event, Label lbl, StackPane imgGroup) {
        boolean dragCompleted = false;
        Dragboard dragboard = event.getDragboard();
        ArrayList<BaseEquipment> allEquipments = Launcher.getAllEquipments();
        if (dragboard.hasContent(BaseEquipment.DATA_FORMAT)) {
            BaseEquipment retrievedEquipment = (BaseEquipment) dragboard.getContent(BaseEquipment.DATA_FORMAT);
            BaseCharacter character = Launcher.getMainCharacter();
            if (retrievedEquipment.getType() == EquipType.weapon && Arrays
                    .binarySearch(character.getAllow_weapon(), retrievedEquipment.getDamageType().getValue()) >= 0) {
                if (Launcher.getEquippedWeapon() != null)
                    allEquipments.add(Launcher.getEquippedWeapon());
                Launcher.setEquippedWeapon((GenericEquipment) retrievedEquipment);
                character.equipWeapon((GenericEquipment) retrievedEquipment);
                equip_handshake = true;
            } else if (retrievedEquipment.getType() == EquipType.armor && Arrays
                    .binarySearch(character.getAllow_armor(), retrievedEquipment.getDamageType().getValue()) >= 0) {
                if (Launcher.getEquippedArmor() != null)
                    allEquipments.add(Launcher.getEquippedArmor());
                Launcher.setEquippedArmor((GenericEquipment) retrievedEquipment);
                character.equipArmor((GenericEquipment) retrievedEquipment);
                equip_handshake = true;
            }
            Launcher.setMainCharacter(character);
            Launcher.setAllEquipments(allEquipments);
            ImageView imgView = new ImageView();
            if (imgGroup.getChildren().size() != 1) {
                imgGroup.getChildren().remove(1);
            }
            lbl.setText(retrievedEquipment.getClass().getSimpleName() + ":\n" + retrievedEquipment.getName());
            imgView.setImage(new Image(ImageHandler.getImage(retrievedEquipment.getImagepath())));
            imgGroup.getChildren().add(imgView);
            dragCompleted = true;
        }
        event.setDropCompleted(dragCompleted);
    }

    public static void onUnequipDragDropped(DragEvent event) {
        boolean dragCompleted = false;
        Dragboard dragboard = event.getDragboard();
        ArrayList<BaseEquipment> allEquipments = Launcher.getAllEquipments();
        if (dragboard.hasContent(BaseEquipment.DATA_FORMAT)) {
            BaseEquipment retrievedEquipment = (BaseEquipment) dragboard.getContent(BaseEquipment.DATA_FORMAT);
            BaseCharacter character = Launcher.getMainCharacter();
            if (retrievedEquipment.getType() == EquipType.weapon) {
                if (Launcher.getEquippedWeapon() != null)
                    allEquipments.add(Launcher.getEquippedWeapon());
                Launcher.setEquippedWeapon(null);
                character.unequipWeapon();
            } else if (retrievedEquipment.getType() == EquipType.armor) {
                if (Launcher.getEquippedArmor() != null)
                    allEquipments.add(Launcher.getEquippedArmor());
                Launcher.setEquippedArmor(null);
                character.unequipArmor();
            }
            Launcher.setMainCharacter(character);
            Launcher.setAllEquipments(allEquipments);
            Launcher.refreshPane();
            dragCompleted = true;
        }
        event.setDropCompleted(dragCompleted);
    }

    public static void onEquipDone(DragEvent event) {
        if (!equip_handshake) return;
        Dragboard dragboard = event.getDragboard();
        ArrayList<BaseEquipment> allEquipments = Launcher.getAllEquipments();
        BaseEquipment retrievedEquipment = (BaseEquipment) dragboard.getContent(BaseEquipment.DATA_FORMAT);
        int pos = -1;
        for (int i = 0; i < allEquipments.size(); i++) {
            if (allEquipments.get(i).getName().equals(retrievedEquipment.getName())) {
                pos = i;
            }
        }
        if (pos != -1) {
            allEquipments.remove(pos);
        }
        equip_handshake = false;
        Launcher.setAllEquipments(allEquipments);
        Launcher.refreshPane();
    }

    public static void Unequip(String slot) {
        BaseCharacter character = Launcher.getMainCharacter();
        ArrayList<BaseEquipment> allEquipments = Launcher.getAllEquipments();
        if (slot.toLowerCase().equals("weapon")) {
            if (Launcher.getEquippedWeapon() != null)
                allEquipments.add(Launcher.getEquippedWeapon());
            character.unequipWeapon();
            Launcher.setEquippedWeapon(null);
        }
        if (slot.toLowerCase().equals("armor")) {
            if (Launcher.getEquippedArmor() != null)
                allEquipments.add(Launcher.getEquippedArmor());
            character.unequipArmor();
            Launcher.setEquippedArmor(null);
        }
        Launcher.setMainCharacter(character);
        Launcher.setAllEquipments(allEquipments);
    }

    public static void setSource(String source) {
        AllCustomHandler.source = source;
    }
}
