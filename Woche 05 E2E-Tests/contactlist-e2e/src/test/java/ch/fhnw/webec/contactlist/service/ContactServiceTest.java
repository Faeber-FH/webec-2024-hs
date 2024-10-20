package ch.fhnw.webec.contactlist.service;

import ch.fhnw.webec.contactlist.SampleContactsAdder;
import ch.fhnw.webec.contactlist.model.ContactListEntry;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
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
        service = new ContactService();
        new SampleContactsAdder(mapper, service).addSampleContacts();
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


    @Test
    void searchContactListByFirstName() {
        List<ContactListEntry> results = service.searchContactList("Mabel");
        assertEquals(1, results.size());
        assertEquals("Mabel Guppy", results.get(0).getName());
    }

    @Test
    void searchContactListByLastName() {
        List<ContactListEntry> results = service.searchContactList("Clouter");
        assertEquals(1, results.size());
        assertEquals("Lauree Clouter", results.get(0).getName());
    }

    @Test
    void searchContactListByEmail() {
        List<ContactListEntry> results = service.searchContactList("alyman0@economist.com");
        assertEquals(1, results.size());
        assertEquals("Lauree Clouter", results.get(0).getName());
    }

    @Test
    void searchContactListByPartialEmail() {
        List<ContactListEntry> results = service.searchContactList("economist.com");
        assertEquals(1, results.size());
        assertEquals("Lauree Clouter", results.get(0).getName());
    }

    @Test
    void searchContactListByPhoneNumber() {
        List<ContactListEntry> results = service.searchContactList("405-580-6403");
        assertEquals(1, results.size());
        assertEquals("Mabel Guppy", results.get(0).getName());
    }

    @Test
    void searchContactListByPartialPhoneNumber() {
        List<ContactListEntry> results = service.searchContactList("580");
        assertEquals(1, results.size());
        assertEquals("Mabel Guppy", results.get(0).getName());
    }

    @Test
    void searchContactListNoResults() {
        List<ContactListEntry> results = service.searchContactList("NonExistent");
        assertTrue(results.isEmpty());
    }

}
