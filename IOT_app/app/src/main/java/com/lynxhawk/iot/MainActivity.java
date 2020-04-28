package com.lynxhawk.iot;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private AppCompatButton btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("deg", "main");
        btn = findViewById(R.id.btn_server);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, IOTActivity.class));
                finish();
            }
        });
        Log.d("deg", "main2");
    }
}
