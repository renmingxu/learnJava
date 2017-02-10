package com.renmingxu.test.swing;

import javax.swing.*;
import java.awt.*;

/**
 * Created by renmingxu on 2017/2/10.
 */
public class JPanelTest {
    public static void main(String args[]) {
        JFrame jframe = new JFrame("JFrame Test");
        jframe.setDefaultCloseOperation(jframe.EXIT_ON_CLOSE);
        jframe.setBounds(500, 300, 500, 500);

        JPanel jpanel = new JPanel();
        jpanel.setLayout(null);
        jpanel.setBackground(Color.blue);

        jframe.setContentPane(jpanel);
        JPanel p1 = new JPanel();
        JPanel p2 = new JPanel();

        p1.setLayout(null);
        p1.setBackground(Color.red);
        p1.setSize(100,100);

        p2.setLayout(null);
        p2.setBackground(Color.green);
        p2.setSize(100,100);
        p2.setLocation(100,100);


        jpanel.add(p1);
        jpanel.add(p2);

        jframe.setResizable(false);
        jframe.setVisible(true);
    }
}
