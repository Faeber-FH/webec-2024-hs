package ch.fhnw.webec.contactlist.service;

import ch.fhnw.webec.contactlist.SampleContactsAdder;
import ch.fhnw.webec.contactlist.db.ContactRepository;
import ch.fhnw.webec.contactlist.model.ContactListEntry;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.opentest4j.AssertionFailedError;

import java.io.IOException;
import java.util.List;

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;
import static java.util.Collections.emptyList;
import static java.util.stream.IntStream.rangeClosed;
import static org.junit.jupiter.api.Assertions.*;

class ContactServiceTest {

	ContactService service;

	ContactServiceTest() throws IOException {
		var mapper = new ObjectMapper().configure(FAIL_ON_UNKNOWN_PROPERTIES, false);
		var sampleContacts = SampleContactsAdder.loadSampleContacts(mapper);

		var repositoryStub = Mockito.mock(ContactRepository.class);
		Mockito.when(repositoryStub.findAll()).thenReturn(sampleContacts);
		Mockito.when(repositoryStub.findById(1)).thenReturn(sampleContacts.stream().filter(x -> x.getId() == 1).findFirst());

		service = new ContactService(repositoryStub);
	}

	@Test
	void contactListIds() {
		var contactList = service.getContactList();
		assertNotNull(contactList);
		var ids = contactList.stream()
				.mapToInt(ContactListEntry::getId)
				.toArray();
		assertArrayEquals(rangeClosed(1, 30).toArray(), ids);
	}

	@Test
	void searchContactList() {
		var contactList = service.searchContactList("ee");
		assertNotNull(contactList);
		var ids = contactList.stream()
				.mapToInt(ContactListEntry::getId)
				.toArray();
		assertArrayEquals(new int[]{2, 3, 15, 16, 25, 26}, ids);
	}

	@Test
	void contactStatistics() {
		var statistics = service.getContactStatistics();
		assertNotNull(statistics);
		assertEquals(30, statistics.contactCount());
		assertEquals(49, statistics.phoneNumberCount());
		assertEquals(45, statistics.emailAddressCount());
	}

	@Test
	void contactListName() {
		var contactList = service.getContactList();
		assertNotNull(contactList);
		assertFalse(contactList.isEmpty());
		assertEquals("Mabel Guppy", contactList.getFirst().getName());
	}

	@Test
	void findContact() {
		var contact = service.findContact(1).orElseThrow(AssertionFailedError::new);
		assertEquals(1, contact.getId());
		assertEquals("Mabel", contact.getFirstName());
		assertEquals("Guppy", contact.getLastName());
		assertEquals(List.of("405-580-6403"), contact.getPhone());
		assertEquals(emptyList(), contact.getEmail());
		assertEquals("Librarian", contact.getJobTitle());
		assertEquals("Photolist", contact.getCompany());
	}

	@Test
	void findContactAbsent() {
		var contact = service.findContact(98213);
		assertTrue(contact.isEmpty());
	}
}
