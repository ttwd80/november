package com.github.ttwd80.november.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.servlet.ModelAndView;

import com.github.ttwd80.november.model.service.DatabaseService;

class IndexControllerTest {

	@Mock
	DatabaseService databaseService;

	IndexController sut;

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		sut = new IndexController(databaseService);
	}

	@Test
	void testRedirectToSetup() {
		when(databaseService.isAnyUserRegistered()).thenReturn(Boolean.FALSE);
		ModelAndView mav = sut.index();
		assertEquals("redirect:/setup/setup", mav.getViewName());
	}

	@Test
	void testUserHasBeenRegistered() {
		when(databaseService.isAnyUserRegistered()).thenReturn(Boolean.TRUE);
		ModelAndView mav = sut.index();
		assertEquals("redirect:/content", mav.getViewName());
	}
}
