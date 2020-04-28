package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import data.deal.datadeal;
import data.receive.ReceiveIOTData;
import data.send.SendIOTData;
import ui.FrmControl;
import ui.FrmMain;

public class Servers extends Thread{

	ServerSocket sst = null;
	
	@Override
	public void run() {
		startServer();
	}
	
	private void startServer() {
		try {
			System.out.println("Server Start!");
			sst = new ServerSocket(6800);     //°ó¶¨¶Ë¿Ú
			ExecutorService pool = Executors.newCachedThreadPool();
			while(true)
			{
				Socket s = sst.accept();
				FrmControl f= new FrmControl();
				dealSocket t = new dealSocket(s,f);
				pool.execute(t);
			}
			
		}catch(IOException e) {
			e.printStackTrace();
		}	
	}	
}

class dealSocket extends Thread{
	Socket s;
	FrmControl frmControl;
	
	InputStream is;
	DataInputStream in;
	DataOutputStream out;
	OutputStream os;
	
	//ReceiveIOTData rd;
	SendIOTData sd;
	byte[] bytes = new byte[57];
	
	byte[] byteout = new byte[54];
	
	public dealSocket(Socket s, FrmControl f)
	{
		this.s=s;
		this.frmControl = f;
	}
	@Override
	public void run() {
		deal();		
	}
	
	private void deal() {
		
		try {
			System.out.println("Server start!");
			while(true)
			{
				os = s.getOutputStream();
				out = new DataOutputStream(os);
				is = s.getInputStream();
				in = new DataInputStream(is);
                in.read(bytes);
                
                //rd = new ReceiveIOTData(bytes);
                //frmControl.Disdata(rd);
                //out.write(frmControl.LoadData(rd.getFrame2()));
                sd = new SendIOTData();
                
				out.flush();
                System.out.printf("!!!!!!!!");
                
		}
		}catch(IOException e)
		{
			e.printStackTrace();
		}catch(Exception e)
		{
			System.out.println("Wrong!");
			e.printStackTrace();											
		}
		finally {
			try {
				is.close();
				in.close();
				os.close();
				out.close();
				s.close();
				frmControl.setVisible(false);
			}catch(IOException e )
			{
				e.printStackTrace();
			}
		}
		
	
	}
	
}