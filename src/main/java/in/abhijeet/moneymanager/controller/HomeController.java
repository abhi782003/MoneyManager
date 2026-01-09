package in.abhijeet.moneymanager.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/status","/health"})
public class HomeController {
	
	//public endpoint to check applicatio is running when we deploy
	@GetMapping
	public String healthCheck() {
		return "Application is running";
	}

}
