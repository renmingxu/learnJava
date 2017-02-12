package com.renmingxu.test.swing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by renmingxu on 2017/2/10.
 */
public class LinkLabel extends JLabel {
    private URI link;
    private String text;
    public LinkLabel(String text, String uri) {
        try {
            link = new URI(uri);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        this.text = text;
        addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                LinkLabel.this.setForeground(Color.red);
                LinkLabel.this.repaint();
            }
            public void mouseExited(MouseEvent e) {
                LinkLabel.this.setForeground(Color.blue);
                LinkLabel.this.repaint();
            }
            public void mouseClicked(MouseEvent e) {
                try {
                    Desktop.getDesktop().browse(link);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        setText(text);
        setForeground(Color.blue);
    }
}
