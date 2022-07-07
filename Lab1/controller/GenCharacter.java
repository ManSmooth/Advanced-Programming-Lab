package controller;

import model.character.*;
import java.util.Random;
import org.json.JSONObject;
import org.json.JSONArray;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class GenCharacter {
    private static ArrayList<JSONObject> classes = new ArrayList<>();
    private static JSONArray names;
    static {
        try {
            String text = new String(Files.readAllBytes(Paths.get("assets/data/class_data.json")), StandardCharsets.UTF_8).replaceAll("[\\n\\t]", "");
            JSONArray jsonarray = new JSONArray(text);
            for (int i = 0; i < jsonarray.length(); i++) {
                classes.add(jsonarray.getJSONObject(i));
            }
            text = new String(Files.readAllBytes(Paths.get("assets/data/names.json")), StandardCharsets.UTF_8).replaceAll("[\\n\\t]", "");
            names = new JSONArray(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static BaseCharacter setUpCharacter() {
        Random rand = new Random();
        return setUpCharacter(rand.nextInt(1, classes.size()));
    }

    public static BaseCharacter setUpCharacter(int type) {
        BaseCharacter character;
        Random rand = new Random();
        String name = names.getString(rand.nextInt(names.length()));
        JSONObject temp_class = classes.get(type);
        int baseDef = rand.nextInt(26) + temp_class.getInt("bDef");
        int baseRes = rand.nextInt(26) + temp_class.getInt("bRes");
        character = new GenericCharacter(name, temp_class.getString("name"), temp_class.getString("imgpath"),
                temp_class.getInt("dtype"), temp_class.getInt("MHP"), temp_class.getInt("bPow"), baseDef,
                baseRes, temp_class.getJSONArray("allow_weapon"), temp_class.getJSONArray("allow_armor"));
        return character;
    }
}
