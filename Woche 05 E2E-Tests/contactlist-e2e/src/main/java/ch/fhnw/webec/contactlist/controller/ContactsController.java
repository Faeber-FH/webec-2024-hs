package ch.fhnw.webec.contactlist.controller;

import ch.fhnw.webec.contactlist.service.ContactService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Controller
public class ContactsController {

    private final ContactService service;

    @Value("${contactlist.min-query-length}")
    private int minQueryLength;

    public ContactsController(ContactService service) {
        this.service = service;
    }

    @GetMapping("contacts")
    public String contacts(String search, Model model) {
        if (search != null && !search.isEmpty()) {
            model.addAttribute("contactList", service.searchContactList(search));
        } else {
            model.addAttribute("contactList", service.getContactList());
        }
        model.addAttribute("minQueryLength", minQueryLength);
        return "contacts";
    }

    @GetMapping("contacts/{id}")
    public String showContact(@PathVariable int id,String search, Model model) {
        var contact = service.findContact(id).orElseThrow(ContactNotFound::new);
        if (search != null && !search.isEmpty() && search.length()>=minQueryLength) {
            model.addAttribute("contactList", service.searchContactList(search));
        } else {
            model.addAttribute("contactList", service.getContactList());
        }        model.addAttribute("contact", contact);
        model.addAttribute("minQueryLength", minQueryLength);
        return "contacts";
    }

    @ExceptionHandler(ContactNotFound.class)
    @ResponseStatus(NOT_FOUND)
    public String notFound(Model model) {
        model.addAttribute("contactList", service.getContactList());
        model.addAttribute("error", "Contact not found");
        return "contacts";
    }

    private static class ContactNotFound extends RuntimeException {
    }
}
