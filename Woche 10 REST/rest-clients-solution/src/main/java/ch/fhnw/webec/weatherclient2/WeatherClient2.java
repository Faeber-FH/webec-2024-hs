package ch.fhnw.webec.weatherclient2;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

public class WeatherClient2 {

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
				"?latitude=" + coords.lat() +
				"&longitude=" + coords.lon() +
				"&current=temperature_2m,precipitation_probability");
			var request = HttpRequest.newBuilder()
				.uri(uri)
				.header("Accept", "application/json")
				.build();
			System.out.println(request + "\n");

			var response = client.send(request, HttpResponse.BodyHandlers.ofString());
			System.out.println(response.statusCode());
			System.out.println(response.body() + "\n");

			if (response.statusCode() == 200) {
				var mapper = new ObjectMapper();
				var weather = mapper.readValue(response.body(), Weather.class);
				System.out.println("Temperature: " +
					weather.current().temperature() + " " +
					weather.units().temperature());
				System.out.println("Precipitation probability: " +
					weather.current().precipitation() + " " +
					weather.units().precipitation());
			}
		}
	}

	record Coordinate(double lat, double lon) {
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	record Weather(
		@JsonProperty("current_units") Units units,
		CurrentWeather current) {
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	record Units(
		@JsonProperty("temperature_2m") String temperature,
		@JsonProperty("precipitation_probability") String precipitation) {
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	record CurrentWeather(
		@JsonProperty("temperature_2m") double temperature,
		@JsonProperty("precipitation_probability") double precipitation) {
	}
}
