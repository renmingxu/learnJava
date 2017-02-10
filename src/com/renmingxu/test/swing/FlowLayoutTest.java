package com.renmingxu.test.swing;

import javax.swing.*;
import java.awt.*;

/**
 * Created by renmingxu on 2017/2/10.
 */
public class FlowLayoutTest {
    public static void main(String args[]) {
        JFrame jframe = new JFrame("JFrame Test");
        jframe.setDefaultCloseOperation(jframe.EXIT_ON_CLOSE);
        jframe.setBounds(500, 300, 500, 500);

        JPanel jpanel = new JPanel();
        jpanel.setLayout(new FlowLayout(FlowLayout.LEFT, 40, 20));
        jpanel.setBackground(Color.blue);

        jframe.setContentPane(jpanel);

        JButton btn1 = new JButton("Button1");
        JButton btn2 = new JButton("Button2");
        JButton btn3 = new JButton("Button1");
        JButton btn4 = new JButton("Button2");
        JButton btn5 = new JButton("Button1");
        JButton btn6 = new JButton("Button2");
        JButton btn7 = new JButton("Button1");
        JButton btn8 = new JButton("Button2");



        jpanel.add(btn1);
        jpanel.add(btn2);
        jpanel.add(btn3);
        jpanel.add(btn4);
        jpanel.add(btn5);
        jpanel.add(btn6);
        jpanel.add(btn7);
        jpanel.add(btn8);

        // jframe.setResizable(false);
        jframe.setVisible(true);
    }
}
