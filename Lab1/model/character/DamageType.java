package model.character;

import java.util.HashMap;

public enum DamageType {
    none(0),
    physical(1),
    magical(2),
    neutral(3);

    private int value;
    private static HashMap<Integer, DamageType> map = new HashMap<>();

    private DamageType(int value) {
        this.value = value;
    }

    static {
        for (DamageType damageType : DamageType.values()) {
            map.put(damageType.value, damageType);
        }
    }

    public static DamageType valueOf(int damageTye) {
        return (DamageType) map.get(damageTye);
    }

    public int getValue() {
        return value;
    }
}