package com.example.examen_segundoparcial;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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


public class MainActivity6antes extends AppCompatActivity {
    ImageButton left_button, btnscanner, agregar, buscar, actualizar, eliminar;

    EditText txtid, txtnombre, txtcantidad, txtprecio, txtmarca, txtcolor, txtpeso, txtmaterial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main6);
        Intent intent = getIntent();
        String puesto= intent.getStringExtra("Puesto");

        String NombreUsuario = intent.getStringExtra("NombreUsuario");
        left_button=(ImageButton) findViewById(R.id.left_button);
        btnscanner = (ImageButton) findViewById(R.id.btnscanner);
        agregar = (ImageButton) findViewById(R.id.agregar);
        eliminar = (ImageButton) findViewById(R.id.eliminar);
        actualizar = (ImageButton) findViewById(R.id.actualizar);
        buscar = (ImageButton) findViewById(R.id.buscar);
        txtid = (EditText) findViewById(R.id.txtid);
        txtnombre = (EditText) findViewById(R.id.txtnombre);
        txtcantidad = (EditText) findViewById(R.id.txtcantidad);
        txtprecio = (EditText) findViewById(R.id.txtprecio);
        txtmarca = (EditText) findViewById(R.id.txtmarca);
        txtcolor = (EditText) findViewById(R.id.txtcolor);
        txtpeso= (EditText) findViewById(R.id.txtpeso);
        txtmaterial = (EditText) findViewById(R.id.txtmaterial);


        left_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sig=new Intent(MainActivity6antes.this,MainActivity4.class);
                sig.putExtra("Puesto", puesto);
                sig.putExtra("NombreUsuario", NombreUsuario);
                startActivity(sig);

            }
        });

        btnscanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //scanner();
            }
        });

        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Agregar("http://"+DireccionServidor.ipServidor+"/proyectohilos/insertarProductos.php");
            }
        });

        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = txtid.getText().toString();
                Buscar("http://"+DireccionServidor.ipServidor+"/proyectohilos/buscarProductos.php?id=" + id);
            }
        });

        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = txtid.getText().toString();
                Eliminar("http://"+DireccionServidor.ipServidor+"/proyectohilos/eliminarProductos.php?id=" + id);
            }
        });

        actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Actualizar("http://"+DireccionServidor.ipServidor+"/proyectohilos/actualizarProductos.php");
            }
        });
    }

    //Metodo del boton scanner
     /*void scanner() {
        IntentIntegrator integrator = new IntentIntegrator(MainActivity6.this);

        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        integrator.setPrompt("Lector");
        integrator.setCameraId(0);
        integrator.setBeepEnabled(true);
        integrator.setBarcodeImageEnabled(true);
        integrator.initiateScan();

    }*/

    // @Override
    //protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
       // IntentResult resul = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

       // if (resul != null) {
         //   if (resul.getContents() == null) {
           //     Toast.makeText(this, "Lectura Cancelada", Toast.LENGTH_LONG).show();
            //} else {
              //  txtid.setText(resul.getContents());
            //}
        //}
        //super.onActivityResult(requestCode, resultCode, data);
    //}

    //Registrar producto
    public void Agregar(String url){
        String id = txtid.getText().toString();
        String nombre = txtnombre.getText().toString();
        int cantidad = 0;
        int precio = 0;
        String marca = txtmarca.getText().toString();
        String color = txtcolor.getText().toString();
        String peso = txtpeso.getText().toString();
        String material = txtmaterial.getText().toString();

        try {
            cantidad = Integer.parseInt(txtcantidad.getText().toString());
            precio = Integer.parseInt(txtprecio.getText().toString());
        } catch (NumberFormatException e) {
            Toast.makeText(MainActivity6antes.this, "La cantidad y el precio deben ser números enteros", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!id.isEmpty() && !nombre.isEmpty() && !marca.isEmpty() && !color.isEmpty() && !peso.isEmpty() && !material.isEmpty()) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String mensaje = jsonObject.getString("mensaje");
                        Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_SHORT).show();
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
                    params.put("Id", txtid.getText().toString());
                    params.put("Nombre", txtnombre.getText().toString());
                    params.put("Cantidad", txtcantidad.getText().toString());
                    params.put("Precio", txtprecio.getText().toString());
                    params.put("Marca", txtmarca.getText().toString());
                    params.put("Color", txtcolor.getText().toString());
                    params.put("Peso", txtpeso.getText().toString());
                    params.put("Material", txtmaterial.getText().toString());
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        } else {
            Toast.makeText(MainActivity6antes.this, "Todos los campos tienen que estar llenados", Toast.LENGTH_SHORT).show();
        }
    }

    //Buscar producto
    public void Buscar(String url) {
        String id = txtid.getText().toString();

        if (!id.isEmpty()) {
            JsonArrayRequest request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    try {
                        // Si la respuesta es vacía, se notifica al usuario
                        if(response.length() == 0) {
                            Toast.makeText(MainActivity6antes.this, "El Id ingresado no existe", Toast.LENGTH_SHORT).show();
                        } else {
                            JSONObject usuario = response.getJSONObject(0);
                            String id = usuario.getString("Id");
                            String nombre = usuario.getString("Nombre");
                            String cantidad = usuario.getString("Cantidad");
                            String precio = usuario.getString("Precio");
                            String marca = usuario.getString("Marca");
                            String color = usuario.getString("Color");
                            String peso = usuario.getString("Peso");
                            String material = usuario.getString("Material");
                            txtid.setText(id);
                            txtnombre.setText(nombre);
                            txtcantidad.setText(cantidad);
                            txtprecio.setText(precio);
                            txtmarca.setText(marca);
                            txtcolor.setText(color);
                            txtpeso.setText(peso);
                            txtmaterial.setText(material);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(MainActivity6antes.this, "Error al procesar la respuesta del servidor", Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                    Toast.makeText(MainActivity6antes.this, "Error al conectar con el servidor", Toast.LENGTH_SHORT).show();
                }
            });

            RequestQueue queue = Volley.newRequestQueue(this);
            queue.add(request);
        } else {
            Toast.makeText(MainActivity6antes.this, "Introduce el id del producto", Toast.LENGTH_SHORT).show();
        }
    }

    //Eliminar
    public void Eliminar(String url) {
        String id = txtid.getText().toString();
        if (!id.isEmpty()) {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        String mensaje = response.getString("mensaje");
                        if(mensaje.equals("El Id no existe")) {
                            Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_SHORT).show();
                            txtid.setText("");
                            txtnombre.setText("");
                            txtcantidad.setText("");
                            txtprecio.setText("");
                            txtmarca.setText("");
                            txtcolor.setText("");
                            txtpeso.setText("");
                            txtmaterial.setText("");
                        }

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
        } else {
            Toast.makeText(MainActivity6antes.this, "Introduce el Id del producto para poder eliminarlo", Toast.LENGTH_SHORT).show();
        }
    }

    //Actualizar
    public void Actualizar(String url){
        String id = txtid.getText().toString();
        String nombre = txtnombre.getText().toString();
        String cantidad = txtcantidad.getText().toString();
        String precio = txtprecio.getText().toString();
        String marca = txtmarca.getText().toString();
        String color = txtcolor.getText().toString();
        String peso = txtpeso.getText().toString();
        String material = txtmaterial.getText().toString();
        if (!id.isEmpty() && !nombre.isEmpty() && !cantidad.isEmpty() && !precio.isEmpty() && !marca.isEmpty() && !color.isEmpty() && !peso.isEmpty() && !material.isEmpty()) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                String message = jsonObject.getString("message");
                                Toast.makeText(MainActivity6antes.this, message, Toast.LENGTH_SHORT).show();
                                txtid.setText("");
                                txtnombre.setText("");
                                txtcantidad.setText("");
                                txtprecio.setText("");
                                txtmarca.setText("");
                                txtcolor.setText("");
                                txtpeso.setText("");
                                txtmaterial.setText("");
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(MainActivity6antes.this, "Error al actualizar producto: " + e.toString(), Toast.LENGTH_LONG).show();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(MainActivity6antes.this, "Error al actualizar producto: " + error.toString(), Toast.LENGTH_LONG).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("Id", txtid.getText().toString());
                    params.put("Nombre", txtnombre.getText().toString());
                    params.put("Cantidad", txtcantidad.getText().toString());
                    params.put("Precio", txtprecio.getText().toString());
                    params.put("Marca", txtmarca.getText().toString());
                    params.put("Color", txtcolor.getText().toString());
                    params.put("Peso", txtpeso.getText().toString());
                    params.put("Material", txtmaterial.getText().toString());
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        } else {
            Toast.makeText(MainActivity6antes.this, "Escribe el Id para poder actualizar los datos", Toast.LENGTH_SHORT).show();
        }
    }

}
