import java.lang.StackWalker.Option;

public class LoggerManager {

  private final static StackWalker walker =
      StackWalker.getInstance(Option.RETAIN_CLASS_REFERENCE);

  public static System.Logger getLogger() {
    final Class<?> caller = walker.getCallerClass();
    return System.getLogger(
        String.format("%s/%s", caller.getModule().getName(), caller.getName()));
  }
}
