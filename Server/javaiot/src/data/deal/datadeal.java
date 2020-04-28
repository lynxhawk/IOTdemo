package data.deal;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;

public class datadeal { 
	   
	    public static byte[] stringToByte(String str) {  
	        //return str.getBytes("GBK");
	    	return str.getBytes();
	    }  
	       
	    public static String bytesToString(byte[] bytes) 
	    {
	    	StringBuilder stringBuilder = new StringBuilder("");
	    	if(bytes==null||bytes.length<=0)
	    	{
	    		return null;
	    	}
	        for(int i = 0;i<bytes.length;i++)
	        {
	        	int v = bytes[i]&0xFF;
	        	String hv = Integer.toHexString(v);// new String(bytes,"GBK");
	        	if(hv.length()<2)
	        	{
	        		stringBuilder.append(0);
	        	}
	        	stringBuilder.append(hv);
	        }
	    	return stringBuilder.toString();
	    }
 
	 public static int bytesToInt(byte[] bytes,int i)
	 {
		 int result=0;
		 result = (int)((bytes[i] & 0xFF)
				 | ((bytes[i+1] & 0xFF)<<8)
				 | ((bytes[i+2] & 0xFF)<<16)
				 | ((bytes[i+3] & 0xFF)<<24));
		 return result;
	 }
	 	
	 public static byte[] IntTobytes(int value)
	 {
		 byte[] src = new byte[4];
		 src[3]=(byte) ((value>>24) & 0xFF);
		 src[2]=(byte) ((value>>16) & 0xFF);
		 src[1]=(byte) ((value>>8) & 0xFF);
		 src[0]=(byte) (value & 0xFF);
		 return src;
	 }
	 
	 public static byte CharTobyte(char c)
	 {
		 Charset cs = Charset.forName("UTF-8");
		 CharBuffer cb = CharBuffer.allocate(1);
		 cb.put(c);
		 cb.flip();
		 ByteBuffer bb = cs.encode(cb);
		 
		 byte[] tmp = bb.array();
		 return tmp[0];
	 }
	 
	 public static byte[] ShortTobytes(int value)
	 {
		 byte[] src = new byte[2];
		 src[1] = (byte) ((value>>8)&0xFF);
		 src[0] = (byte) (value&0xFF);
		 return src;
	 }
	 
	 public static short bytesToShort(byte[] bytes,int i)
	 {
		 short result = 0;
		 result = (short)((bytes[i]&0xFF)
		 | ((bytes[i+1] & 0xFF)<<8));
		 
		 return result;
	 }

	  public static byte[] intToByte(int number) {  
	        int temp = number;  
	        byte[] b = new byte[4];  
	        for (int i = 0; i < b.length; i++) {  
	            b[i] = new Integer(temp & 0xff).byteValue();// 将最低位保存在最低位  
	            temp = temp >> 8;// 向右移8位  
	        }  
	        return b;  
	    }  
	  
	  public static int byteToInt(byte[] b) {  
//	        int s = 0;  
//	        int s0 = b[0] & 0xff;// 最低位  
//	        int s1 = b[1] & 0xff;  
//	        int s2 = b[2] & 0xff;  
//	        int s3 = b[3] & 0xff;  
//	        s3 <<= 24;  
//	        s2 <<= 16;  
//	        s1 <<= 8;  
//	        s = s0 | s1 | s2 | s3;  
//	        return s;  
//	        
	    		return (0xff & b[0]) | (0xff00 & (b[1] << 8)) | (0xff0000 & (b[2] << 16))
	    				| (0xff000000 & (b[3] << 24));
	    	}

	  public static boolean check(byte[] bytes)
	  {
		  int n;
		  byte chk = 0;
		  for(n=0;n<bytes.length-2;n++)
		  {
			  chk+=bytes[n];
		  }
		  if(chk==bytes[bytes.length-1])
			  return true;
		  else 
			  return false;
	  }

	  
//	 public static byte[] intToByteArray(int i) {  
//	    byte[] result = new byte[4];  
//	    // 由高位到低位  
//	    result[0] = (byte) ((i >> 24) & 0xFF);  
//	    result[1] = (byte) ((i >> 16) & 0xFF);  
//	    result[2] = (byte) ((i >> 8) & 0xFF);  
//	    result[3] = (byte) (i & 0xFF);  
//	    return result;  
//	}
	
	
//	  public static short byteToShort(byte[] b) { 
//	        short s = 0; 
//	        short s0 = (short) (b[0] & 0xff);// 最低位 
//	        short s1 = (short) (b[1] & 0xff); 
//	        s1 <<= 8; 
//	        s = (short) (s0 | s1); 
//	        return s; 
//	    }	  
//	   public static char byteToChar(byte[] b)
//	{
//        char c = (char) (((b[0] & 0xFF) << 8) | (b[1] & 0xFF));
//        return c;
//    }
	
//	  public static byte charToByte(char c)
//	  {
//		  byte b = (byte)c;
//		  return b;
//	  }
//	  
//	  public static char byteToChar(byte b)
//	  {
//		  char ch = (char) b;
//		  return ch;
//	  }     
	
}
