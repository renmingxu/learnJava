package com.renmingxu.test.swing;


import javax.swing.*;
import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by renmingxu on 2017/2/10.
 */
public class LoginFrame extends JFrame {
    private static int defaultWidth = 400;
    private static int defaultHeight = 300;
    private static String defaultTitle = "LoginFrame";
    private JPanel mainPanel;
    private JTextField usernameField;
    private JPasswordField passwordField;


    public LoginFrame() {
        super(defaultTitle);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setBounds((screenSize.width - defaultWidth) / 2,
                (screenSize.height - defaultHeight) / 2,
                defaultWidth, defaultHeight);
        this.setResizable(false);
        this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);

        mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(Color.gray);

        this.setContentPane(mainPanel);

        usernameField = new JTextField();
        passwordField = new JPasswordField();


    }
}
