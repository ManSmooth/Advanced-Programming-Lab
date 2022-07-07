package controller;

import model.item.*;
import model.character.DamageType;
import org.json.JSONObject;
import org.json.JSONArray;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

//Imports are omitted
public class GenItemList {
    private static ArrayList<GenericEquipment> items = new ArrayList<>();
    static {
        try {
            String text = new String(Files.readAllBytes(Paths.get("Lab1/assets/data/item_data.json")),
                    StandardCharsets.UTF_8).replaceAll("[\\n\\t]", "");
            JSONArray jsonarray = new JSONArray(text);
            for (int i = 0; i < jsonarray.length(); i++) {
                JSONObject temp = jsonarray.getJSONObject(i);
                items.add(new GenericEquipment(temp.getString("name"), EquipType.valueOf(temp.getInt("type")),
                        temp.getInt("bPow"), temp.getInt("bDef"), temp.getInt("bRes"),
                        DamageType.valueOf(temp.getInt("dtype")), temp.getString("imgpath")));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<BaseEquipment> setUpItemList() {
        ArrayList<BaseEquipment> itemLists = new ArrayList<BaseEquipment>();
        
        itemLists.add(items.get(1));
        itemLists.add(items.get(2));
        itemLists.add(items.get(3));
        itemLists.add(items.get(4));
        itemLists.add(items.get(5));
        itemLists.add(items.get(6));
        return itemLists;
    }
}