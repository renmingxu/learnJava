package com.renmingxu.test.swing;


import com.renmingxu.test.net.Http;
import sun.awt.image.ImageWatched;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by renmingxu on 2017/2/10.
 */

public class LoginFrame extends JFrame {
    private static final int defaultWidth = 350;
    private static final int defaultHeight = 165;
    private static final String defaultTitle = "登陆界面";
    private JPanel mainPanel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JButton loginButton;
    private JButton exitButton;
    private LinkLabel regLabel;
    private LinkLabel fpwLabel;

    public LoginFrame(String title, int width, int height) {
        super(title);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setBounds((screenSize.width - width) / 2,
                (screenSize.height - height) / 2,
                width, height);
        this.setResizable(false);
        this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
        Thread t = new Thread(){
            public void run() {
                try {
                    Thread.sleep(100);
                    LoginFrame.this.setIconImage(
                            Toolkit.getDefaultToolkit().getImage(
                                    new URL("https://qqooqq.com/favicon.ico")));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        t.start();

        // add Panel
        mainPanel = new JPanel(null);
        mainPanel.setBackground(Color.DARK_GRAY);
        this.setContentPane(mainPanel);

        // new Views
        usernameField = new JTextField();
        usernameLabel = new JLabel("账户: ");
        passwordField = new JPasswordField();
        passwordLabel = new JLabel("密码: ");
        loginButton = new JButton("登陆");
        exitButton = new JButton("退出");
        regLabel = new LinkLabel("注册", "https://qqooqq.com/chat/reg.php");
        fpwLabel = new LinkLabel("忘记密码", "https://qqooqq.com/char/fpw.php");


        // set Size Location Font

        Font fontSong = new Font("宋体",Font.PLAIN,16);
        Font fontCourier = new Font("Monospaced",Font.PLAIN,16);
        usernameLabel.setBounds(10, 10,100,30);
        usernameLabel.setFont(fontSong);
        usernameLabel.setForeground(Color.white);
        usernameField.setBounds(120, 10, 200,30);
        usernameField.setFont(fontCourier);
        passwordLabel.setBounds(10, 50, 100, 30);
        passwordLabel.setFont(fontSong);
        passwordLabel.setForeground(Color.white);
        passwordField.setBounds(120, 50, 200, 30);
        passwordField.setFont(fontCourier);
        loginButton.setBounds(10, 90, 100, 30);
        loginButton.setFont(fontSong);
        exitButton.setBounds(120, 90, 100, 30);
        exitButton.setFont(fontSong);
        regLabel.setBounds(230, 90, 35,30);
        regLabel.setFont(fontSong);
        fpwLabel.setBounds(270, 90, 70, 30);
        fpwLabel.setFont(fontSong);

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
                try {
                    String str = Http.get("https://qqooqq.com/chat/login.php?username=" + username + "&password=" + password);
                    if (str.equals("True")){

                    }
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });

        // add views to mainPanel
        mainPanel.add(usernameField);
        mainPanel.add(usernameLabel);
        mainPanel.add(passwordField);
        mainPanel.add(passwordLabel);
        mainPanel.add(loginButton);
        mainPanel.add(exitButton);
        mainPanel.add(regLabel);
        mainPanel.add(fpwLabel);
    }
    public LoginFrame(String title) {
        this(title, defaultWidth, defaultHeight);
    }
    public LoginFrame() {
        this(defaultTitle, defaultWidth, defaultHeight);
    }
}
