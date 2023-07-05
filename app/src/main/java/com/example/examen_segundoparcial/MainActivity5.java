package com.example.examen_segundoparcial;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
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

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class MainActivity5 extends AppCompatActivity {

    Button  agregar, btnRealizarVenta, btnRegistro;
    ImageButton atras;
    EditText txtid, txtcantidad;
    ImageButton btnscanner;
    TextView txtmontal, txttotal, txttotalcantidad;
    ListView listview;

    Layout listview_item;




    private List<producto> listaProductos = new ArrayList<>();
    private ArrayAdapter<producto> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);

        txtid = (EditText) findViewById(R.id.txtid);
        btnscanner = (ImageButton) findViewById(R.id.btnscanner);
        btnRegistro = (Button) findViewById(R.id.btnRegistro);

        listview=(ListView) findViewById(R.id.listview);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaProductos);
        listview.setAdapter(adapter);

        Intent intent = getIntent();
        String puesto= intent.getStringExtra("Puesto");
        String NombreUsuario = intent.getStringExtra("NombreUsuario");
        txtmontal=(TextView) findViewById(R.id.txtmontal);
        txttotalcantidad=(TextView) findViewById(R.id.txttotalcantidad);
        atras=(ImageButton)  findViewById(R.id.atras);
        agregar=(Button)  findViewById(R.id.btnAgregar);
        txtid=(EditText)  findViewById(R.id.txtid);
        txtcantidad=(EditText)  findViewById(R.id.txtcantidad);
        btnRealizarVenta=(Button) findViewById(R.id.btnRealizarVenta);


        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sig = new Intent(MainActivity5.this, MainActivity8.class);
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

        btnRealizarVenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                registrarVenta("http://"+DireccionServidor.ipServidor+"/proyectohilos/realizarVenta.php");
            }
        });

        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = txtid.getText().toString();
                agregar("http://"+DireccionServidor.ipServidor+"/proyectohilos/RecuperarDatosVenta.php?id=" + id);
            }
        });

        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sig=new Intent(MainActivity5.this,MainActivity4.class);
                sig.putExtra("Puesto", puesto);
                sig.putExtra("NombreUsuario", NombreUsuario);
                startActivity(sig);
            }
        });
    }

    /*void scanner() {
        IntentIntegrator integrator = new IntentIntegrator(MainActivity5.this);

        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        integrator.setPrompt("Lector");
        integrator.setCameraId(0);
        integrator.setBeepEnabled(true);
        integrator.setBarcodeImageEnabled(true);
        integrator.initiateScan();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult resul = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if (resul != null) {
            if (resul.getContents() == null) {
                Toast.makeText(this, "Lectura Cancelada", Toast.LENGTH_LONG).show();
            } else {
                txtid.setText(resul.getContents());
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }*/

    public int calcularcantidad (){
        int totalproductos=0;
        for (producto proc : listaProductos ){
            totalproductos+= proc.getCantidad();
        }
        return totalproductos;
    }

    public int calculartotal (){
        int totalprecio=0;
        int preciou=0;
        int cantidadproducto=0;
        for (producto proc : listaProductos ) {
            preciou=proc.getPrecio();
            cantidadproducto=proc.getCantidad();

            totalprecio += preciou*cantidadproducto;
        }
        return totalprecio;
    }

    public int calculartotalproducto(BigInteger id, int cantidad){
        int cantidadtotalproducto=0;
        int cantidadtotal=0;
        for(producto proc : listaProductos){
            if(id.equals(proc.getId())){
                cantidadtotal+=proc.getCantidad();
            }
        }
        cantidadtotalproducto=cantidadtotal+cantidad;
       return ((cantidadtotalproducto));
    }

    public void agregar(String url) {
        String idInt = txtid.getText().toString();
        BigInteger idb= new BigInteger(idInt);
        String cantidadInt = txtcantidad.getText().toString();


        if (!idInt.isEmpty() && !cantidadInt.isEmpty()) {
            JsonArrayRequest request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    try {
                        // Si la respuesta es vacía, se notifica al usuario
                        if(response.length() == 0) {
                            Toast.makeText(MainActivity5.this, "El Id ingresado no existe", Toast.LENGTH_SHORT).show();
                        } else {

                            JSONObject usuario = response.getJSONObject(0);
                            String id = usuario.getString("Id");
                            BigInteger id1 =new BigInteger(id);
                            String nombre = usuario.getString("Nombre");
                            int cantidad = usuario.getInt("Cantidad");
                            int precio = usuario.getInt("Precio");

                            calculartotalproducto( idb, Integer.parseInt(cantidadInt));

                            if (calculartotalproducto(idb , Integer.parseInt(cantidadInt)) > cantidad) {
                                Toast.makeText(MainActivity5.this, "No existe suficiente productos para su compra",Toast.LENGTH_SHORT).show();
                            }else {
                                producto productos = new producto(id1, nombre, Integer.parseInt(cantidadInt), precio);
                                listaProductos.add(productos);
                                adapter.notifyDataSetChanged();
                                Toast.makeText(MainActivity5.this, "Producto agregado", Toast.LENGTH_SHORT).show();

                                txtmontal.setText("$"+String.valueOf(calculartotal()));
                                txttotalcantidad.setText(String.valueOf(calcularcantidad()));
                                txtid.setText("");
                                txtcantidad.setText("");
                            }

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(MainActivity5.this, "Error al procesar la respuesta del servidor", Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                    Toast.makeText(MainActivity5.this, "Error al conectar con el servidor", Toast.LENGTH_SHORT).show();
                }
            });

            RequestQueue queue = Volley.newRequestQueue(this);
            queue.add(request);
        } else {
            Toast.makeText(MainActivity5.this, "Debes ingresar el ID del producto y la cantidad", Toast.LENGTH_SHORT).show();
        }
    }

    public void registrarVenta(String url) {
        String cantidad_total = String.valueOf(calcularcantidad());
        String monto_final = String.valueOf(calculartotal());

        if (!listaProductos.isEmpty()) {
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
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("cantidad_total", cantidad_total);
                    params.put("precio_total", monto_final);
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        } else {
            Toast.makeText(MainActivity5.this, "Debes agregar por lo menos 1 producto para realizar la venta", Toast.LENGTH_SHORT).show();
        }
        for (producto proc : listaProductos) {
            BigInteger id2 = proc.getId();
            String id3 = String.valueOf(id2);


            recuperarCantidad("http://"+DireccionServidor.ipServidor+"/proyectohilos/recuperarCantidad.php?id=" + id3, new CantidadCallback() {
                @Override
                public void onCantidadRecuperada(int cantidad) {
                    int cantidadrecuperada = cantidad;
                    int cantidad2 = proc.getCantidad();
                    int cantidadfinal =0;
                        cantidadfinal = cantidadrecuperada - cantidad2;
                    if (cantidadfinal == 0) {
                        eliminarProductoVenta("http://"+DireccionServidor.ipServidor+"/proyectohilos/eliminarProductos.php?id=" + id2);
                    } else {
                        actualizarCantidadProducto("http://"+DireccionServidor.ipServidor+"/proyectohilos/actualizarCantidadProducto.php", cantidadfinal, id2);
                    }
                }
            });
        }
        listview.setAdapter(null);
        listaProductos.clear();
    }

    public interface CantidadCallback {
        void onCantidadRecuperada(int cantidad);
    }

    public void recuperarCantidad(String url, CantidadCallback callback) {
        RequestQueue queue = Volley.newRequestQueue(this);
        final int[] cantidad = {0};
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    if(jsonArray.length() > 0) {
                        JSONObject producto = jsonArray.getJSONObject(0);
                        cantidad[0] = producto.getInt("Cantidad");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity5.this, "Error al procesar la respuesta del servidor", Toast.LENGTH_SHORT).show();
                }
                callback.onCantidadRecuperada(cantidad[0]);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(MainActivity5.this, "Error al conectar con el servidor", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(request);
    }

    public void eliminarProductoVenta(String url) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String mensaje = response.getString("mensaje");
                    if(mensaje.equals("El Id no existe")) {
                        Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_SHORT).show();
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
    }

    public void actualizarCantidadProducto(String url, int cantidadfinal, BigInteger id2) {
        BigInteger id = id2;
        int cantidad = cantidadfinal;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String message = jsonObject.getString("message");
                        Toast.makeText(MainActivity5.this, message, Toast.LENGTH_SHORT).show();

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(MainActivity5.this, "Error al actualizar producto: " + e.toString(), Toast.LENGTH_LONG).show();
                    }
                }},
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(MainActivity5.this, "Error al actualizar producto: " + error.toString(), Toast.LENGTH_LONG).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("Id", String.valueOf(id));
                    params.put("Cantidad", String.valueOf(cantidad));
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
    }

}
