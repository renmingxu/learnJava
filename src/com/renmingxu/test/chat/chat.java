package com.renmingxu.test.chat;

import java.util.Scanner;

/**
 * Created by renmingxu on 2017/2/12.
 */
public class chat {
    public static void main(String args[]) {
        Scanner input = new Scanner(System.in);
        System.out.print("Username:");
        String username = input.next();
        System.out.print("Password:");
        String password = input.next();
        ChatClient chatClient = new ChatClient(username, password);
        if (chatClient.LoginConnect()) {
            System.out.println("LoginConnect successed");
            chatClient.GetOnlineUsers();
            while (true) {
                System.out.println("sendmsg,onlineusers");
                String cmd = input.next();
                input.nextLine();
                switch (cmd) {
                    case "sendmsg":
                        System.out.print("To:");
                        String usernameTo = input.next();
                        input.nextLine();
                        System.out.print("Content:");
                        String messageContent = input.nextLine();
                        System.out.println(messageContent);
                        chatClient.SendPrivateTextMessage(usernameTo, messageContent);
                        break;
                    case "onlineusers":
                        chatClient.GetOnlineUsers();
                }

            }
        }

    }
}
