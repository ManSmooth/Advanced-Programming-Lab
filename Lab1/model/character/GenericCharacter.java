package model.character;

import org.json.JSONArray;

public class GenericCharacter extends BaseCharacter {
    DamageType[] types = DamageType.values();
    public GenericCharacter(String name, String classname, String imgpath, int type_id, int mhp, int basePow, int baseDef,
            int baseRes, JSONArray allow_w, JSONArray allow_a) {
        this.name = name;
        this.classname = classname;
        this.type = types[type_id];
        this.imgpath = imgpath;
        this.fullHp = mhp;
        this.basePow = basePow;
        this.baseDef = baseDef;
        this.baseRes = baseRes;
        this.hp = this.fullHp;
        this.power = this.basePow;
        this.defense = baseDef;
        this.resistance = baseRes;
        this.allow_weapon = new int[allow_w.length()];
        for (int i = 0; i < allow_weapon.length; i++) {
            allow_weapon[i] = allow_w.getInt(i);
        }
        this.allow_armor = new int[allow_a.length()];
        for (int i = 0; i < allow_armor.length; i++) {
            allow_armor[i] = allow_a.getInt(i);
        }
    }
}