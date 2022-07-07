package view;

import model.character.BaseCharacter;
import controller.AllCustomHandler;
import controller.ImageHandler;
import javafx.scene.layout.*;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.*;
import javafx.geometry.Insets;

public class CharacterPane extends ScrollPane {
    private BaseCharacter character;

    public CharacterPane() {
    }

    private Pane getDetailsPane() {
        Pane characterInfoPane = new VBox(10);
        characterInfoPane.setBorder(null);
        characterInfoPane.setPadding(new Insets(25, 25, 25, 25));
        Label name, classname, type, hp, atk, def, res;
        ImageView mainImage = new ImageView();
        if (this.character != null) {
            name = new Label("Name: " + character.getName());
            classname = new Label(character.getClassname());
            mainImage.setImage(new Image(ImageHandler.getImage(character.getImagepath())));
            hp = new Label("HP: " + character.getHp().toString() + "/" + character.getFullHp().toString());
            type = new Label("Type: " + character.getType().toString());
            atk = new Label("ATK: " + character.getPower());
            def = new Label("DEF: " + character.getDefense());
            res = new Label("RES: " + character.getResistance());
        } else {
            name = new Label("Name: ");
            classname = new Label("");
            mainImage.setImage(new Image(ImageHandler.getImage("/assets/unknown.png")));
            hp = new Label("HP: ");
            type = new Label("Type: ");
            atk = new Label("ATK: ");
            def = new Label("DEF: ");
            res = new Label("RES: ");
        }
        Button genCharacter = new Button();
        genCharacter.setText("Generate Character");
        genCharacter.setOnAction(new AllCustomHandler.GenCharacterHandler());
        characterInfoPane.getChildren().addAll(name, classname, mainImage, type, hp, atk, def, res,
                genCharacter);
        return characterInfoPane;
    }

    public void drawPane(BaseCharacter character) {
        this.character = character;
        Pane characterInfo = getDetailsPane();
        this.setStyle("-fx-background-color:Black;");
        this.setContent(characterInfo);
    }
}
