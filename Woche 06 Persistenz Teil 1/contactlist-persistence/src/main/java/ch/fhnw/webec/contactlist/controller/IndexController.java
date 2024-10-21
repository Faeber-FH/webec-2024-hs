package ch.fhnw.webec.contactlist.controller;

import ch.fhnw.webec.contactlist.model.Contact;
import ch.fhnw.webec.contactlist.service.ContactService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
    private final ContactService service;

    public IndexController(ContactService service) {
        this.service = service;
    }

    @GetMapping("/request")
    public String contacts(String firstname, String lastname, String jobtitle, String company, Model model) {
       if (firstname != null && !firstname.isBlank() && lastname != null && !lastname.isBlank() && jobtitle != null && !jobtitle.isBlank() && company != null && !company.isBlank()) {
           var newContact = new Contact();
           newContact.setFirstName(firstname);
           newContact.setLastName(lastname);
           newContact.setJobTitle(jobtitle);
           newContact.setCompany(company);
           service.add(newContact);
           return "redirect:/contacts";
        }else {
           return "redirect:/";
       }
    }
}
