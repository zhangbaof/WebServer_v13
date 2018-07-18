package com.zbf.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zbf.example.bean.Person;

@Controller
@SpringBootApplication
public class ThymeleafProjectApplication {

	
	@RequestMapping("/")
	public String testThymeleaf(Model model){
		
		Person p1 = new Person("mxy",21);
		model.addAttribute("singlePerson", p1);
		return "index";
		
		
	}
	
	
	
	public static void main(String[] args) {
		SpringApplication.run(ThymeleafProjectApplication.class, args);
	}
}
