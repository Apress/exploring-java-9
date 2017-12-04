package io.vividcode.feature9.ui;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.JComponent;
import javax.swing.JFrame;

public class MultiResolutionImageDemo {

  private static class ImageCanvas extends JComponent {

    private final String name;

    public ImageCanvas(final String name) {
      this.name = name;
    }

    @Override
    public void paint(final Graphics g) {
      final Image image = Toolkit.getDefaultToolkit()
          .getImage(
              MultiResolutionImageDemo.class
                  .getResource(String.format("/images/%s", this.name)));
      g.drawImage(image, 10, 10, this);
      g.dispose();
    }
  }

  public static void main(final String[] args) {
    final JFrame frame = new JFrame();
    frame.setTitle("Multi-resolution images");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setLayout(new BorderLayout());
    final ImageCanvas canvas = new ImageCanvas("flower.png");
    frame.add("Center", canvas);
    frame.setSize(360, 300);
    frame.setVisible(true);
  }
}
