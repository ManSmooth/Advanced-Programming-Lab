package core;

import model.character.*;
import model.item.*;
import view.*;
import controller.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.*;
import java.util.ArrayList;
import java.util.Comparator;

public class Launcher extends Application {
    private static Scene mainScene;
    private static BaseCharacter mainCharacter = null;
    private static ArrayList<BaseEquipment> allEquipments = null;
    private static GenericEquipment equippedWeapon = null;
    private static GenericEquipment equippedArmor = null;
    private static CharacterPane characterPane = null;
    private static EquipmentPane equipPane = null;
    private static InventoryPane inventoryPane = null;

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Intro to RPG");
        primaryStage.setResizable(false);
        primaryStage.show();
        mainCharacter = GenCharacter.setUpCharacter();
        allEquipments = GenItemList.setUpItemList();
        Pane mainPane = getMainPane();
        mainScene = new Scene(mainPane);
        primaryStage.setScene(mainScene);
    }

    public Pane getMainPane() {
        BorderPane mainPane = new BorderPane();
        characterPane = new CharacterPane();
        equipPane = new EquipmentPane();
        inventoryPane = new InventoryPane();
        refreshPane();
        mainPane.setCenter(characterPane);
        mainPane.setLeft(equipPane);
        mainPane.setBottom(inventoryPane);
        return mainPane;
    }

    public static void refreshPane() {
        allEquipments.sort(Comparator.comparing(BaseEquipment::getName));
        characterPane.drawPane(mainCharacter);
        equipPane.drawPane(equippedWeapon, equippedArmor);
        inventoryPane.drawPane(allEquipments);
    }

    public static void setMainCharacter(BaseCharacter mainCharacter) {
        Launcher.mainCharacter = mainCharacter;
    }

    public static BaseCharacter getMainCharacter() {
        return mainCharacter;
    }

    public static void setEquippedWeapon(GenericEquipment equippedWeapon) {
        Launcher.equippedWeapon = equippedWeapon;
    }

    public static GenericEquipment getEquippedWeapon() {
        return equippedWeapon;
    }

    public static void setEquippedArmor(GenericEquipment equippedArmor) {
        Launcher.equippedArmor = equippedArmor;
    }

    public static GenericEquipment getEquippedArmor() {
        return equippedArmor;
    }

    public static void setAllEquipments(ArrayList<BaseEquipment> allEquipments) {
        Launcher.allEquipments = allEquipments;
    }

    public static ArrayList<BaseEquipment> getAllEquipments() {
        return allEquipments;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
