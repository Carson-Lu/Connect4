package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/*
 * Abstract class for Event Handling
 */
public abstract class EventHandler implements MouseListener, ActionListener {


    @Override
    public void mouseClicked(MouseEvent e) {
        // Do nothing
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // Do nothing
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // Do nothing
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        JButton button = (JButton) e.getSource();

        // Makes mouse look like its clickable
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 25));

    }

    @Override
    public void mouseExited(MouseEvent e) {
        // Do nothing
    }

    public abstract void actionPerformed(ActionEvent e);
}
