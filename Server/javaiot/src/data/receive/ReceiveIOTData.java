package data.receive;

import data.deal.datadeal;

public class ReceiveIOTData {
	
	private char h1;
	private char h2;
	private char h3;
	private char h4;
//	private char num1;
//	private char num2;
//	private char num3;
//	private char num4;
	private String NUM;
	private char deviceid;
	private char devicenum;
	private short heartbeat;
	private ReceiveSensorData frame1;
	private ReceiveSensorData frame2;
	private ReceiveSensorData frame3;
	
	public ReceiveIOTData(byte[] bytes) throws Exception{
		 //4C 59 4E 58
//		 if(bytes[0] == (byte)0x4C && bytes[1]==(byte)0x59
//				 &&bytes[2]==(byte)0x4E && bytes[3]==(byte)0x58)
//		 {
//			 if(datadeal.check(bytes)) {
//				 System.out.println("Data Right");
//			 }
//			 else {
//				 throw new Exception("Start Flag Wrong!");
//			 }
//		 }
//		 else {
//			 System.out.println(datadeal.bytesToString(bytes));
//			 throw new Exception("Start Flag Wrong!");
//		 }
		 
		 //lynx
		 byte[] lynx = new byte[4];
         System.arraycopy(bytes, 0, lynx, 0, 4);
         //System.out.println(datadeal.bytesToString(lynx));
         System.out.println(new String(lynx));
         
         //31602377
         byte[] stu = new byte[4];
         System.arraycopy(bytes, 4, stu, 0, 4);
         NUM = datadeal.bytesToString(stu);
         System.out.println(NUM);
         
         //deviceid
         //byte[] bytes3 = new byte[2];
         deviceid = (char)bytes[8];
         //System.arraycopy(bytes, 12, bytes3, 0, 2);
         System.out.println(deviceid);
         
         //devicenum
         devicenum = (char) bytes[9];
         System.out.println(devicenum);
         
         //heartbeat
         heartbeat = datadeal.bytesToShort(bytes, 10);
         System.out.println(heartbeat);
         
         byte[] data1 = new byte[15];
         byte[] data2 = new byte[15];
         byte[] data3 = new byte[15];
         
         for(int i=0;i<15;i++)
         {
        	 data1[i] = bytes[12+i];
        	 data2[i] = bytes[27+i];
        	 data3[i] = bytes[42+i];
         }
         
         frame1 = new ReceiveSensorData(data1);
         frame2 = new ReceiveSensorData(data2);
         frame3 = new ReceiveSensorData(data3);
        
         System.out.println("R:"+frame2.getData1()+"G:"+frame2.getData2()+"B:"+frame2.getData3());
  
	}
	
	public ReceiveIOTData() {
		// TODO Auto-generated constructor stub
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




	public char getDevicenum() {
		return devicenum;
	}



	public void setDevicenum(char devicenum) {
		this.devicenum = devicenum;
	}



	public short getHeartbeat() {
		return heartbeat;
	}



	public void setHeartbeat(short heartbeat) {
		this.heartbeat = heartbeat;
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

	public String getNUM() {
		return NUM;
	}

	public void setNUM(String nUM) {
		NUM = nUM;
	}

	public char getDeviceid() {
		return deviceid;
	}

	public void setDeviceid(char deviceid) {
		this.deviceid = deviceid;
	}



}
