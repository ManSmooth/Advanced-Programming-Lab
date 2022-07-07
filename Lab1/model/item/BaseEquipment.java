package model.item;

import java.io.Serializable;
import javafx.scene.input.DataFormat;
import model.character.DamageType;

public class BaseEquipment implements Serializable {
    public static final DataFormat DATA_FORMAT = new DataFormat("model.item.BaseEquipment");
    protected String name;
    protected String imgpath;
    protected DamageType damageType;
    protected EquipType type;
    protected int power, defense, resistance;

    public String getName() {
        return name;
    }

    public String getImagepath() {
        return imgpath;
    }

    public void setImagepath(String imgpath) {
        this.imgpath = imgpath;
    }

    public DamageType getDamageType() {
        return damageType;
    }

    public EquipType getType() {
        return type;
    }
}
