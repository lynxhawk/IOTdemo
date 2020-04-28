package com.lynxhawk.iot.data;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;


public class DataDeal {
    public static String bytesToString(byte[] bytes) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (bytes == null || bytes.length <= 0){
            return null;

        }
        for (int i = 0; i < bytes.length; i++) {
            int v = bytes[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }
    public static byte[] stringToBytes(String str) {
        byte[] bytes = new byte[str.length() / 2];
        for(int i = 0; i < str.length() / 2; i++) {
            String subStr = str.substring(i * 2, i * 2 + 2);
            bytes[i] = (byte) Integer.parseInt(subStr, 16);
        }
        return bytes;
    }
    public static int bytesToInt(byte[] bytes, int i) {
        int result = 0 ;
        result = (int) ( (bytes[i] & 0xFF)
                | ((bytes[i+1] & 0xFF) << 8)
                | ((bytes[i+2] & 0xFF) << 16)
                | ((bytes[i+3] & 0xFF) << 24));
        return result;
    }
    public static byte[] intToBytes(int value) {
        byte[] src = new byte[4];
        src[3] = (byte) ( (value >> 24) & 0xFF);
        src[2] = (byte) ( (value >> 16) & 0xFF);
        src[1] = (byte) ( (value >> 8) & 0xFF);
        src[0] = (byte) ( value  & 0xFF);
        return src;
    }
    public static byte charToByte(char c) {
        Charset cs = Charset.forName("Utf-8");
        CharBuffer cb = CharBuffer.allocate(1);
        cb.put(c);
        cb.flip();
        ByteBuffer bb = cs.encode(cb);
        byte[] tmp = bb.array();
        return tmp[0];
    }
    public static byte[] shortToBytes(int value) {
        byte[] src = new byte[2];
        src[1] = (byte) ( (value >> 8) & 0xFF);
        src[0] = (byte) ( value  & 0xFF);
        return src;
    }
    public static short bytesToShort(byte[] bytes, int i) {
        short result = 0;
        result = (short) ( (bytes[i] & 0xFF)
                | ((bytes[i+1] & 0xFF) << 8) );
        return result;
    }
    public static boolean check(byte[] bytes) {
        byte[] dataBytes = new byte[56];
        byte[] crcBytes = new byte[2];
        System.arraycopy(bytes, 0, dataBytes, 0, 56);
        System.arraycopy(bytes, 56, crcBytes, 0, 2);
        String chk = getCheck(dataBytes);
        String crc = DataDeal.bytesToString(crcBytes);
        if (crc.equals(chk))
            return true;
        return false;
    }
    public static String getCheck(byte[] bytes) {

        int CRC = 0x0000FFFF;
        int POLYMIAL = 0x0000A001;

        int i, j;
        for (i = 0; i < bytes.length; i++) {
            CRC ^= ((int) bytes[i] & 0x000000FF);
            for (j = 0; j < 8; j++) {
                if ((CRC & 0x00000001) != 0) {
                    CRC >>= 1;
                    CRC ^= POLYMIAL;
                }
                else
                    CRC >>= 1;
            }
        }
        String c = Integer.toHexString(CRC);
        String c1 = c.substring(0,2);
        String c2 = c.substring(2,4);
        c = c2 + c1;
        return  c;
    }
}

