package start;

import server.Servers;
import ui.FrmControl;
import ui.FrmMain;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub 
//		    FrmControl f= new FrmControl();
            Servers s = new Servers();
            s.start();
        
	}

}
