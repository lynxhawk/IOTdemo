package com.lynxhawk.iot.data.send;

import com.lynxhawk.iot.data.DataDeal;

public class SendIOTData {
    private int HEADER;
    private String NUM;
    private byte sensor_num;
    private short heart_beat;
    private SendDevData frame1;
    private SendDevData frame2;
    private SendDevData frame3;
    byte[] bytes = new byte[58];
    public SendIOTData() {
        HEADER = 0xABABABAB;
        NUM = "31602377";
        sensor_num = 3;
        heart_beat = 1;
        frame1 = new SendDevData();
        frame2 = new SendDevData();
        frame3 = new SendDevData();
    }

    public byte[] getBytes() {
        int i;
        byte[] b1 = DataDeal.intToBytes(HEADER);
        for (i = 0; i < 4; i++) {
            bytes[i] = b1[i];
        }

        byte[] b2 = DataDeal.stringToBytes(NUM);
        for (i = 0; i < 4; i++) {
            bytes[i+4] = b2[i];
        }

        bytes[8] = sensor_num;

        byte[] b4 = DataDeal.shortToBytes(heart_beat);
        for (i = 0; i < 2; i++) {
            bytes[i+9] = b4[i];
        }

        byte[] b5 = frame1.getBytes();
        for (i = 0; i < 15; i++) {
            bytes[i+11] = b5[i];
        }

        byte[] b6 = frame2.getBytes();
        for (i = 0; i < 15; i++) {
            bytes[i+26] = b6[i];
        }

        byte[] b7 = frame3.getBytes();
        for (i = 0; i < 15; i++) {
            bytes[i+41] = b7[i];
        }

        byte[] dataBytes = new byte[56];
        System.arraycopy(bytes, 0, dataBytes, 0, 56);
        byte[] crcBytes =  DataDeal.stringToBytes(DataDeal.getCheck(dataBytes));
        for (i = 0; i < 2; i++) {
            bytes[i+56] = crcBytes[i];
        }

        return bytes;
    }

    public int getHEADER() {
        return HEADER;
    }

    public void setHEADER(int HEADER) {
        this.HEADER = HEADER;
    }

    public String getNUM() {
        return NUM;
    }

    public void setNUM(String NUM) {
        this.NUM = NUM;
    }

    public byte getSensor_num() {
        return sensor_num;
    }

    public void setSensor_num(byte sensor_num) {
        this.sensor_num = sensor_num;
    }

    public short getHeart_beat() {
        return heart_beat;
    }

    public void setHeart_beat(short heart_beat) {
        this.heart_beat = heart_beat;
    }

    public SendDevData getFrame1() {
        return frame1;
    }

    public void setFrame1(SendDevData frame1) {
        this.frame1 = frame1;
    }

    public SendDevData getFrame2() {
        return frame2;
    }

    public void setFrame2(SendDevData frame2) {
        this.frame2 = frame2;
    }

    public SendDevData getFrame3() {
        return frame3;
    }

    public void setFrame3(SendDevData frame3) {
        this.frame3 = frame3;
    }
}
