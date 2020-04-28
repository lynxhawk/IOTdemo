package ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import data.receive.ReceiveIOTData;
import data.receive.ReceiveSensorData;
import data.send.SendIOTData;

import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JSlider;
import javax.swing.JLabel;

public class FrmControl extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private String lynx="LYNX";
	
	private Label heartbeatnum = new Label("null");
	private Label stuno = new Label("null");
	private Label tempnum = new Label("null");
	private Label humidityvalue = new Label("null");
	private TextField RED = new TextField(10);
	private TextField GREEN = new TextField(10);
	private TextField BLUE = new TextField(10);
	private Label IRstatus = new Label("off");
	private Label heartbeatlabel = new Label("HeartBeat");
	private Label IR = new Label("IR");
	private Label RGB = new Label("RGB");
	private Label temp = new Label("temperature");
	private Label humidity = new Label("Humidity");
	private Label motor = new Label("Motor");
	private Label lblStudentno = new Label("studentNo");
	
	private JSlider slider = new JSlider(0,100);

	static SendIOTData data = new SendIOTData();
	
	static short heartbeat = 0;
	
	public FrmControl() {
		this.setSize(600, 800);
		setTitle(lynx);
		setBounds(100, 100, 618, 462);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
	
		heartbeatlabel.setBounds(35, 10, 93, 30);			
		contentPanel.add(heartbeatlabel);
	
		heartbeatnum.setBounds(173, 10, 93, 30);
		contentPanel.add(heartbeatnum);
		
		IR.setBounds(35, 57, 93, 30);
		contentPanel.add(IR);
		
		RGB.setBounds(35, 93, 93, 30);
		contentPanel.add(RGB);
		
		temp.setBounds(35, 146, 110, 30);
		contentPanel.add(temp);
		
		humidity.setBounds(35, 197, 93, 30);
		contentPanel.add(humidity);
		
		motor.setBounds(35, 249, 93, 30);
		contentPanel.add(motor);
		
		IRstatus.setBounds(173, 57, 93, 30);
		contentPanel.add(IRstatus);
		
		RED.setText(""+data.getSensor2().getData1());
		RED.setBounds(173, 93, 87, 30);
		contentPanel.add(RED);
		
		GREEN.setText(""+data.getSensor2().getData2());
		GREEN.setBounds(275, 93, 87, 30);
		contentPanel.add(GREEN);
		
		BLUE.setText(""+data.getSensor2().getData3());
		BLUE.setBounds(368, 93, 87, 30);
		contentPanel.add(BLUE);
		
		tempnum.setText(""+data.getSensor1().getData1());
		tempnum.setBounds(173, 146, 93, 30);
		contentPanel.add(tempnum);
		
		humidityvalue.setText(""+data.getSensor1().getData2());
		humidityvalue.setBounds(173, 197, 93, 30);
		contentPanel.add(humidityvalue);
		
		slider.setValue(data.getSensor3().getData1());
		slider.setBounds(173, 253, 200, 26);
		slider.setSnapToTicks(true);
		slider.setPaintTicks(true);
		slider.setMajorTickSpacing(5);
		slider.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				// TODO Auto-generated method stub
				data.getSensor3().setData1(((JSlider)e.getSource()).getValue());
			}
	
		});
		contentPanel.add(slider);
		
		lblStudentno.setBounds(35, 302, 81, 21);
		contentPanel.add(lblStudentno);
		
		stuno.setBounds(173, 302, 81, 21);
		contentPanel.add(stuno);
		
		this.setVisible(true);
	}

	public void Disdata(ReceiveIOTData rd) {
		IRstatus.setText(rd.getFrame3().getData1()==1?"on":"off" );
		heartbeatnum.setText(""+rd.getHeartbeat());
		GREEN.setText(""+rd.getFrame2().getData1());
		RED.setText(""+rd.getFrame2().getData2());
		BLUE.setText(""+rd.getFrame2().getData3());
		humidityvalue.setText(""+rd.getFrame1().getData2());
		tempnum.setText(""+rd.getFrame1().getData1());
		stuno.setText(rd.getNUM());
	}
	
	public byte[] LoadData(ReceiveSensorData s) { //加载控制数据
		heartbeat++;
	    //data.setheartbeat(heartbeat);
		data.getSensor2().setData1(Integer.parseInt(RED.getText()));
		data.getSensor2().setData2(Integer.parseInt(GREEN.getText()));
		data.getSensor2().setData3(Integer.parseInt(BLUE.getText()));
		byte[] bytes = data.getBytes();
		return bytes;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
