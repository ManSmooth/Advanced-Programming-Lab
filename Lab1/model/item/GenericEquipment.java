package model.item;

import model.character.DamageType;

public class GenericEquipment extends BaseEquipment {

    public GenericEquipment(String name, EquipType type, int power, int defense, int resistance, DamageType dtype,
            String imgpath) {
        this.name = name;
        this.type = type;
        this.imgpath = imgpath;
        this.power = power;
        this.defense = defense;
        this.resistance = resistance;
        this.damageType = dtype;
    }

    public int getPower() {
        return power;
    }

    public int getDefense() {
        return defense;
    }

    public int getResistance() {
        return resistance;
    }

    @Override
    public String toString() {
        return name;
    }
}
