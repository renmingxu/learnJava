package com.renmingxu.test.socks.server;

import com.renmingxu.test.socks.tool.Log;

/**
 * Created by renmingxu on 17-2-13.
 */
public class SocksClientThread extends Thread {

    public static final int INIT_CLIENT = 0;
    public static final int RECV_FROM_REMOTE = 1;
    public static final int RECV_FROM_CLIENT = 2;

    private int threadType;
    private SocksClient socksClient;

    @Override
    public void run() {
        switch (threadType) {
            case INIT_CLIENT:
                initClient();
                break;
            case RECV_FROM_CLIENT:
                recvFromClient();
                break;
            case RECV_FROM_REMOTE:
                recvFromRemote();
                break;
            default:

        }
    }

    public void initClient() {
        Log.info(this.getName() + " run");
        if (socksClient.init()) {
            if (socksClient.initAddress()) {
                socksClient.run();
                return;
            }
        }
        socksClient.close();

    }

    public void recvFromClient() {
        socksClient.recvFromClient();
    }

    public void recvFromRemote() {
        socksClient.recvFromRemote();
    }

    public SocksClientThread(SocksClient socksClient) {
        this(socksClient,INIT_CLIENT);
    }
    public SocksClientThread(SocksClient socksClient, int threadType) {
        super(socksClient.toString());
        this.socksClient = socksClient;
        this.threadType = threadType;
    }
}
