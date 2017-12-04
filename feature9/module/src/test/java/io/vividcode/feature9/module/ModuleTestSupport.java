package io.vividcode.feature9.module;

import java.lang.module.Configuration;
import java.lang.module.ModuleFinder;
import java.lang.module.ModuleReference;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

public class ModuleTestSupport {

	public static final String MODULE_NAME = "io.vividcode.store.common";

	public static ModuleFinder getModuleFinder() throws URISyntaxException {
		return ModuleFinder.of(
				Paths.get(
						ModuleDescriptorTest.class.getResource("/modules").toURI()));
	}

	public static ModuleReference getModuleReference() throws URISyntaxException {
		return getModuleFinder().find(MODULE_NAME).get();
	}

	public static Configuration getConfiguration() throws URISyntaxException {
		return ModuleLayer.boot().configuration().resolve(
				getModuleFinder(),
				ModuleFinder.ofSystem(),
				List.of(MODULE_NAME)
		);
	}

	public static ModuleLayer getModuleLayer() throws URISyntaxException {
		return ModuleLayer.boot().defineModulesWithOneLoader(
				getConfiguration(),
				ClassLoader.getSystemClassLoader()
		);
	}

	public static Optional<Module> getModule() throws URISyntaxException {
		return getModuleLayer().findModule(MODULE_NAME);
	}
}
