package ch.fhnw.webec.contactlist.controller;

import ch.fhnw.webec.contactlist.service.ContactService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AboutController {
    private final ContactService service;

    public AboutController(ContactService service) {
        this.service = service;
    }

    @GetMapping("about")
    public String contacts(Model model) {
        model.addAttribute("stats", service.getStatistics());
        return "about";
    }
}
