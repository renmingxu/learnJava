package com.renmingxu.test.socks.server;

import com.renmingxu.test.socks.tool.Log;
import com.renmingxu.test.socks.tool.Tools;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StreamCorruptedException;
import java.net.Socket;

/**
 * Created by renmingxu on 17-2-13.
 */
public class SocksClient {

    private static final byte[] SOCKS_METHODS = {0,};
    private static final byte SOCKS_VERSION = 5;
    private static final byte SOCK_CMD_CONNECT = 1;
    private static final byte[] SOCKS_CMD = {SOCK_CMD_CONNECT,};

    private static final byte SOCKS_ADDRESS_TYPE_IPV4 = 1;
    private static final int SOCKS_REQ_DATA_LENGTH_IPV4 = 10;
    private static final byte SOCKS_ADDRESS_TYPE_DOMAIN = 3;


    private Socket clientSocket;
    private InputStream clientInputStream;
    private OutputStream clientOutputStream;
    private Socket remoteSocket;
    private InputStream remoteInputStream;
    private OutputStream remoteOutputStream;
    
    private SocksClientThread initClient;
    private SocksClientThread recvFromClientThread;
    private SocksClientThread recvFromRemoteThread;
    private String remoteAddress;
    private int remotePort;
    private int method;
    private boolean removed = false;

    public SocksClient(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }
    public boolean init() {
        byte[] b = new byte[50000];
        int len;
        try {
            clientInputStream = clientSocket.getInputStream();
            clientOutputStream = clientSocket.getOutputStream();
            len = clientInputStream.read(b);
            b = Tools.subByte(b, 0, len);
            Log.debug(Tools.byteToHexString(b));
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        byte socksVersion = b[0];
        if (socksVersion != SOCKS_VERSION) {
            Log.warn("Wrong Version" + this);
            return false;
        }
        int socksMethodLength = b[1];
        int dataLenghtShouldBe = 2 + socksMethodLength;
        if (len != dataLenghtShouldBe) {
            return false;
        }
        byte[] socksMethods = Tools.subByte(b, 2, socksMethodLength);
        for (byte Method : SOCKS_METHODS) {
            for (byte socksMethod : socksMethods) {
                if (socksMethod == Method) {
                    this.method = socksMethod;
                    byte[] reponseByte = {5,  socksMethod};

                    Log.debug("Init reponseByte: "  + Tools.byteToHexString(reponseByte));
                    try {
                        clientOutputStream.write(reponseByte);
                    } catch (IOException e) {
                        e.printStackTrace();
                        return false;
                    }
                    return true;
                }
            }
        }
        byte[] noAvailableMethodByte = {5, 127};
        try {
            clientOutputStream.write(noAvailableMethodByte);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    public boolean initAddress() {
        byte[] b = new byte[50000];
        int len;
        try {
            len = clientInputStream.read(b);
            b = Tools.subByte(b, 0, len);
            Log.debug(Tools.byteToHexString(b));
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        if (len < 4) {
            return false;
        }
        byte socksVersion = b[0];
        byte socksCmd = b[1];
        if (socksCmd == SOCK_CMD_CONNECT) {
            byte socksRsv = b[2];
            byte socksAddressType = b[3];
            boolean noerror = false;
            if (socksAddressType == SOCKS_ADDRESS_TYPE_IPV4) {
                if (len != SOCKS_REQ_DATA_LENGTH_IPV4) {
                    return false;
                }
                remoteAddress = Tools.byteToInt(b[4]) + "." +
                        Tools.byteToInt(b[5]) + "." +
                        Tools.byteToInt(b[6]) + "." +
                        Tools.byteToInt(b[7]);
                remotePort = Tools.byteToInt(b[8]) * 256 + Tools.byteToInt(b[9]);
                noerror = true;
            }
            if (socksAddressType == SOCKS_ADDRESS_TYPE_DOMAIN) {
                if (len < 5) {
                    return false;
                }
                int domainLength = Tools.byteToInt(b[4]);
                int dataLengthShouldBe = 7 + domainLength;
                if (len != dataLengthShouldBe) {
                    return false;
                }
                byte[] domainName = Tools.subByte(b, 5, domainLength);
                Log.debug(new String(domainName));
                remoteAddress = new String(domainName);
                remotePort = Tools.byteToInt(b[len - 2]) * 256 + Tools.byteToInt(b[len - 1]);
                noerror = true;
            }

            if (noerror) {
                Log.debug("remoteAddress: " + remoteAddress);
                Log.debug("remotePort: " + remotePort);
                try {
                    remoteSocket = new Socket(remoteAddress, remotePort);
                    remoteInputStream = remoteSocket.getInputStream();
                    remoteOutputStream = remoteSocket.getOutputStream();
                    String localAddress = clientSocket.getLocalAddress().getHostAddress();
                    Log.debug("localAddress: " + localAddress);
                    int localPort = clientSocket.getLocalPort();
                    String[] list = localAddress.split("\\.");
                    Log.debug("localhostAddressListLength: " + list.length);
                    /*
                    if (list.length != 4) {
                        return false;
                    }
                    */

                    byte[] reponseByte = {5, 0, 0, 1, 0,0,0,0,
                            /*
                            Tools.intTobyte(Integer.valueOf(list[0])),
                            Tools.intTobyte(Integer.valueOf(list[1])),
                            Tools.intTobyte(Integer.valueOf(list[2])),
                            Tools.intTobyte(Integer.valueOf(list[3])),*/
                            Tools.intTobyte(localPort / 256),
                            Tools.intTobyte(localPort % 256),
                    };

                    //byte[] reponseByte = {5, 0, 0, 1,0,0,0,0,16,26};
                    Log.debug("InitAddress reponseByte: " + Tools.byteToHexString(reponseByte));
                    clientOutputStream.write(reponseByte);
                } catch (IOException e) {
                    Log.warn(remoteAddress + " : "+ e.getMessage());
                    return false;
                }
                return true;
            }
            return false;
        }

        return false;
    }
    public void run() {
        recvFromClientThread = new SocksClientThread(this, SocksClientThread.RECV_FROM_CLIENT);
        recvFromRemoteThread = new SocksClientThread(this, SocksClientThread.RECV_FROM_REMOTE);
        SocksServer.This.clientThreadPool.execute(recvFromClientThread);
        SocksServer.This.clientThreadPool.execute(recvFromRemoteThread);
    }

    public void recvFromClient() {
        while (true) {
            byte[] b = new byte[50000];
            int len;
            try {
                len = clientInputStream.read(b);
                if (len <= 0) {
                    break;
                }
                b = Tools.subByte(b, 0, len);
                remoteOutputStream.write(b);
            } catch (IOException e) {
                break;
            }
        }
        close();
    }

    public void recvFromRemote() {
        while (true) {
            byte[] b = new byte[50000];
            int len;
            try {
                len = remoteInputStream.read(b);
                if (len <= 0) {
                    break;
                }
                b = Tools.subByte(b, 0, len);
                clientOutputStream.write(b);
            } catch (IOException e) {
                break;
            }
        }
        close();
    }

    @Override
    public String toString() {
        return "SocksClient("
                + "LocalAddress:" + clientSocket.getLocalSocketAddress().toString()
                + ",RemoteAddress:" + clientSocket.getRemoteSocketAddress().toString()
                + ")";
    }

    public void close() {
        if (!removed) {
            Log.info(this + " exit");
            SocksServer.This.removeSocksClient(this);
            removed = true;
        }
        try {
            if (clientSocket != null)
                clientSocket.close();
            if (remoteSocket != null)
                remoteSocket.close();
        } catch (IOException e) {
        }
    }
}
