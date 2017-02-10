package com.renmingxu.test.net;

/**
 * Created by renmingxu on 2017/2/5.
 */

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.Buffer;
import java.util.*;

public class HttpGet {
    public static void main(String args[]) throws IOException {
        String result = "", line;
        BufferedReader in;
        BufferedInputStream bin;
        int tmpi = 1;
        char[] tmpc = new char[1024];
        byte[] tmpb = new byte[1024];

        URLConnection urlconn = new URL("https://qqooqq.com/").openConnection();
        urlconn.connect();
        Map<String, List<String>> headermap = urlconn.getHeaderFields();
        for (String key : headermap.keySet()) {
            System.out.println(key + " ---> " + headermap.get(key));
        }
        in = new BufferedReader(new InputStreamReader(
                urlconn.getInputStream()));
        bin = new BufferedInputStream( urlconn.getInputStream());
        for ( ;tmpi > 0;tmpi = in.read(tmpc)){
            result += new String(tmpc);
            tmpc = new char[1024];
        }
        for ( ;tmpi > 0;tmpi = bin.read(tmpb)){
            result += new String(tmpb);
            tmpb = new byte[1024];
        }
        System.out.println(result);
    }
}