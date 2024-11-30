package ch.fhnw.webec.weatherclient;

import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

public class WeatherClient {

	private static final String BASE_URI = "https://api.open-meteo.com/v1/";

	private static final Map<String, Coordinate> CITY_COORDINATES = Map.of(
		"Basel", new Coordinate(47.56013141204476, 7.58970186827881),
		"Windisch", new Coordinate(47.48101988584374, 8.21202947270268),
		"Locarno", new Coordinate(46.16960630520142, 8.79593817784890),
		"St. Moritz", new Coordinate(46.49237775748978, 9.83773353873480));

	public static void main(String[] args) throws IOException, InterruptedException {

		try (var client = HttpClient.newHttpClient()) {
			var coords = CITY_COORDINATES.get("Windisch");
			var uri = URI.create(BASE_URI + "forecast" +
				"?latitude=" + coords.lat +
				"&longitude=" + coords.lon +
				"&current=temperature_2m,precipitation_probability,relative_humidity_2m,wind_speed_10m");
			var request = HttpRequest.newBuilder()
				.uri(uri)
				.header("Accept", "application/json")
				.build();
			System.out.println(request + "\n");

			var response = client.send(request, HttpResponse.BodyHandlers.ofString());
			System.out.println(response.statusCode());
			System.out.println(response.body() + "\n");

			if (response.statusCode() == 200) {
				var json = new JSONObject(response.body());
				var units = json.getJSONObject("current_units");
				var current = json.getJSONObject("current");
				System.out.println("Temperature: " +
					current.getDouble("temperature_2m") + " " +
					units.getString("temperature_2m"));
				System.out.println("Precipitation probability: " +
					current.getDouble("precipitation_probability") + " " +
					units.getString("precipitation_probability"));
				System.out.println("Relative humidity at 2 meters: " +
					current.getDouble("relative_humidity_2m") + " " +
					units.getString("relative_humidity_2m"));
				System.out.println("Wind speed at 10 meters: " +
					current.getDouble("wind_speed_10m") + " " +
					units.getString("wind_speed_10m"));
			}
		}
	}

	record Coordinate(double lat, double lon) {
	}
}
