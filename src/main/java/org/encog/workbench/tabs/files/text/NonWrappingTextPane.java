package org.encog.workbench.tabs.files.text;

import java.awt.Component;

import javax.swing.JTextPane;
import javax.swing.plaf.ComponentUI;
import javax.swing.text.StyledDocument;

public class NonWrappingTextPane extends JTextPane {
  public NonWrappingTextPane() {
    super();
  }

  public NonWrappingTextPane(StyledDocument doc) {
    super(doc);
  }

  public boolean getScrollableTracksViewportWidth() {
    Component parent = getParent();
    ComponentUI ui = getUI();

    return parent != null ? (ui.getPreferredSize(this).width <= parent
        .getSize().width) : true;
  }
}