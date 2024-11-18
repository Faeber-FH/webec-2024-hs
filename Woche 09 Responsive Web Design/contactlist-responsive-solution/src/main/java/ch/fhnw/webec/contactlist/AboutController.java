package ch.fhnw.webec.contactlist;

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
	public String showAbout(Model model) {
		model.addAttribute("statistics", service.getContactStatistics());
		return "about";
	}

}
