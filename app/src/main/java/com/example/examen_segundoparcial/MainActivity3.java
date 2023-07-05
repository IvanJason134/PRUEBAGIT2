package com.example.examen_segundoparcial;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class MainActivity3 extends AppCompatActivity {

    //Button btnescaner;
    //EditText codigobarras;

    ImageButton left_button, agregar, buscar, actualizar, eliminar;


    EditText txtnombre, txtusuario, txtclave, txtcorreo, txttelefono, txtedad, txtdireccion, txtpuesto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        Intent intent = getIntent();
        String puesto= intent.getStringExtra("Puesto");
        String NombreUsuario = intent.getStringExtra("NombreUsuario");

        left_button=(ImageButton) findViewById(R.id.left_button);
        agregar = (ImageButton) findViewById(R.id.agregar);
        eliminar = (ImageButton) findViewById(R.id.eliminar);
        actualizar = (ImageButton) findViewById(R.id.actualizar);
        buscar = (ImageButton) findViewById(R.id.buscar);
        txtnombre = (EditText) findViewById(R.id.txtnombre);
        txtusuario = (EditText) findViewById(R.id.txtusuario);
        txtclave = (EditText) findViewById(R.id.txtclave);
        txtcorreo = (EditText) findViewById(R.id.txtcorreo);
        txttelefono = (EditText) findViewById(R.id.txttelefono);
        txtedad = (EditText) findViewById(R.id.txtedad);
        txtdireccion = (EditText) findViewById(R.id.txtdireccion);
        txtpuesto = (EditText) findViewById(R.id.txtpuesto);


        left_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sig=new Intent(MainActivity3.this,MainActivity4.class);
                sig.putExtra("Puesto", puesto);
                sig.putExtra("NombreUsuario", NombreUsuario);
                startActivity(sig);
            }
        });

        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Agregar("http://"+DireccionServidor.ipServidor+"/proyectohilos/insertarEmpleados.php");
            }
        });

        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usuario = txtusuario.getText().toString();
                Buscar("http://"+DireccionServidor.ipServidor+"/proyectohilos/buscarEmpleados.php?usuario=" + usuario);
            }
        });

        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usuario = txtusuario.getText().toString();
                Eliminar("http://"+DireccionServidor.ipServidor+"/proyectohilos/eliminarEmpleados.php?usuario=" + usuario);
            }
        });

        actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Actualizar("http://"+DireccionServidor.ipServidor+"/proyectohilos/actualizarEmpleados.php");
            }
        });
    }

    public void Agregar(String url) {
        String usuario = txtusuario.getText().toString();
        String nombre = txtnombre.getText().toString();
        String clave = txtclave.getText().toString();
        String correo = txtcorreo.getText().toString();
        String telefono = txttelefono.getText().toString();
        String edad = txtedad.getText().toString();
        String direccion = txtdireccion.getText().toString();
        String puesto = txtpuesto.getText().toString();

        if (!usuario.isEmpty() && !nombre.isEmpty() && !clave.isEmpty() && !correo.isEmpty() && !telefono.isEmpty() && !edad.isEmpty() && !direccion.isEmpty() && !puesto.isEmpty()) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String mensaje = jsonObject.getString("mensaje");
                        Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_SHORT).show();
                        txtusuario.setText("");
                        txtnombre.setText("");
                        txtclave.setText("");
                        txtcorreo.setText("");
                        txttelefono.setText("");
                        txtedad.setText("");
                        txtdireccion.setText("");
                        txtpuesto.setText("");
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), "Error al agregar empleado", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String,String> getParams(){
                    Map<String,String> params = new HashMap<String, String>();
                    params.put("Usuario", txtusuario.getText().toString());
                    params.put("Nombre", txtnombre.getText().toString());
                    params.put("Clave", txtclave.getText().toString());
                    params.put("Correo", txtcorreo.getText().toString());
                    params.put("Telefono", txttelefono.getText().toString());
                    params.put("Edad", txtedad.getText().toString());
                    params.put("Direccion", txtdireccion.getText().toString());
                    params.put("Puesto", txtpuesto.getText().toString());

                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        } else {
            Toast.makeText(MainActivity3.this, "Todos los campos tienen que estar llenados", Toast.LENGTH_SHORT).show();
        }
    }

    //Buscar empleados
    public void Buscar(String url) {
        JsonArrayRequest request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                // Si la respuesta es vacía, se notifica al usuario
                    if(response.length() == 0) {
                        Toast.makeText(MainActivity3.this, "El Usuario ingresado no existe", Toast.LENGTH_SHORT).show();
                    } else {
                        JSONObject usuario = response.getJSONObject(0);
                        String Usuario = usuario.getString("Usuario");
                        String Nombre = usuario.getString("Nombre");
                        String Clave = usuario.getString("Clave");
                        String Correo = usuario.getString("Correo");
                        String Telefono = usuario.getString("Telefono");
                        String Edad = usuario.getString("Edad");
                        String Direccion = usuario.getString("Direccion");
                        String Puesto = usuario.getString("Puesto");

                        txtusuario.setText(Usuario);
                        txtnombre.setText(Nombre);
                        txtclave.setText(Clave);
                        txtcorreo.setText(Correo);
                        txttelefono.setText(Telefono);
                        txtedad.setText(Edad);
                        txtdireccion.setText(Direccion);
                        txtpuesto.setText(Puesto);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity3.this, "Error al procesar la respuesta del servidor", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(MainActivity3.this, "Error al conectar con el servidor", Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }


    //Eliminar empleados
    public void Eliminar(String url) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String mensaje = response.getString("mensaje");
                    Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_SHORT).show();
                    txtusuario.setText("");
                    txtnombre.setText("");
                    txtclave.setText("");
                    txtcorreo.setText("");
                    txttelefono.setText("");
                    txtedad.setText("");
                    txtdireccion.setText("");
                    txtpuesto.setText("");
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "Error al eliminar empleado", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);
    }

    //Actualizar
    public void Actualizar(String url) {
        String usuario = txtusuario.getText().toString();
        String nombre = txtnombre.getText().toString();
        String clave = txtclave.getText().toString();
        String correo = txtcorreo.getText().toString();
        String telefono = txttelefono.getText().toString();
        String edad = txtedad.getText().toString();
        String direccion = txtdireccion.getText().toString();
        String puesto = txtpuesto.getText().toString();

        if (!usuario.isEmpty() && !nombre.isEmpty() && !clave.isEmpty() && !correo.isEmpty() && !telefono.isEmpty() && !edad.isEmpty() && !direccion.isEmpty() && !puesto.isEmpty()) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                String message = jsonObject.getString("message");
                                Toast.makeText(MainActivity3.this, message, Toast.LENGTH_SHORT).show();
                                txtusuario.setText("");
                                txtnombre.setText("");
                                txtclave.setText("");
                                txtcorreo.setText("");
                                txttelefono.setText("");
                                txtedad.setText("");
                                txtdireccion.setText("");
                                txtpuesto.setText("");
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(MainActivity3.this, "Error al actualizar empleado: " + e.toString(), Toast.LENGTH_LONG).show();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(MainActivity3.this, "Error al actualizar empleado: " + error.toString(), Toast.LENGTH_LONG).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("Usuario", txtusuario.getText().toString());
                    params.put("Nombre", txtnombre.getText().toString());
                    params.put("Clave", txtclave.getText().toString());
                    params.put("Correo", txtcorreo.getText().toString());
                    params.put("Telefono", txttelefono.getText().toString());
                    params.put("Edad", txtedad.getText().toString());
                    params.put("Direccion", txtdireccion.getText().toString());
                    params.put("Puesto", txtpuesto.getText().toString());
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        } else {
            Toast.makeText(MainActivity3.this, "Rellene todos los campos para poder actualizar los datos", Toast.LENGTH_SHORT).show();
        }
    }
}