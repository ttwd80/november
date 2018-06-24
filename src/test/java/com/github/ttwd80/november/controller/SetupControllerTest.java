package com.github.ttwd80.november.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.servlet.ModelAndView;

import com.github.ttwd80.november.model.service.DatabaseService;

class SetupControllerTest {

	@Mock
	DatabaseService databaseService;

	SetupController sut;

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		sut = new SetupController(databaseService);
	}

	@Test
	void testSetupAlreadySetUp() {
		when(databaseService.isAnyUserRegistered()).thenReturn(Boolean.TRUE);
		ModelAndView mav = sut.setup();
		assertEquals("redirect:/", mav.getViewName());
	}

	@Test
	void testSetupBlankDatabase() {
		when(databaseService.isAnyUserRegistered()).thenReturn(Boolean.FALSE);
		ModelAndView mav = sut.setup();
		assertEquals("/setup/setup", mav.getViewName());
	}

	@Test
	void testInit() {
		ModelAndView mav = sut.init("secretPassword");
		assertEquals("redirect:/", mav.getViewName());
	}

}
