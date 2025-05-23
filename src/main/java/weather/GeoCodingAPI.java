package weather;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class GeoCodingAPI {
    private static final String API_KEY = "AIzaSyDJ6frOqVDG8RQ4IwdRL-02iuXxMv-XT3E";

    public static CoordInfo getCoordinates(String address) {
        address = address.replace(" ", "%20");
        String url = "https://maps.googleapis.com/maps/api/geocode/json?address=" + address + "&key=" + API_KEY;
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.body() == null) {
                System.err.println("Empty response from API");
                return null;
            }

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(response.body());

            // Check if the API returned a valid status
            if (!rootNode.has("status") || !"OK".equals(rootNode.get("status").asText())) {
                System.err.println("Invalid API response status: " + rootNode.get("status").asText());
                return null;
            }

            // Extract the first result (if available)
            JsonNode results = rootNode.path("results");
            if (results.isEmpty() || !results.isArray()) {
                System.err.println("No results found");
                return null;
            }

            JsonNode firstResult = results.get(0);
            JsonNode location = firstResult.path("geometry").path("location");

            if (location.isMissingNode()) {
                System.err.println("Missing location data");
                return null;
            }

            String lat = location.path("lat").asText();
            String lon = location.path("lng").asText();

            return new CoordInfo(lat, lon);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
