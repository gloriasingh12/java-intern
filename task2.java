import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * PROJECT: REST API Client
 * TASK 2: Consume Public REST API and Parse JSON
 * DELIVERABLE: Java program using HttpClient for Weather Data
 */
public class RestApiClient {

    public static void main(String[] args) {
        // Public API URL (Noida Weather - Latitude: 28.53, Longitude: 77.39)
        String url = "https://api.open-meteo.com/v1/forecast?latitude=28.53&longitude=77.39&current_weather=true";

        System.out.println("--- Fetching Live Data for Noida ---");
        
        try {
            // 1. Create HttpClient
            HttpClient client = HttpClient.newHttpClient();

            // 2. Build HttpRequest
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            // 3. Send Request and get HttpResponse
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // 4. Handle Response
            if (response.statusCode() == 200) {
                System.out.println("Status Code: 200 (Success) ✅");
                parseAndDisplay(response.body());
            } else {
                System.out.println("Error: Could not fetch data. Status Code: " + response.statusCode());
            }

        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }

    // A simple parser logic to show structured data
    public static void parseAndDisplay(String jsonBody) {
        System.out.println("\n--- Raw JSON Response ---");
        System.out.println(jsonBody);

        System.out.println("\n--- Structured Weather Data ---");
        // Simple string manipulation to extract values without external libraries like Jackson/Gson
        // for ease of use in a single script
        if (jsonBody.contains("temperature")) {
            String temp = jsonBody.split("\"temperature\":")[1].split(",")[0];
            String wind = jsonBody.split("\"windspeed\":")[1].split(",")[0];
            
            System.out.println("📍 Location: Noida, UP");
            System.out.println("🌡️ Current Temperature: " + temp + "°C");
            System.out.println("💨 Wind Speed: " + wind + " km/h");
            System.out.println("-------------------------------");
        }
    }
}
