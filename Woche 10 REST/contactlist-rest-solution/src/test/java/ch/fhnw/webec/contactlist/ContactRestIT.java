package ch.fhnw.webec.contactlist;

import ch.fhnw.webec.contactlist.model.Contact;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureTestDatabase
public class ContactRestIT {

	@LocalServerPort
	int port;

	RestClient client;

	@BeforeEach
	public void setup() {
		 client = RestClient.create("http://localhost:" + port + "/api");
	}

	@Test
	public void getAllContacts() {
		var contacts = client.get().uri("/contacts").retrieve().body(Contact[].class);

		assertEquals(30, contacts.length);
		var first = contacts[0];
		assertEquals("Mabel", first.getFirstName());
		assertEquals("Guppy", first.getLastName());
		assertEquals("Librarian", first.getJobTitle());
		assertEquals("Photolist", first.getCompany());
	}

	@Test
	public void getContact() {
		var contact = client.get().uri("/contacts/1").retrieve().body(Contact.class);

		assertEquals("Mabel", contact.getFirstName());
		assertEquals("Guppy", contact.getLastName());
		assertEquals("Librarian", contact.getJobTitle());
		assertEquals("Photolist", contact.getCompany());
	}

	@Test
	@DirtiesContext
	public void deleteContact() {
		client.delete().uri("/contacts/1").retrieve();

		Exception exception = assertThrows(HttpClientErrorException.class, () -> {
			client.get().uri("/contacts/1").retrieve().body(Contact.class);
		});

		assertEquals("404 : [no body]", exception.getMessage());
	}

	@Test
	@DirtiesContext
	public void updateContact() {
		// before
		var contact = client.get().uri("/contacts/1").retrieve().body(Contact.class);
		assertEquals("Mabel", contact.getFirstName());
		assertEquals("Guppy", contact.getLastName());

		// update
		contact.setFirstName("John");
		contact.setLastName("Doe");
		client.put().uri("/contacts/1").body(contact).retrieve().body(Contact.class);

		// after
		var updated = client.get().uri("/contacts/1").retrieve().body(Contact.class);
		assertEquals("John", updated.getFirstName());
		assertEquals("Doe", updated.getLastName());
	}

	@Test
	@DirtiesContext
	public void addContact() {
		// before
		var contactsBefore = client.get().uri("/contacts").retrieve().body(Contact[].class);
		assertEquals(30, contactsBefore.length);

		// add
		var newContact = new Contact();
		newContact.setFirstName("Jane");
		newContact.setLastName("Doe");
		var response = client.post().uri("/contacts").body(newContact).retrieve().toBodilessEntity();
		var location = response.getHeaders().getLocation();

		// after
		var added = client.get().uri("/" + location).retrieve().body(Contact.class);
		assertEquals("Jane", added.getFirstName());
		assertEquals("Doe", added.getLastName());

		var contactsAfter = client.get().uri("/contacts").retrieve().body(Contact[].class);
		assertEquals(31, contactsAfter.length);
	}

}
