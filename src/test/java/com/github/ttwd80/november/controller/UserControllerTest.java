package com.github.ttwd80.november.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

class UserControllerTest {

	UserController sut;

	@BeforeEach
	void setUp() throws Exception {
		sut = new UserController();
	}

	@Test
	void testIndex() {
		ModelAndView mav = sut.index();
		assertEquals("/user/index", mav.getViewName());
	}

}
