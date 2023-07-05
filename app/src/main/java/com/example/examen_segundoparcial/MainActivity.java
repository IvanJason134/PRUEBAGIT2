package com.example.examen_segundoparcial;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //E0C7DC,C0A3CF,58577B,DC22DC,FCECFC,721272,746C8F,C2ADCB,841484
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, MainProducto.class);
                startActivity(intent);
                finish();
            }
        }, 5000);
    }
}