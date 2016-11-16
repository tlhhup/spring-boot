package com.tlh.springmvc.action;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HelloAction {

	@RequestMapping("/index")
	public String index(Model model){
		model.addAttribute("user", "hello");
		return "index";
	}
	
}
