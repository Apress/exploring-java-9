package io.vividcode.feature9.ui;

import static javax.swing.JOptionPane.YES_NO_OPTION;
import static javax.swing.JOptionPane.YES_OPTION;

import java.awt.Desktop;
import java.awt.HeadlessException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class QuitHandlerDemo {

  private static class MainFrame extends JFrame {

    public MainFrame() throws HeadlessException {
      setTitle("Quit Handler");
      setSize(400, 300);
      setVisible(true);
    }
  }

  public static void main(final String[] args) {
    final MainFrame frame = new MainFrame();
    frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    final Desktop desktop = Desktop.getDesktop();
    desktop.setQuitHandler((e, response) -> {
      final int result = JOptionPane
          .showConfirmDialog(frame, "Quit the app?", "Quit?", YES_NO_OPTION);
      if (result == YES_OPTION) {
        response.performQuit();
      } else {
        response.cancelQuit();
      }
    });
  }
}
