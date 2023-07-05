package com.example.examen_segundoparcial;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity4 extends AppCompatActivity {


    Button siguiente, atras;
    TextView empleado,ventas, productos, inventario, holausuario;
    ImageButton left_button;

    View layout, layout2;
    ImageView background2, background3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        layout= (View) findViewById(R.id.layout);
        background2= (ImageView) findViewById(R.id.background2);
        empleado= (TextView) findViewById(R.id.empleado);

        layout2 = (View) findViewById(R.id.layout2);
        background3 = (ImageView)findViewById(R.id.background3);
        productos= (TextView) findViewById(R.id.productos);

        ventas= (TextView) findViewById(R.id.ventas);
        holausuario= (TextView) findViewById(R.id.holausuario);
        inventario= (TextView) findViewById(R.id.inventario);

        left_button=(ImageButton) findViewById(R.id.left_button);
        Intent intent = getIntent();
        String puesto= intent.getStringExtra("Puesto");

        String NombreUsuario = intent.getStringExtra("NombreUsuario");
        holausuario.setText(NombreUsuario);

        if(puesto.equals("Empleado")){
            empleado.setVisibility(View.INVISIBLE);
            productos.setVisibility(View.INVISIBLE);
            layout2.setVisibility(View.INVISIBLE);
            layout.setVisibility(View.INVISIBLE);
            background2.setVisibility(View.INVISIBLE);
            background3.setVisibility(View.INVISIBLE);
        }

        left_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sig=new Intent(MainActivity4.this,MainActivity2.class);
                startActivity(sig);

            }
        });

        empleado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sig = new Intent(MainActivity4.this, MainActivity3.class);
                sig.putExtra("Puesto", puesto);
                sig.putExtra("NombreUsuario", NombreUsuario);
                startActivity(sig);
            }
        });

        ventas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sig = new Intent(MainActivity4.this, MainActivity5.class);
                sig.putExtra("Puesto", puesto);
                sig.putExtra("NombreUsuario", NombreUsuario);
                startActivity(sig);
            }
        });

        productos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sig = new Intent(MainActivity4.this, MainActivity6.class);
                sig.putExtra("Puesto", puesto);
                sig.putExtra("NombreUsuario", NombreUsuario);
                startActivity(sig);
            }
        });


        inventario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sig = new Intent(MainActivity4.this, MainActivity7.class);
                sig.putExtra("Puesto", puesto);
                sig.putExtra("NombreUsuario", NombreUsuario);
                startActivity(sig);
            }
        });




    }

}