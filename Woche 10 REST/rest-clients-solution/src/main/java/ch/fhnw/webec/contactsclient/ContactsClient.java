package ch.fhnw.webec.contactsclient;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;

public class ContactsClient {

	public static void main(String[] args) throws IOException, InterruptedException {
		try (var client = HttpClient.newHttpClient()) {
			var request = HttpRequest.newBuilder()
				.uri(URI.create("http://localhost:8080/api/contacts"))
				.build();
			var response = client.send(request, BodyHandlers.ofString());
			var mapper = new ObjectMapper();
			var contacts = mapper.readValue(response.body(), Contact[].class);

			for (var contact : contacts) {
				System.out.println(contact.getFirstName() +
					" " + contact.getLastName() +
					" (" + contact.getId() + ")");
			}
		}
	}
}
