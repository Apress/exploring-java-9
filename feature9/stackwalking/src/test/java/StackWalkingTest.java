import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.lang.StackWalker.Option;
import java.lang.System.Logger.Level;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.Test;

public class StackWalkingTest {

  @Test
  public void testWalkClass() throws Exception {
    final StackWalker stackWalker =
        StackWalker.getInstance(Option.RETAIN_CLASS_REFERENCE);
    final Set<String> classNames = stackWalker.walk(stream ->
        stream.map(StackWalker.StackFrame::getClassName)
            .collect(Collectors.toSet())
    );
    assertTrue(classNames.contains("StackWalkingTest"));
  }

  @Test
  public void testForEach() throws Exception {
    final StackWalker stackWalker =
        StackWalker.getInstance(Set.of(Option.SHOW_HIDDEN_FRAMES,
            Option.SHOW_REFLECT_FRAMES));
    stackWalker.forEach(stackFrame -> System.out.printf("%6d| %s -> %s %n",
        stackFrame.getLineNumber(),
        stackFrame.getClassName(),
        stackFrame.getMethodName()));
  }

  @Test
  public void testGetCallerClass() throws Exception {
    final System.Logger logger = LoggerManager.getLogger();
    assertEquals("null/StackWalkingTest", logger.getName());
    logger.log(Level.INFO, "Hello World");
  }

}
