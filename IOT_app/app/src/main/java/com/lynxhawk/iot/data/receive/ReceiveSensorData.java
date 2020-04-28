package com.lynxhawk.iot.data.receive;

import com.lynxhawk.iot.data.DataDeal;

public class ReceiveSensorData {
    private char id;
    private int v1;
    private int v2;
    private int v3;
    private short VNot;

    public ReceiveSensorData(byte[] bytes) {
        id = (char) bytes[0];
        v1 = DataDeal.bytesToInt(bytes, 1);
        v2 = DataDeal.bytesToInt(bytes, 5);
        v3 = DataDeal.bytesToInt(bytes, 9);
        VNot = DataDeal.bytesToShort(bytes, 13);
    }

    public char getId() {
        return id;
    }

    public void setId(char id) {
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

