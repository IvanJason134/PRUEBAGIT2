package com.example.examen_segundoparcial;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
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

public class MainActivity8 extends AppCompatActivity {

    ImageButton left_button;
    TableLayout table_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main8);

        Intent intent = getIntent();
        String puesto= intent.getStringExtra("Puesto");
        String NombreUsuario = intent.getStringExtra("NombreUsuario");

        left_button = (ImageButton) findViewById(R.id.left_button);
        table_layout = (TableLayout) findViewById(R.id.table_layout);

        historialVentas("http://"+DireccionServidor.ipServidor+"/proyectohilos/historialVentas.php");

        left_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sig = new Intent(MainActivity8.this, MainActivity5.class);
                sig.putExtra("Puesto", puesto);
                sig.putExtra("NombreUsuario", NombreUsuario);
                startActivity(sig);
            }
        });
    }

    public void historialVentas(String url) {
        JsonArrayRequest request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject venta = response.getJSONObject(i);
                        String id = venta.getString("Id");
                        String cantidad_total = venta.getString("Cantidad_Total");
                        int precio_total = venta.getInt("Precio_Total");

                        TableRow row = new TableRow(MainActivity8.this);

                        TextView col1 = new TextView(MainActivity8.this);
                        col1.setText(id);
                        row.addView(col1);

                        TextView col2 = new TextView(MainActivity8.this);
                        col2.setText(cantidad_total);
                        row.addView(col2);

                        TextView col3 = new TextView(MainActivity8.this);
                        col3.setText(String.valueOf(precio_total));
                        row.addView(col3);

                        table_layout.addView(row);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity8.this, "Error al cargar los productos", Toast.LENGTH_SHORT).show();
            }
        });
        Volley.newRequestQueue(this).add(request);
    }
}