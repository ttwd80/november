package com.github.ttwd80.november.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AdminController {

	@RequestMapping("/admin/index")
	public ModelAndView index() {
		return new ModelAndView("/admin/index");
	}
}
