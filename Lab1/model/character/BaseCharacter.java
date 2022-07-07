package model.character;

import model.item.*;

public class BaseCharacter {
    protected String name, imgpath, classname;
    protected DamageType type;
    protected Integer fullHp, basePow, baseDef, baseRes;
    protected Integer hp, power, defense, resistance;
    protected GenericEquipment weapon, armor;
    protected int[] allow_armor, allow_weapon;

    public String getName() {
        return name;
    }

    public String getImagepath() {
        return imgpath;
    }

    public Integer getHp() {
        return hp;
    }

    public Integer getFullHp() {
        return fullHp;
    }

    public Integer getPower() {
        return power;
    }

    public Integer getDefense() {
        return defense;
    }

    public Integer getResistance() {
        return resistance;
    }

    public String getClassname() {
        return classname;
    }

    public void equipWeapon(GenericEquipment weapon) {
        this.weapon = weapon;
        calculateStats();
    }

    public void equipArmor(GenericEquipment armor) {
        this.armor = armor;
        calculateStats();
    }

    public void unequipWeapon() {
        this.weapon = null;
        calculateStats();
    }

    public void unequipArmor() {
        this.armor = null;
        calculateStats();
    }

    private void calculateStats() {
        this.power = basePow + (weapon != null? weapon.getPower(): 0) + (armor != null? armor.getPower(): 0);
        this.defense = baseDef + (weapon != null? weapon.getDefense(): 0) + (armor != null? armor.getDefense(): 0);
        this.resistance = baseRes + (weapon != null? weapon.getResistance(): 0) + (armor != null? armor.getResistance(): 0);
    }

    public int[] getAllow_armor() {
        return allow_armor;
    }

    public int[] getAllow_weapon() {
        return allow_weapon;
    }

    @Override
    public String toString() {
        return name;
    }

    public DamageType getType() {
        return type;
    }
}
