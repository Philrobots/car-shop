package ca.ulaval.glo4003.evulution.infrastructure.mappers;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class JsonFileMapper {
    private static List<String> parseJsonFileFromName(String name, String key) {
        JsonParser parser = new JsonParser();
        List<String> nameList = new ArrayList<>();
        try {
            JsonArray obj = (JsonArray) parser.parse(new FileReader(name));
            for (JsonElement x : obj) {
                JsonObject jsonObject = x.getAsJsonObject();
                nameList.add(String.valueOf(jsonObject.get(key)).replaceAll("^\"|\"$", ""));
            }
            return nameList;
        } catch (Exception e) {
            throw new RuntimeException("Launch failed because of problem while parsing json files");
        }
    }

    public static List<String> parseBatteries() {
        return parseJsonFileFromName("batteries.json", "name");
    }

    public static List<String> parseCarModels() {
        return parseJsonFileFromName("models.json", "name");
    }

    public static List<String> parseDeliveryLocations() {
        return parseJsonFileFromName("campus-delivery-location.json", "location");
    }
}
