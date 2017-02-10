package com.renmingxu.test.swing;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by renmingxu on 2017/2/10.
 */
public class LoginFrame extends JFrame {
    private static int defaultWidth = 350;
    private static int defaultHeight = 180;
    private static String defaultTitle = "LoginFrame";
    private JPanel mainPanel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JButton loginButton;
    private JButton exitButton;

    public LoginFrame() {
        super(defaultTitle);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setBounds((screenSize.width - defaultWidth) / 2,
                (screenSize.height - defaultHeight) / 2,
                defaultWidth, defaultHeight);
        this.setResizable(false);
        this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);


        // add Panel
        mainPanel = new JPanel(null);
        mainPanel.setBackground(Color.gray);
        this.setContentPane(mainPanel);

        // new Views
        usernameField = new JTextField();
        usernameLabel = new JLabel("Username: ");
        passwordField = new JPasswordField();
        passwordLabel = new JLabel("Password: ");
        loginButton = new JButton("Login");
        exitButton = new JButton("Exit");


        // set Size Location Font

        Font font = new Font("Courier New",Font.PLAIN,16);
        usernameLabel.setBounds(10, 10,100,30);
        usernameLabel.setFont(font);
        usernameField.setBounds(120, 10, 200,30);
        usernameField.setFont(font);
        passwordLabel.setBounds(10, 50, 100, 30);
        passwordLabel.setFont(font);
        passwordField.setBounds(120, 50, 200, 30);
        passwordField.setFont(font);
        loginButton.setBounds(10, 90, 100, 30);
        loginButton.setFont(font);
        exitButton.setBounds(120, 90, 100, 30);
        exitButton.setFont(font);

        // add ActionListener

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                System.out.println(username + password);
            }
        });

        // add views to mainPanel
        mainPanel.add(usernameField);
        mainPanel.add(usernameLabel);
        mainPanel.add(passwordField);
        mainPanel.add(passwordLabel);
        mainPanel.add(loginButton);
        mainPanel.add(exitButton);
    }
}
