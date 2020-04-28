package com.lynxhawk.iot.data.receive;

import android.provider.ContactsContract;

import com.lynxhawk.iot.data.DataDeal;

public class ReceiveIOTData {
    private String NUM; // 学号

    private char sensor_num;// 传感器数量
    private short heart_beat;// 心跳

    // 传感器数据帧
    private ReceiveSensorData frame1;
    private ReceiveSensorData frame2;
    private ReceiveSensorData frame3;

    private String crc;

    public String getCrc() {
        return crc;
    }

    public void setCrc(String crc) {
        this.crc = crc;
    }

    public ReceiveIOTData(byte[] bytes) throws Exception {
        if (bytes[0] == (byte)0xAB && bytes[1] == (byte)0xAB
                && bytes[2] == (byte)0xAB && bytes[3] == (byte)0xAB) {

            if (DataDeal.check(bytes)) {
                System.out.println("Data Right");
            }
            else {
                throw new Exception("Data wrong");
            }
//            System.out.println("Data Right");
        }
        else {
            System.out.println(DataDeal.bytesToString(bytes));
            throw new Exception("Start Wrong!!!!");
        }
        //  学号 4个字节
        byte[] bs = new byte[4];
        System.arraycopy(bytes, 4, bs, 0, 4);// arraycopy的作用是在bytes数组中从索引为4开始复制到bs数组索引为0的位置，复制的元素个数为4
        NUM = DataDeal.bytesToString(bs);
        //  传感器数量 1个字节
        sensor_num = (char) bytes[8];
        //  心跳 2个字节
        heart_beat = DataDeal.bytesToShort(bytes, 9);

        byte[] data1 = new byte[15];
        byte[] data2 = new byte[15];
        byte[] data3 = new byte[15];
        for (int i = 0; i < 15; i++) {
            data1[i] = bytes[11+i];
            data2[i] = bytes[26+i];
            data3[i] = bytes[41+i];
        }

        byte[] crcbyte = new byte[2];
        System.arraycopy(bytes, 56, crcbyte, 0, 2);
        crc = DataDeal.bytesToString(crcbyte);

        frame1 = new ReceiveSensorData(data1);
        frame2 = new ReceiveSensorData(data2);
        frame3 = new ReceiveSensorData(data3);

    }

    public String getNUM() {
        return NUM;
    }

    public void setNUM(String NUM) {
        this.NUM = NUM;
    }

    public char getSensor_num() {
        return sensor_num;
    }

    public void setSensor_num(char sensor_num) {
        this.sensor_num = sensor_num;
    }

    public short getHeart_beat() {
        return heart_beat;
    }

    public void setHeart_beat(short heart_beat) {
        this.heart_beat = heart_beat;
    }

    public ReceiveSensorData getFrame1() {
        return frame1;
    }

    public void setFrame1(ReceiveSensorData frame1) {
        this.frame1 = frame1;
    }

    public ReceiveSensorData getFrame2() {
        return frame2;
    }

    public void setFrame2(ReceiveSensorData frame2) {
        this.frame2 = frame2;
    }

    public ReceiveSensorData getFrame3() {
        return frame3;
    }

    public void setFrame3(ReceiveSensorData frame3) {
        this.frame3 = frame3;
    }
}
