package com.github.ttwd80.november.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

class ContentControllerTest {

	ContentController sut;

	@BeforeEach
	void setUp() throws Exception {
		sut = new ContentController();
	}

	@ParameterizedTest
	@CsvSource({ "ROLE_ADMIN, 'redirect:/admin/index'", "'ROLE_ADMIN, ROLE_USER', redirect:/admin/index",
			"ROLE_USER, redirect:/user/index", "'', redirect:/" })
	void testViewBasedOnRoles(String items, String viewName) {
		List<String> roles = Arrays.asList(StringUtils.split(items, ','));
		MockHttpServletRequest mockRequest = new MockHttpServletRequest();
		roles.stream().forEach(mockRequest::addUserRole);
		ModelAndView mav = sut.content(mockRequest);
		assertEquals(viewName, mav.getViewName());
	}
}
