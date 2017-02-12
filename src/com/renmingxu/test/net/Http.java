package com.renmingxu.test.net;

/**
 * Created by renmingxu on 2017/2/5.
 */

import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.Buffer;
import java.util.*;

public class Http {
    public static Map<String, List<String>> lastGetHeaders;
    public static String get(String url) {
        URLConnection urlconn = null;
        String result = new String();
        char[] tmpc = new char[10240];
        int tmpi;
        BufferedReader in;
        try {
            urlconn = new URL(url).openConnection();
            urlconn.connect();
            lastGetHeaders = urlconn.getHeaderFields();
            in = new BufferedReader(new InputStreamReader(
                    urlconn.getInputStream()));
            for(;;){
                tmpi = in.read(tmpc);
                if (tmpi <= 0){
                    break;
                }
                result += new String(tmpc).substring(0, tmpi);
                tmpc = new char[10240];
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}