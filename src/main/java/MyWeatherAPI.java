import weather.Period;
import weather.WeatherAPI;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MyWeatherAPI extends WeatherAPI {

    // Method to fetch forecast using latitude & longitude
    public static ArrayList<Period> getForecastByLatLon(String lat, String lon) {
        GridInfo gridInfo = convertLatLonToGrid(lat, lon);
        if (gridInfo == null) {
            System.err.println("Failed to get grid info from lat/lon");
            return null;
        }

        System.out.println("Location: " + gridInfo.city + ", " + gridInfo.state);
        System.out.println("Region: " + gridInfo.region + ", GridX: " + gridInfo.gridX + ", GridY: " + gridInfo.gridY);

        // Call WeatherAPI.getForecast with extracted region, gridX, and gridY
        return WeatherAPI.getForecast(gridInfo.region, gridInfo.gridX, gridInfo.gridY);
    }

    // Converts latitude & longitude to region, gridX, gridY, city, and state
    private static GridInfo convertLatLonToGrid(String lat, String lon) {
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
            JsonNode properties = rootNode.path("properties");

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
