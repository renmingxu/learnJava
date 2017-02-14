package com.renmingxu.test.socks.tool;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Date;

/**
 * Created by renmingxu on 17-2-13.
 */
public class Log {
    public static final int DEBUG = 0;
    public static final int INFO = 1;
    public static final int WARN = 2;
    public static final int ERROR = 4;

    public static PrintStream out = System.out;

    public static void debug(String debug) {
        //out.println("Log DEBUG(" + getTimeString() + "): " + debug);
    }
    public static void info(String info) {
        out.println("Log  INFO(" + getTimeString() + "): " + info);
    }
    public static void warn(String warn) {
        out.println("Log  WARN(" + getTimeString() + "): " + warn);
    }
    public static void error(String error) {
        out.println("Log ERROR(" + getTimeString() + "): " + error);
    }
    public static void log(String s) {
        info(s);
    }
    public static void log(String s, int logLevel) {
        switch (logLevel) {
            case INFO:
                info(s);
                break;
            case WARN:
                warn(s);
                break;
            case ERROR:
                error(s);
                break;
            default:

        }
    }
    public static String getTimeString() {
        return new Date().toString();
    }
}
