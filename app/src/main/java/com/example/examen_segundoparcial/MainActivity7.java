package com.example.examen_segundoparcial;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity7 extends AppCompatActivity {

    ImageButton left_button;
    TableLayout table_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main7);

        Intent intent = getIntent();
        String puesto= intent.getStringExtra("Puesto");
        String NombreUsuario = intent.getStringExtra("NombreUsuario");

        left_button = (ImageButton) findViewById(R.id.left_button);
        table_layout = (TableLayout) findViewById(R.id.table_layout);

        inventario("http://"+DireccionServidor.ipServidor+"/proyectohilos/inventarioProductos.php");

        left_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sig = new Intent(MainActivity7.this, MainActivity4.class);
                sig.putExtra("Puesto", puesto);
                sig.putExtra("NombreUsuario", NombreUsuario);
                startActivity(sig);
            }
        });
    }

    public void inventario(String url){
        JsonArrayRequest request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject producto = response.getJSONObject(i);
                        String id = producto.getString("Id");
                        String nombre = producto.getString("Nombre");
                        int cantidad = producto.getInt("Cantidad");
                        int precio = producto.getInt("Precio");
                        String marca = producto.getString("Marca");
                        String color = producto.getString("Color");
                        String peso = producto.getString("Peso");
                        String material = producto.getString("Material");
                        TableRow row = new TableRow(MainActivity7.this);

                        TextView col1 = new TextView(MainActivity7.this);
                        col1.setText(id);
                        row.addView(col1);

                        TextView col2 = new TextView(MainActivity7.this);
                        col2.setText(nombre);
                        row.addView(col2);

                        TextView col3 = new TextView(MainActivity7.this);
                        col3.setText(String.valueOf(cantidad));
                        row.addView(col3);

                        TextView col4 = new TextView(MainActivity7.this);
                        col4.setText(String.valueOf(precio));
                        row.addView(col4);

                        TextView col5 = new TextView(MainActivity7.this);
                        col5.setText(marca);
                        row.addView(col5);

                        TextView col6 = new TextView(MainActivity7.this);
                        col6.setText(color);
                        row.addView(col6);

                        TextView col7 = new TextView(MainActivity7.this);
                        col7.setText(peso);
                        row.addView(col7);

                        TextView col8 = new TextView(MainActivity7.this);
                        col8.setText(material);
                        row.addView(col8);

                        table_layout.addView(row);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity7.this, "Error al cargar los productos", Toast.LENGTH_SHORT).show();
            }
        });
        Volley.newRequestQueue(this).add(request);
    }
}
