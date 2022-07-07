package model.item;

import java.util.HashMap;

public enum EquipType {
    none(0),
    weapon(1),
    armor(2);

    private int value;
    private static HashMap<Integer, EquipType> map = new HashMap<>();

    private EquipType(int value) {
        this.value = value;
    }

    static {
        for (EquipType equipType : EquipType.values()) {
            map.put(equipType.value, equipType);
        }
    }

    public static EquipType valueOf(int equipType) {
        return (EquipType) map.get(equipType);
    }

    public int getValue() {
        return value;
    }
}