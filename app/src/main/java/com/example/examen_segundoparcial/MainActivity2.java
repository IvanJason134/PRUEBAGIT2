package com.example.examen_segundoparcial;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class MainActivity2 extends AppCompatActivity {

    EditText txtusuario;
    EditText txtclave;
    Button btnIngresar, btnregistrar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        txtusuario= (EditText) findViewById(R.id.txtusuario);
        btnIngresar= (Button) findViewById(R.id.btningresar);
        txtclave= (EditText) findViewById(R.id.txtclave);

        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                IniciarSesion("http://"+DireccionServidor.ipServidor+"/proyectohilos/IniciarSesion.php");

            }
        });
    }

    public void IniciarSesion(String url) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if (success) {
                                String puesto = jsonObject.getString("puesto").toString();
                                String usuario = jsonObject.getString("usuario").toString();
                                Toast.makeText(MainActivity2.this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(MainActivity2.this, MainActivity4.class);
                                intent.putExtra("Puesto", puesto);
                                intent.putExtra("NombreUsuario", usuario);
                                startActivity(intent);
                            } else {
                                Toast.makeText(MainActivity2.this, "Usuario y/o contraseña inválidos", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(MainActivity2.this, "Error en la respuesta del servidor", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(MainActivity2.this, "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("usuario", txtusuario.getText().toString().trim());
                params.put("contrasena", txtclave.getText().toString().trim());
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}