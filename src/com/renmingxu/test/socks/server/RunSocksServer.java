package com.renmingxu.test.socks.server;

import com.renmingxu.test.socks.tool.Log;

import java.util.Date;

/**
 * Created by renmingxu on 17-2-13.
 */
public class RunSocksServer {
    public static void main(String[] args) {
        int port = 1090;
        if (args.length == 2) {
            port = Integer.valueOf(args[1]);
        }
        SocksServer socksServer = new SocksServer(port);
        if (socksServer.init()){
            Log.info("Server run");
        }
    }
}
