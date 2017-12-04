package io.vividcode.feature9.module;

import static org.junit.Assert.assertTrue;

import java.net.URISyntaxException;
import java.util.Optional;
import org.junit.Test;

public class ResourceTest {

	@Test
	public void testResources() throws URISyntaxException {
		final Optional<Module> moduleOpt = ModuleTestSupport.getModule();
		assertTrue(moduleOpt.isPresent());
		final Module module = moduleOpt.get();
		assertTrue(module.isOpen("config"));
		assertTrue(module
				.getClassLoader()
				.resources("config/application.properties")
				.count() > 0
		);
	}
}
