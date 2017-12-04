package io.vividcode.feature9.nashorn;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import jdk.nashorn.api.tree.CompilationUnitTree;
import jdk.nashorn.api.tree.Diagnostic;
import jdk.nashorn.api.tree.FunctionDeclarationTree;
import jdk.nashorn.api.tree.Parser;
import jdk.nashorn.api.tree.SimpleTreeVisitorES6;
import org.junit.Test;

public class ParserAPITest {

  @Test
  public void testSimpleParser() throws Exception {
    final Parser parser = Parser.create("--language=es6");
    final CompilationUnitTree tree =
        parser.parse(
            "test.js",
            "function a() {return 'hello';}",
            System.out::println
        );
    final List<String> functions = new ArrayList<>();
    tree.accept(new SimpleTreeVisitorES6<Void, Void>() {
      @Override
      public Void visitFunctionDeclaration(
          final FunctionDeclarationTree node,
          final Void r) {
        final String name = node.getName().getName();
        assertEquals("a", name);
        functions.add(name);
        return null;
      }
    }, null);
    assertEquals(1, functions.size());
  }

  @Test
  public void testParseError() throws Exception {
    final Parser parser = Parser.create("--language=es6");
    final List<Diagnostic> errors = new ArrayList<>();
    parser.parse(
        "error.js",
        "function error() { var a = 1;",
        diagnostic -> {
          assertEquals(1, diagnostic.getLineNumber());
          assertEquals(Diagnostic.Kind.ERROR, diagnostic.getKind());
          errors.add(diagnostic);
        }
    );
    assertEquals(1, errors.size());
  }
}
