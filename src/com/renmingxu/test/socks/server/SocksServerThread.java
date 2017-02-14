package com.renmingxu.test.socks.server;

import com.renmingxu.test.socks.tool.Log;

/**
 * Created by renmingxu on 17-2-13.
 */
public class SocksServerThread extends Thread {
    private SocksServer socksServer;
    @Override
    public void run() {
        Log.info(this.getName() + " run");
        while(true) {
            SocksClient socksClient = socksServer.acceptSocksClient();
            if (socksClient == null) {
                return;
            }
            socksServer.addSocksClient(socksClient);
        }
    }
    public SocksServerThread(SocksServer socksServer) {
        super(socksServer.toString());
        this.socksServer = socksServer;
    }
}
