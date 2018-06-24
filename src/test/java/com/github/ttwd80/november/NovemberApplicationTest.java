package com.github.ttwd80.november;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.builder.SpringApplicationBuilder;

class NovemberApplicationTest {

	NovemberApplication sut;

	boolean configureCalled;

	@BeforeEach
	void setUp() throws Exception {
		sut = new NovemberApplication();
	}

	@Test
	void testPasswordEncoder() {
		assertNotNull(sut.passwordEncoder());
	}

	@Test
	void testConfigureSpringApplicationBuilder() {
		this.configureCalled = false;
		class MockSpringApplicationBuilder extends SpringApplicationBuilder {
			public SpringApplicationBuilder sources(Class<?>... sources) {
				NovemberApplicationTest.this.configureCalled = true;
				return this;
			}
		}
		SpringApplicationBuilder mockSpringApplicationBuilder = new MockSpringApplicationBuilder();
		sut.configure(mockSpringApplicationBuilder);
		assertTrue(this.configureCalled);
	}

}
