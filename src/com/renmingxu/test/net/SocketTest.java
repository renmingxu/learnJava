package com.renmingxu.test.net;

/**
 * Created by renmingxu on 2017/2/7.
 */


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.ServerSocket;
import java.net.SocketAddress;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.alibaba.fastjson.*;
import com.renmingxu.test.encrypt.AesCbcPKCS5PaddingEncrypter;


public class SocketTest {
    public static void main (String args[]) throws IOException {
        String tmp = new String();

        Socket s = new Socket("localhost", 9999);

        OutputStream outstream = s.getOutputStream();
        InputStream instream = s.getInputStream();
        AesCbcPKCS5PaddingEncrypter cipher =
                AesCbcPKCS5PaddingEncrypter.getInstance("zhoushujie123456" ,"1234567812345678");
        String str = "renmingxuasdfasdfasdf";
        for (int i = 0; i < 11; i++) {
            str += str;
        }
        System.out.println("length sent: " + str.length());
        outstream.write(cipher.encrypt(str));
        byte[] b = new byte[1024000];

        int len = instream.read(b);
        tmp = new String(cipher.decrypt(b, 0, len));
        System.out.println("length sent: " + tmp.length());
        System.out.println(tmp);
        s.close();

    }
}
