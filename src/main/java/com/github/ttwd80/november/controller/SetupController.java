package com.github.ttwd80.november.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.github.ttwd80.november.model.service.DatabaseService;

@Controller
public class SetupController {

	private DatabaseService databaseService;

	@Autowired
	public SetupController(DatabaseService databaseService) {
		this.databaseService = databaseService;
	}

	@RequestMapping("/setup/setup")
	public ModelAndView setup() {
		if (databaseService.isAnyUserRegistered()) {
			return new ModelAndView("redirect:/");
		} else {
			return new ModelAndView("/setup/setup");
		}
	}

	@RequestMapping("/setup/init")
	public ModelAndView init(@RequestParam("password") String initialAdminPassword) {
		databaseService.initDatabase(initialAdminPassword);
		return new ModelAndView("redirect:/");
	}
}
