package data.receive;

import data.deal.datadeal;

public class ReceiveSensorData {

	private char id;
	private int data1;
	private int data2;
	private int data3;
	private short VNot;

	public ReceiveSensorData (byte[] bytes)
	{
		id = (char)bytes[0];
		data1 = datadeal.bytesToInt(bytes,1);
		data2 = datadeal.bytesToInt(bytes, 5);
		data3 = datadeal.bytesToInt(bytes, 9);
		VNot = datadeal.bytesToShort(bytes, 13);
	}
	
	
	public char getId() {
		return id;
	}



	public void setId(char id) {
		this.id = id;
	}



	public int getData1() {
		return data1;
	}



	public void setData1(int data1) {
		this.data1 = data1;
	}



	public int getData2() {
		return data2;
	}



	public void setData2(int data2) {
		this.data2 = data2;
	}



	public int getData3() {
		return data3;
	}



	public void setData3(int data3) {
		this.data3 = data3;
	}



	public short getVNot() {
		return VNot;
	}



	public void setVNot(short vNot) {
		VNot = vNot;
	}

}
