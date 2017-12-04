package io.vividcode.feature9.ui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Desktop;
import java.awt.HeadlessException;
import java.awt.desktop.AppForegroundEvent;
import java.awt.desktop.AppForegroundListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.JFrame;
import javax.swing.JTextArea;

public class SystemEventsDemo {

  private static class MainFrame extends JFrame {

    final JTextArea textArea = new JTextArea();

    public MainFrame() throws HeadlessException {
      final Container container = getContentPane();
      final BorderLayout layout = new BorderLayout(5, 5);
      container.setLayout(layout);
      this.textArea.setEditable(false);
      container.add(this.textArea, BorderLayout.CENTER);

      setTitle("System events");
      setSize(400, 300);
      setVisible(true);

      final Desktop desktop = Desktop.getDesktop();
      desktop.addAppEventListener(new AppForegroundListener() {
        @Override
        public void appRaisedToForeground(final AppForegroundEvent e) {
          log("App to foreground");
        }

        @Override
        public void appMovedToBackground(final AppForegroundEvent e) {
          log("App to background");
        }
      });

      desktop.setAboutHandler(e -> log("About"));

      desktop.setPreferencesHandler(e -> log("Preferences"));
    }

    private void log(final String message) {
      this.textArea.append(String
          .format("%s: %s%n", LocalDateTime.now()
                  .format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
              message));
    }
  }

  public static void main(final String[] args) {
    final MainFrame frame = new MainFrame();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }
}
