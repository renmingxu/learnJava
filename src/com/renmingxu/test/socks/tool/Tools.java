package com.renmingxu.test.socks.tool;

/**
 * Created by renmingxu on 17-2-13.
 */
public class Tools {
    public static byte[] byteRemoveZeroTail(byte[] data) {
        int i;
        for (i = data.length - 1; data[i] == 0; i--);
        byte[] result = new byte[++i];
        System.arraycopy(data, 0, result, 0, i);
        return result;
    }
    public static byte[] subByte(byte[] data, int indexBegin, int length) {
        if (length <= 0) {
            return null;
        }
        byte[] result = new byte[length];
        System.arraycopy(data, indexBegin, result, 0, length);
        return result;
    }
    public static String byteToHexString(byte[] bytes) {
        StringBuffer sb = new StringBuffer(bytes.length);
        String sTemp;
        for (int i = 0; i < bytes.length; i++) {
            sTemp = Integer.toHexString(0xFF & bytes[i]);
            if (sTemp.length() < 2)
                sb.append(0);
            sb.append(sTemp.toUpperCase());
        }
        return sb.toString();
    }
    public static int byteToInt(byte b) {
        int i = b;
        if (i >= 0) {
            return i;
        }
        if (i < 0) {
            return 256 + i;
        }
        return 0;
    }
    public static byte intTobyte(int i) {
        if (i >=0 && i <= 127) {
            return (byte)i;
        }
        if (i >= 128 && i <= 255) {
            return (byte)(i - 256);
        }
        return 0;
    }
}
