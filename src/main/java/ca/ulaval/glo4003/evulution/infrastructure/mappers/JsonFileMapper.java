package ca.ulaval.glo4003.evulution.infrastructure.mappers;

import ca.ulaval.glo4003.evulution.domain.car.BatteryInformationDto;
import ca.ulaval.glo4003.evulution.domain.car.ModelInformationDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class JsonFileMapper {

    private static final JsonParser parser = new JsonParser();
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static List<BatteryInformationDto> parseBatteries() {
        try {
            JsonArray obj = (JsonArray) parser.parse(new FileReader("batteries.json"));
            return objectMapper.readValue(obj.toString(), new TypeReference<>() {
            });
        } catch (Exception e) {
            throw new RuntimeException("Launch failed because of problem while parsing json files");
        }
    }

    public static List<ModelInformationDto> parseModels() {
        try {
            JsonArray obj = (JsonArray) parser.parse(new FileReader("models.json"));
            return objectMapper.readValue(obj.toString(), new TypeReference<>() {
            });
        } catch (Exception e) {
            throw new RuntimeException("Launch failed because of problem while parsing json files");
        }
    }

    public static List<String> parseDeliveryLocations() {
        List<String> nameList = new ArrayList<>();
        try {
            JsonArray obj = (JsonArray) parser.parse(new FileReader("campus-delivery-location.json"));
            for (JsonElement x : obj) {
                JsonObject jsonObject = x.getAsJsonObject();
                nameList.add(String.valueOf(jsonObject.get("location")).replaceAll("^\"|\"$", ""));
            }
            return nameList;
        } catch (Exception e) {
            throw new RuntimeException("Launch failed because of problem while parsing json files");
        }
    }
}
