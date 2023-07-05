package com.example.examen_segundoparcial;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainProducto extends AppCompatActivity implements miMenu {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainproductos);
    }

    @Override
    public void menuboton(int boton) {
        Intent in=new Intent(MainProducto.this, MainActivity6.class);
        in.putExtra("botonpul",boton);
        startActivity(in);
        //startActivities(new Intent[]{in});



    }
}