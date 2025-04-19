import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

public class WeatherAPIClient {

    private static final String API_KEY = "your_api_key_here"; // Replace with your API Key
    private static final String BASE_URL = "https://api.openweathermap.org/data/2.5/weather";

    // Method to fetch weather data by city name
    public static String getWeatherData(String cityName) throws Exception {
        // Construct the API URL
        String urlString = BASE_URL + "?q=" + cityName + "&appid=" + API_KEY + "&units=metric"; // Using metric units
                                                                                                // for Celsius

        // Create a URL object
        URL url = new URL(urlString);

        // Open an HTTP connection
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        // Get the response code
        int responseCode = connection.getResponseCode();
        if (responseCode != 200) {
            throw new Exception("Error: Unable to fetch data from the API. Response code: " + responseCode);
        }

        // Read the response data
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();

        // Return the raw JSON response
        return response.toString();
    }

    // Method to parse and display weather information from JSON
    public static void displayWeather(String cityName) {
        try {
            // Fetch the weather data as a JSON string
            String jsonResponse = getWeatherData(cityName);

            // Convert the string response into a JSON object
            JSONObject weatherData = new JSONObject(jsonResponse);

            // Extract relevant information
            String city = weatherData.getString("name");
            JSONObject main = weatherData.getJSONObject("main");
            double temperature = main.getDouble("temp");
            double humidity = main.getDouble("humidity");
            JSONObject weather = weatherData.getJSONArray("weather").getJSONObject(0);
            String description = weather.getString("description");

            // Display the weather information
            System.out.println("Weather in " + city + ":");
            System.out.println("Temperature: " + temperature + "°C");
            System.out.println("Humidity: " + humidity + "%");
            System.out.println("Condition: " + description);
        } catch (Exception e) {
            System.out.println("Error fetching or parsing weather data: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        // Get the city name from the user
        System.out.print("Enter city name: ");
        String cityName = System.console().readLine();

        // Display the weather data
        displayWeather(cityName);
    }
}
