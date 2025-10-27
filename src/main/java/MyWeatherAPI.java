import com.fasterxml.jackson.core.JsonProcessingException;
import weather.GridInfo;
import weather.Period;
import weather.Root;
import weather.WeatherAPI;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MyWeatherAPI extends WeatherAPI {

    public static ArrayList<Period> getForecast(String region, int gridx, int gridy) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.weather.gov/gridpoints/"+region+"/"+String.valueOf(gridx)+","+String.valueOf(gridy)+"/forecast"))
                //.method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = null;
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        Root r = getObject(response.body());
        if(r == null){
            System.err.println("Failed to parse JSon");
            return null;
        }
        return r.properties.periods;
    }
    public static Root getObject(String json){
        ObjectMapper om = new ObjectMapper();
        Root toRet = null;
        try {
            toRet = om.readValue(json, Root.class);
            if (toRet == null || toRet.properties == null) {
                return null;
            }
            ArrayList<Period> p = toRet.properties.periods;

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return toRet;
    }


    // Converts latitude & longitude to region, gridX, gridY, city, and state
    public static GridInfo convertLatLonToGrid(String lat, String lon) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.weather.gov/points/" + lat + "," + lon))
                .build();

        HttpResponse<String> response = null;
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        if (response == null || response.body() == null) {
            System.err.println("Failed to fetch data from API");
            return null;
        }

        // Parse JSON response to extract grid info, city, and state
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode rootNode = objectMapper.readTree(response.body());
            if (rootNode.has("status") && rootNode.get("status").asInt() != 200) {
                return null;
            }
            JsonNode properties = rootNode.path("properties");
            if (properties.isMissingNode()) {
                return null;
            }
            String gridId = properties.path("gridId").asText();
            int gridX = properties.path("gridX").asInt();
            int gridY = properties.path("gridY").asInt();
            String city = properties.path("relativeLocation").path("properties").path("city").asText();
            String state = properties.path("relativeLocation").path("properties").path("state").asText();

            return new GridInfo(gridId, gridX, gridY, city, state);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
