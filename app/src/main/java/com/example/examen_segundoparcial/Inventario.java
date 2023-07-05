package com.example.examen_segundoparcial;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Inventario#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Inventario extends Fragment {



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Inventario() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Inventario.
     */
    // TODO: Rename and change types and number of parameters
    public static Inventario newInstance(String param1, String param2) {
        Inventario fragment = new Inventario();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    TableLayout table_layout;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inventario, container, false);
        table_layout = (TableLayout) view.findViewById(R.id.table_layout);
        // Inflate the layout for this fragment

        inventario("https://arcigurumi.000webhostapp.com/inventarioProductos.php");

        return view;
    }

    public void inventario(String url){
        JsonArrayRequest request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        // Obtener los datos del producto del JSON y agregarlos a la tabla
                        JSONObject producto = response.getJSONObject(i);
                        String id = producto.getString("Id");
                        String nombre = producto.getString("Nombre");
                        int cantidad = producto.getInt("Cantidad");
                        int precio = producto.getInt("Precio");
                        String color = producto.getString("Color");
                        TableRow row = new TableRow(getActivity()); // Usar getActivity() para obtener la actividad que contiene el fragmento

                        // Crear TextViews y configurar sus valores
                        // Agregar los TextViews a la fila y la fila a la tabla
                        TextView col1 = new TextView(getActivity());
                        col1.setText(id);
                        row.addView(col1);

                        TextView col2 = new TextView(getActivity());
                        col2.setText(nombre);
                        row.addView(col2);

                        TextView col3 = new TextView(getActivity());
                        col3.setText(String.valueOf(cantidad));
                        row.addView(col3);

                        TextView col4 = new TextView(getActivity());
                        col4.setText(String.valueOf(precio));
                        row.addView(col4);

                        TextView col6 = new TextView(getActivity());
                        col6.setText(color);
                        row.addView(col6);

                        table_layout.addView(row);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Error al cargar los productos", Toast.LENGTH_SHORT).show();
            }
        });
        Volley.newRequestQueue(getActivity()).add(request); // Usar getActivity() para obtener la actividad que contiene el fragmento
    }
}