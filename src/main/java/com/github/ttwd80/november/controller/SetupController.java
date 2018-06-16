package com.github.ttwd80.november.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.github.ttwd80.november.model.service.DatabaseService;

@Controller
public class SetupController {

	private final DatabaseService databaseService;

	@Autowired
	public SetupController(DatabaseService databaseService) {
		super();
		this.databaseService = databaseService;
	}

	@RequestMapping("/setup")
	public ModelAndView setup() {
		if (databaseService.isSetuUp()) {
			return new ModelAndView("redirect:/index");
		} else {
			return new ModelAndView("setup");
		}
	}
}
