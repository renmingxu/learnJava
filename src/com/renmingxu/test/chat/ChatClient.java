package com.renmingxu.test.chat;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.renmingxu.test.encrypt.AES;
import com.renmingxu.test.encrypt.AesCbcPKCS5PaddingEncrypter;
import com.renmingxu.test.net.Http;

import javax.net.ssl.SNIHostName;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by renmingxu on 2017/2/12.
 */
public class ChatClient {
    private String username;
    private String password;
    private String randomKey;
    private Socket sock;
    private OutputStream outStream;
    private InputStream inStream;
    private AesCbcPKCS5PaddingEncrypter cipher;
    private Thread clientRecvDataPacketFromServerThread;
    private static final String DEFAULT_ENCRYPT_IV = "1234567812345678";
    public ChatClient(String username, String password) {
        this.username = username;
        this.password = password;
    }
    public boolean LoginConnect() {
        String str = Http.get("http://renmingxu.com/php/login.php?username=" + username + "&password=" + password);
        JSONObject j = JSON.parseObject(str);
        if (j.getBoolean("access") == true){
            randomKey = j.getJSONObject("data").getString("randomkey");
            if (randomKey.length() == 16) {
                cipher = AesCbcPKCS5PaddingEncrypter.getInstance(randomKey, DEFAULT_ENCRYPT_IV);
            } else {
                System.out.println("Wrong randomKey length");
                return false;
            }
        } else {
            System.out.println(j.getString("info"));
            return false;
        }
        try {
            sock = new Socket("renmingxu.com", 7777);
            outStream = sock.getOutputStream();
            inStream = sock.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        j = new JSONObject();
        j.put("username", username);
        j.put("token", AES.byteToHexString(cipher.encrypt(username)));
        String jsonstr = JSON.toJSONString(j);
        try {
            outStream.write(jsonstr.getBytes("UTF-8"));
            byte[] dataencrypt = new byte[50000];

            int len = inStream.read(dataencrypt);
            byte[] data = cipher.decryptwithzero(dataencrypt);
            j = JSON.parseObject(new String(data,"UTF-8"));
            if (j.getString("cmd").equals("ClientInit")) {
                clientRecvDataPacketFromServerThread = new Thread(){
                  public void run(){
                      ChatClient.this.RecvDataPacketFromServer();
                      System.out.println("return from RecvDataPacketFromServer");
                  }
                };
                clientRecvDataPacketFromServerThread.start();
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    private void RecvDataPacketFromServer(){
        while(true){
            byte[] b = new byte[50000];
            try {
                int len = inStream.read(b);
                byte[] data = cipher.decryptwithzero(b);
                JSONObject j = JSON.parseObject(new String(data, "UTF-8"));
                JSONObjectPacketHandler(j);
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }

        }
    }
    private void SendJSONObject(JSONObject j){
        String jsonstr = JSON.toJSONString(j);
        byte[] dataencrypt = cipher.encrypt(jsonstr);
        try {
            outStream.write(dataencrypt);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void JSONObjectPacketHandler(JSONObject j) {
        switch(j.getString("cmd")) {
            case "PrivateTextMessage":
                OnPrivateTextMessage(j.getJSONObject("data"));
                break;
            case "OnlineUsers":
                OnOnlineUsers(j.getJSONObject("data"));
                break;
            default:

        }
    }
    private void OnPrivateTextMessage(JSONObject j) {
        System.out.println(j);
    }
    private void OnOnlineUsers(JSONObject j) {
        System.out.println(j);
    }
    public void SendPrivateTextMessage(String usernameTo, String messageContent){
        JSONObject j = new JSONObject();
        j.put("cmd", "PrivateTextMessage");
        JSONObject jdata = new JSONObject();
        jdata.put("UsernameTo", usernameTo);
        jdata.put("MessageContent", messageContent);
        jdata.put("SendTime", System.currentTimeMillis());
        j.put("data", jdata);
        SendJSONObject(j);
    }
    public void GetOnlineUsers(){
        JSONObject j = new JSONObject();
        j.put("cmd", "OnlineUsers");
        j.put("data", new JSONObject());
        SendJSONObject(j);
    }
}
