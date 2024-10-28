package ch.fhnw.webec.contactlist.controller;

import ch.fhnw.webec.contactlist.model.Contact;
import ch.fhnw.webec.contactlist.service.ContactService;
import ch.fhnw.webec.contactlist.utils.StringList;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Controller
public class ContactsController {

	private final ContactService service;

	public ContactsController(ContactService service) {
		this.service = service;
	}

	@Value("${contactlist.min-query-length}")
	private int minQueryLength;

	@GetMapping("contacts")
	public String contacts(String query, Model model) {
		if (query != null && !query.isBlank()) {
			if (query.length() < minQueryLength) {
				model.addAttribute("error", "Query must be at least " + minQueryLength + " characters long");
				model.addAttribute("contactList", service.getContactList());
			} else {
				model.addAttribute("query", query);
				model.addAttribute("contactList", service.searchContactList(query));
			}
		} else {
			model.addAttribute("contactList", service.getContactList());
		}
		model.addAttribute("minQueryLength", minQueryLength);
		return "contacts";
	}

	@GetMapping("contacts/add")
	public String addContact(String query, Model model) {
		var contact = new Contact();
		contact.setEmail(List.of("", "", ""));
		contact.setPhone(List.of("", "", ""));

		model.addAttribute("contact", contact);
		model.addAttribute("edit", 1);
		contacts(query, model);
		return "contacts";
	}

	@GetMapping("contacts/{id}")
	public String showContact(@PathVariable int id, String query, Integer edit, Model model) {
		var contact = service.findContact(id).orElseThrow(ContactNotFound::new);

		// add an empty phone number and e-mail address field, so new values can be added
		contact.getEmail().add("");
		contact.getPhone().add("");

		model.addAttribute("contact", contact);
		model.addAttribute("edit", edit);
		contacts(query, model);
		return "contacts";
	}

	@PostMapping("contacts/{id}/delete")
	public String deleteContact(@PathVariable int id, String query, Model model) {
		service.delete(id);
		contacts(query, model);
		return "contacts";
	}

	@PostMapping("contacts/{id}")
	public String updateContact(@PathVariable int id, @ModelAttribute Contact contact, String query) {

		if (id != 0) contact.setId(id);

		// we offer too many fields to enter no values in these lists, so we have to remove the empty ones before saving
		contact.setPhone(StringList.removeBlankEntries(contact.getPhone()));
		contact.setEmail(StringList.removeBlankEntries(contact.getEmail()));

		service.update(contact);

		return "redirect:/contacts/" + contact.getId() + (query != null && !query.isBlank() ? "?query=" + query : "");
	}

	@ExceptionHandler(ContactNotFound.class)
	@ResponseStatus(NOT_FOUND)
	public String notFound(Model model) {
		model.addAttribute("error", "Contact not found");
		contacts(null, model);
		return "contacts";
	}

	private static class ContactNotFound extends RuntimeException {}
}
