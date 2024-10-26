package ch.fhnw.webec.contactlist.controller;

import ch.fhnw.webec.contactlist.service.ContactService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Controller
@Validated
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
            if (query.length() < minQueryLength) throw new RuntimeException("query too short");
            model.addAttribute("query", query);
            model.addAttribute("contactList", service.searchContactList(query));
        } else {
            model.addAttribute("contactList", service.getContactList());
        }
        model.addAttribute("minQueryLength", minQueryLength);
        model.addAttribute("isInEdit", false);
        return "contacts";
    }

    @GetMapping("contacts/{id}")
    public String showContact(@PathVariable int id, String query, Model model) {
        var contact = service.findContact(id).orElseThrow(ContactNotFound::new);
        model.addAttribute("contact", contact);
        contacts(query, model);
        model.addAttribute("isInEdit", false);
        return "contacts";
    }

    @GetMapping("contacts/{id}/edit")
    public String editContact(@PathVariable int id, String query, Model model) {
        var contact = service.findContact(id).orElseThrow(ContactNotFound::new);
        model.addAttribute("contact", contact);
        contacts(query, model);
        model.addAttribute("isInEdit", true);
        return "contacts";
    }

    @ExceptionHandler(ContactNotFound.class)
    @ResponseStatus(NOT_FOUND)
    public String notFound(Model model) {
        model.addAttribute("contactList", service.getContactList());
        model.addAttribute("error", "Contact not found");
        return "contacts";
    }

    private static class ContactNotFound extends RuntimeException {}
}
