package ch.fhnw.webec.contactlist.service;

import ch.fhnw.webec.contactlist.model.Contact;
import ch.fhnw.webec.contactlist.model.ContactListEntry;
import ch.fhnw.webec.contactlist.model.ContactStatistics;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Comparator.comparing;

@Service
public class ContactService {

    private final List<Contact> contacts = new ArrayList<>();

    public List<ContactListEntry> getContactList() {
        return contacts.stream()
                .sorted(comparing(Contact::getId))
                .map(c -> new ContactListEntry(c.getId(), c.getFirstName() + " " + c.getLastName()))
                .toList();
    }

    public List<ContactListEntry> searchContactList(String query) {
        return contacts.stream()
                .filter(c -> c.containsString(query))
                .sorted(comparing(Contact::getId))
                .map(c -> new ContactListEntry(c.getId(), c.getFirstName() + " " + c.getLastName()))
                .toList();
    }

    public ContactStatistics getContactStatistics() {
        return new ContactStatistics(
          contacts.size(),
          contacts.stream().mapToInt(x -> x.getPhone().size()).sum(),
          contacts.stream().mapToInt(x -> x.getEmail().size()).sum()
        );
    }

    public Optional<Contact> findContact(int id) {
        return contacts.stream()
                .filter(c -> c.getId() == id)
                .findFirst();
    }

    public Contact add(String firstName, String lastName,
                       String jobTitle, String company) {
        var contact = new Contact();
        contact.setFirstName(firstName);
        contact.setLastName(lastName);
        contact.setJobTitle(jobTitle);
        contact.setCompany(company);
        return add(contact);
    }

    public Contact add(Contact contact) {
        contacts.add(contact);
        return contact; // important for later, when using Repository
    }
}
