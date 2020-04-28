package com.lynxhawk.iot;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.lynxhawk.iot.data.receive.ReceiveIOTData;
import com.lynxhawk.iot.data.send.SendIOTData;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class IOTActivity extends AppCompatActivity {

    private ReceiveIOTData receiveIOTData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iot);

        Log.d("deg", "iot");

        new Thread() {
            byte[] bytes = new byte[58];
            ServerSocket sst = null;
            @Override
            public void run() {
                startServer();
            }

            private void startServer(){
                try {
                    sst = new ServerSocket(12234);
                    Log.d("deg", "socket");
                    while (true) {
                        Log.d("deg", "true");
                        Socket s = sst.accept();
                        Log.d("deg", "socket!");
                        InputStream is = s.getInputStream();
                        Log.d("deg", "is");
                        final OutputStream os = s.getOutputStream();
                        Log.d("deg", "os");
                        DataInputStream in = new DataInputStream(is);
                        Log.d("deg", "is!");
                        final DataOutputStream out = new DataOutputStream(os);
                        Log.d("deg", "os!");
                        try {
                            Log.d("deg", "start");
                            while (true) {
                                int n;
                                Log.d("deg", "n");

                                in.read(bytes);
                                //  显示数据
                                Log.d("deg", "read");
                                receiveIOTData = new ReceiveIOTData(bytes);

                                final TextView xuehao = findViewById(R.id.xuehao);
                                final TextView red = findViewById(R.id.red);
                                final TextView green = findViewById(R.id.green);
                                final TextView blue = findViewById(R.id.blue);
                                final TextView wendu = findViewById(R.id.temp);
                                final TextView shidu = findViewById(R.id.hum);
                                final TextView crc = findViewById(R.id.showcrc);

                                Log.d("deg", receiveIOTData.getNUM());
                                xuehao.setText(receiveIOTData.getNUM());
                                //显示RGB灯
                                red.setText(receiveIOTData.getFrame1().getV1()+"");
                                green.setText(receiveIOTData.getFrame1().getV2()+"");
                                blue.setText(receiveIOTData.getFrame1().getV3()+"");
                                //显示温湿度
                                wendu.setText(receiveIOTData.getFrame2().getV1()+"");
                                shidu.setText(receiveIOTData.getFrame2().getV2()+"");

                                crc.setText(receiveIOTData.getCrc()+"");

                                //   发送控制数据
                                final SendIOTData sendIOTData = new SendIOTData();
                                EditText moto = findViewById(R.id.moto);
                                EditText r = findViewById(R.id.edit_R);
                                EditText g = findViewById(R.id.edit_G);
                                EditText b = findViewById(R.id.edit_B);
                                sendIOTData.getFrame1().setId((byte) 1);
                                sendIOTData.getFrame1().setV1( !TextUtils.isEmpty(r.getText()) ? Integer.parseInt(r.getText().toString()) : 0 );
                                sendIOTData.getFrame1().setV2( !TextUtils.isEmpty(g.getText()) ? Integer.parseInt(g.getText().toString()) : 0 );
                                sendIOTData.getFrame1().setV3( !TextUtils.isEmpty(b.getText()) ? Integer.parseInt(b.getText().toString()) : 0 );
                                sendIOTData.getFrame2().setId((byte) 2);
                                sendIOTData.getFrame2().setV1( Integer.parseInt(wendu.getText().toString()) );
                                sendIOTData.getFrame2().setV2( Integer.parseInt(shidu.getText().toString()) );
                                sendIOTData.getFrame2().setV3( 0 );
                                sendIOTData.getFrame3().setId((byte) 3);
                                sendIOTData.getFrame3().setV1( !TextUtils.isEmpty(moto.getText()) ? Integer.parseInt(moto.getText().toString()) : 0 );
                                sendIOTData.getFrame3().setV2( 0 );
                                sendIOTData.getFrame3().setV3( 0 );
                                out.write(sendIOTData.getBytes());
                                out.flush();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            try {
                                is.close();
                                in.close();
                                os.close();
                                out.close();
                                s.close();
                            } catch (IOException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                    }
                } catch (IOException e) {
                    // TODO: handle exception
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
