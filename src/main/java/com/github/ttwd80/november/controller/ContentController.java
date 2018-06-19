package com.github.ttwd80.november.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ContentController {

	@RequestMapping("/content")
	public ModelAndView content(HttpServletRequest request) {
		if (request.isUserInRole("ROLE_ADMIN")) {
			return new ModelAndView("redirect:/admin/index");
		} else if (request.isUserInRole("ROLE_USER")) {
			return new ModelAndView("redirect:/user/index");
		} else {
			return new ModelAndView("redirect:/");
		}
	}
}
