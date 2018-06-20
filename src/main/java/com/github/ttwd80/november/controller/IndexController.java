package com.github.ttwd80.november.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.github.ttwd80.november.model.service.DatabaseService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class IndexController {

	private final DatabaseService databaseService;

	@Autowired
	public IndexController(DatabaseService databaseService) {
		super();
		this.databaseService = databaseService;
	}

	@RequestMapping("/")
	public ModelAndView index() {
		if (databaseService.isAnyUserRegistered()) {
			log.info("At least one user exists");
			return new ModelAndView("redirect:/content");
		} else {
			log.info("No user exists");
			return new ModelAndView("redirect:/setup/setup");
		}
	}

}
