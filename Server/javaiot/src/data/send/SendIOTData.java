package data.send;

import data.deal.datadeal;

public class SendIOTData {
	private char h1='L';
	private char h2='Y';
	private char h3='N';
	private char h4='X';

	private byte num1;
	private byte num2;
	private byte num3;
	private byte num4;
	private char sensor_num = 3;
	
	SendSensorData sensor1;
	SendSensorData sensor2;
	SendSensorData sensor3;
	
	byte[] bytes = new byte[54];
	
   	public byte[] getBytes()
	{
   		bytes[0]=datadeal.CharTobyte(h1);
   		bytes[1]=datadeal.CharTobyte(h2);
   		bytes[2]=datadeal.CharTobyte(h3);
   		bytes[3]=datadeal.CharTobyte(h4);
   	    bytes[4]=num1;
   	    bytes[5]=num2;
   	    bytes[6]=num3;
   	    bytes[7]=num4;
   	    bytes[8]=datadeal.CharTobyte(sensor_num);
   	    
   	    byte[] b2 = sensor1.getBytes();
   	    byte[] b3 = sensor2.getBytes();
   	    byte[] b4 = sensor3.getBytes();
   	    
   	    for(int i=0;i<15;i++)
   	    {
   	    	bytes[9+i]=b2[i];
   	    }
   	   for(int i=0;i<15;i++)
  	    {
  	    	bytes[24+i]=b3[i];
  	    }
   	   for(int i=0;i<15;i++)
  	    {
  	    	bytes[39+i]=b4[i];
  	    }
		return bytes;

	}
   	
   	public SendIOTData()
   	{
//   		this.h1=(byte)0x4C;
//   		this.h2=(byte)0x59;
//   		this.h3=(byte)0x4E;
//   		this.h4=(byte)0x58;
   		this.num1=(byte)0x31;
   		this.num2=(byte)0x60;
   		this.num3=(byte)0x23;
   		this.num4=(byte)0x77;
   		sensor1=new SendSensorData();
   		sensor2=new SendSensorData();
   		sensor3=new SendSensorData();
   		
   	}

	public char getH1() {
		return h1;
	}

	public void setH1(char h1) {
		this.h1 = h1;
	}

	public char getH2() {
		return h2;
	}

	public void setH2(char h2) {
		this.h2 = h2;
	}

	public char getH3() {
		return h3;
	}

	public void setH3(char h3) {
		this.h3 = h3;
	}

	public char getH4() {
		return h4;
	}

	public void setH4(char h4) {
		this.h4 = h4;
	}

	public byte getNum1() {
		return num1;
	}

	public void setNum1(byte num1) {
		this.num1 = num1;
	}

	public byte getNum2() {
		return num2;
	}

	public void setNum2(byte num2) {
		this.num2 = num2;
	}

	public byte getNum3() {
		return num3;
	}

	public void setNum3(byte num3) {
		this.num3 = num3;
	}

	public byte getNum4() {
		return num4;
	}

	public void setNum4(byte num4) {
		this.num4 = num4;
	}

	public char getSensor_num() {
		return sensor_num;
	}

	public void setSensor_num(char sensor_num) {
		this.sensor_num = sensor_num;
	}

	public SendSensorData getSensor1() {
		return sensor1;
	}

	public void setSensor1(SendSensorData sensor1) {
		this.sensor1 = sensor1;
	}

	public SendSensorData getSensor2() {
		return sensor2;
	}

	public void setSensor2(SendSensorData sensor2) {
		this.sensor2 = sensor2;
	}

	public SendSensorData getSensor3() {
		return sensor3;
	}

	public void setSensor3(SendSensorData sensor3) {
		this.sensor3 = sensor3;
	}

	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}

	
}

