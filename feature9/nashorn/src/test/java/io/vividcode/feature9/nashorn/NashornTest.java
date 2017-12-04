package io.vividcode.feature9.nashorn;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.InputStreamReader;
import java.util.Optional;
import javax.script.Bindings;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.script.SimpleBindings;
import jdk.nashorn.api.scripting.NashornScriptEngineFactory;
import jdk.nashorn.api.scripting.ScriptObjectMirror;
import org.junit.Test;

public class NashornTest {

  private final ScriptEngine es5Engine =
      new ScriptEngineManager().getEngineByName("Nashorn");
  private final ScriptEngine es6Engine =
      new NashornScriptEngineFactory().getScriptEngine("--language=es6");

  @Test
  public void testSimpleEval() throws Exception {
    assertEquals(2, this.es5Engine.eval("1 + 1"));
  }

  @Test
  public void testBinaryAndOctalLiterals() throws Exception {
    assertEquals(503, this.es6Engine.eval("0b111110111"));
    assertEquals(503, this.es6Engine.eval("0o767"));
  }

  @Test
  public void testTemplateString() throws Exception {
    final Bindings bindings = new SimpleBindings();
    bindings.put("name", "Alex");
    final Object result = this.es6Engine.eval("`Hello ${name}`", bindings);
    assertEquals("Hello Alex", result);
  }

  @Test
  public void testIterator() throws Exception {
    final ScriptObjectMirror result =
        (ScriptObjectMirror) eval("iterator", null);
    assertTrue(result.isArray());
    assertEquals(10, result.size());
  }

  @Test
  public void testFunction() throws Exception {
    final Object result = eval("function", null);
    assertEquals(3, result);
  }

  @Test(expected = RuntimeException.class)
  public void testClass() throws Exception {
    final Object result = eval("class", null);
    assertEquals(314.1592653589793, result);
  }

  private Object eval(final String fileName, final Bindings inputBindings)
      throws ScriptException {
    final Bindings bindings = Optional.ofNullable(inputBindings)
        .orElse(new SimpleBindings());
    return this.es6Engine.eval(
        new InputStreamReader(
            NashornTest.class.getResourceAsStream(
                String.format("/%s.js", fileName)
            )),
        bindings
    );
  }
}
