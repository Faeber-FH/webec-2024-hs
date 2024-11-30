package ch.fhnw.webec.contactlist.controller;

import ch.fhnw.webec.contactlist.model.Contact;
import ch.fhnw.webec.contactlist.service.ContactService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("api/contacts")
public class ContactsRestController {

	private final ContactService contactService;

	public ContactsRestController(ContactService contactService) {
		this.contactService = contactService;
	}

	@GetMapping
	public List<Contact> getAll() {
		return contactService.getAll();
	}

	@GetMapping("{id}")
	public ResponseEntity<Contact> get(@PathVariable int id) {
		return ResponseEntity.of(contactService.findContact(id)); // 404 is returned when entity not found
	}

	@PutMapping("{id}")
	public ResponseEntity update(@PathVariable int id, @RequestBody Contact contact) {
		if(contactService.findContact(id).isPresent()) {
			contact.setId(id); // path ID wins
			contactService.update(contact);
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("{id}")
	public ResponseEntity delete(@PathVariable int id) {
		if(contactService.findContact(id).isPresent()) {
			contactService.delete(id);
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping()
	public ResponseEntity<String> add(@RequestBody Contact contact) {
		contactService.add(contact);
		return ResponseEntity.created(URI.create("contacts/" + contact.getId())).build();
	}

}
