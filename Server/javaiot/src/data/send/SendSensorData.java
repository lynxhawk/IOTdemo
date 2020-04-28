package data.send;

import data.deal.datadeal;

public class SendSensorData {
	  private char sensorid;
      private int data1;
      private int data2;
      private int data3;
      private short VNot;
      
      byte[] bytes = new byte[15];
      
  	public byte[] getBytes()
  	{
  	    bytes[0] = datadeal.CharTobyte(sensorid);
  	    byte[] b1 = datadeal.intToByte(data1);
  	    byte[] b2 = datadeal.intToByte(data2);
  	    byte[] b3 = datadeal.intToByte(data3);
  		byte[] b4 = datadeal.ShortTobytes(VNot);
  	    
  	    for(int i = 0;i<4;i++)
  	    {
  	    	bytes[i+1]=b1[i];
  	    }
  	    for(int i = 0;i<4;i++)
	    {
	    	bytes[i+5]=b2[i];
	    }
  	    for(int i = 0;i<4;i++)
	    {
	    	bytes[i+9]=b3[i];
	    }
  	    for(int i = 0; i<2;i++)
  	    {
  	    	bytes[i+13]=b4[i];
  	    }
  	 
	return bytes;
  	}
      
	public char getSensorid() {
		return sensorid;
	}
	public void setSensorid(char sensorid) {
		this.sensorid = sensorid;
	}
	public int getData3() {
		return data3;
	}
	public void setData3(int data3) {
		this.data3 = data3;
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

	public short getVNot() {
		return VNot;
	}

	public void setVNot(short vNot) {
		VNot = vNot;
	}

	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}
    
}
