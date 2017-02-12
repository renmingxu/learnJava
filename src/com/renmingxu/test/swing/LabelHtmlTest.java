package com.renmingxu.test.swing;

import sun.awt.image.ImageWatched;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by renmingxu on 2017/2/10.
 */
public class LabelHtmlTest {
    public static void main(String args[]) {
        JFrame jframe = new JFrame();
        jframe.setDefaultCloseOperation(jframe.EXIT_ON_CLOSE);
        jframe.setBounds(500, 300, 500, 500);
        JPanel panel = (JPanel) jframe.getContentPane();
        LinkLabel label = new LinkLabel("Link", "https://qqooqq.com/");
        panel.add(label);
        jframe.setVisible(true);
    }
}
