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


public class SocketTest {
    public static void main (String args[]) throws IOException {
        String tmp = new String();

        Socket s = new Socket("localhost", 9999);

        OutputStream outstream = s.getOutputStream();
        InputStream instream = s.getInputStream();


        Map<String, String> m = new HashMap<String, String>();
        m.put("asdf", "asdf");
        m.put("asdf", "asdf");
        m.put("asdf", "asdf");

        String jsonstr = JSON.toJSONString(m) + "asdf";

        outstream.write(jsonstr.getBytes());
        byte[] b = new byte[100];

        int len = instream.read(b);
        tmp = new String(b);
        System.out.println(tmp);
        JSONObject j = JSON.parseObject(tmp);
        for(java.util.Map.Entry<String,Object> entry:j.entrySet()){
            System.out.println(entry.getKey() + " - " + entry.getValue());
        }
        s.close();

    }
}
