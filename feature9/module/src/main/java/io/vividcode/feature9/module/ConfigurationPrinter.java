package io.vividcode.feature9.module;

import java.lang.module.Configuration;
import java.lang.module.ModuleFinder;
import java.lang.module.ResolvedModule;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ConfigurationPrinter {

  public void printLayer(final Configuration configuration) {
    sortedModules(configuration.modules()).forEach(module -> {
      System.out.println(module.name());
      printModule(module);
      System.out.println("");
    });
  }

  private void printModule(final ResolvedModule module) {
    sortedModules(module.reads())
        .forEach(m -> System.out.println("|--" + m.name()));
  }

  private List<ResolvedModule> sortedModules(final Set<ResolvedModule> modules) {
    return modules
        .stream()
        .sorted(Comparator.comparing(ResolvedModule::name))
        .collect(Collectors.toList());
  }

  public static void main(final String[] args) {
    final Configuration configuration = Configuration.resolve(
        ModuleFinder.of(Paths.get(args[0])),
        List.of(Configuration.empty()),
        ModuleFinder.ofSystem(),
        List.of("io.vividcode.store.runtime")
    );
    new ConfigurationPrinter().printLayer(configuration);
  }
}
