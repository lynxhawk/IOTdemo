package com.lynxhawk.iot.data.send;

import com.lynxhawk.iot.data.DataDeal;

public class SendDevData {
    private byte id;
    private int v1;
    private int v2;
    private int v3;
    private short VNot;

    byte[] bytes = new byte[15];

    public byte[] getBytes() {
        bytes[0] = id;
        byte[] b1 = DataDeal.intToBytes(v1);
        byte[] b2 = DataDeal.intToBytes(v2);
        byte[] b3 = DataDeal.intToBytes(v3);
        byte[] b4 = DataDeal.shortToBytes(VNot);
        for (int i = 0; i < 4; i++) {
            bytes[1+i] = b1[i];
        }
        for (int i = 0; i < 4; i++) {
            bytes[5+i] = b2[i];
        }
        for (int i = 0; i < 4; i++) {
            bytes[9+i] = b3[i];
        }
        for (int i = 0; i < 2; i++) {
            bytes[13+i] = b4[i];
        }
        return bytes;
    }

    public byte getId() {
        return id;
    }

    public void setId(byte id) {
        this.id = id;
    }

    public int getV1() {
        return v1;
    }

    public void setV1(int v1) {
        this.v1 = v1;
    }

    public int getV2() {
        return v2;
    }

    public void setV2(int v2) {
        this.v2 = v2;
    }

    public int getV3() {
        return v3;
    }

    public void setV3(int v3) {
        this.v3 = v3;
    }

    public short getVNot() {
        return VNot;
    }

    public void setVNot(short VNot) {
        this.VNot = VNot;
    }
}

