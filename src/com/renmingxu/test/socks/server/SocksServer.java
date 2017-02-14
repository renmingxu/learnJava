package com.renmingxu.test.socks.server;

import com.renmingxu.test.socks.tool.Log;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.*;

/**
 * Created by renmingxu on 17-2-13.
 */
public class SocksServer {

    private static final int THREAD_POOL_CORE_POOL_SIZE = 100;
    private static final int THREAD_POOL_MAX_POOL_SIZE = 1024;
    private static final int THREAD_POOL_KEEP_ALIVE_TIME = 10;
    public static SocksServer This;

    private int port;
    private ServerSocket serverSocket;
    public ExecutorService clientThreadPool;
    private SocksServerThread socksServerThread;
    private ArrayList<SocksClient> socksClientsList;

    public SocksServer(int port) {
        this.port = port;
        this.This = this;
    }
    public boolean init() {
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        socksClientsList = new ArrayList<>();
        /*
        clientThreadPool = new ThreadPoolExecutor(
                        THREAD_POOL_CORE_POOL_SIZE,
                        THREAD_POOL_MAX_POOL_SIZE,
                        THREAD_POOL_KEEP_ALIVE_TIME,
                        TimeUnit.MILLISECONDS,
                        new LinkedBlockingQueue<Runnable>());
                        */
        clientThreadPool = Executors.newCachedThreadPool();
        //clientThreadPool;
        socksServerThread = new SocksServerThread(this);
        run();
        return true;
    }
    public void run() {
        clientThreadPool.execute(socksServerThread);
    }
    public void addSocksClient(SocksClient socksClient) {
        socksClientsList.add(socksClient);
        clientThreadPool.execute(new SocksClientThread(socksClient));
    }
    public void removeSocksClient(SocksClient socksClient) {
        socksClientsList.remove(socksClient);
        Log.debug(socksClient + "removed");
    }
    public SocksClient acceptSocksClient() {
        Socket newClientSocket;
        try {
             newClientSocket = serverSocket.accept();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        SocksClient socksClient = new SocksClient(newClientSocket);
        return socksClient;
    }
    public void close() {
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
    }

    @Override
    public String toString() {
        return "SocksServer(ServerAddress:" + serverSocket.getLocalSocketAddress() + ")";
    }
}
