package com.example.examen_segundoparcial;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Consultar#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Consultar extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Consultar() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Consultar.
     */
    // TODO: Rename and change types and number of parameters
    public static Consultar newInstance(String param1, String param2) {
        Consultar fragment = new Consultar();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

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

        View view = inflater.inflate(R.layout.fragment_consultar, container, false);
        ImageButton consultar = (ImageButton) view.findViewById(R.id.consultar);
        ImageButton btnscanner = (ImageButton) view.findViewById(R.id.btnscanner);

        consultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText txtid = getView().findViewById(R.id.txtid);
                String id = txtid.getText().toString();
                Buscar("https://arcigurumi.000webhostapp.com/buscar.php?id=" + id);
            }
        });

        btnscanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                scanner();
            }
        });

        return view;
    }

    void scanner() {

        IntentIntegrator integrator = IntentIntegrator.forSupportFragment(Consultar.this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        integrator.setPrompt("Lector");
        integrator.setCameraId(0);
        integrator.setBeepEnabled(true);
        integrator.setBarcodeImageEnabled(true);
        integrator.initiateScan();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        EditText txtid = getView().findViewById(R.id.txtid);
        super.onActivityResult(requestCode, resultCode, data);

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(getActivity(), "Lectura Cancelada", Toast.LENGTH_LONG).show();
            } else {
                txtid.setText(result.getContents());
            }
        }
    }

    public void Buscar(String url) {
        EditText txtid = getView().findViewById(R.id.txtid);
        EditText txtnombre = getView().findViewById(R.id.txtnombre);
        EditText txtcantidad = getView().findViewById(R.id.txtcantidad);
        EditText txtprecio = getView().findViewById(R.id.txtprecio);
        EditText txtcolor = getView().findViewById(R.id.txtcolor);
        EditText txtid2 = getView().findViewById(R.id.txtid);
        String id2 = txtid2.getText().toString();

        if (!id2.isEmpty()) {
            JsonArrayRequest request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    try {
                        // Si la respuesta es vacía, se notifica al usuario
                        if(response.length() == 0) {
                            Toast.makeText(getContext(), "El Id ingresado no existe", Toast.LENGTH_SHORT).show();
                        } else {
                            JSONObject usuario = response.getJSONObject(0);
                            String id = usuario.getString("Id");
                            String nombre = usuario.getString("Nombre");
                            String cantidad = usuario.getString("Cantidad");
                            String precio = usuario.getString("Precio");
                            String color = usuario.getString("Color");
                            txtid.setText(id);
                            txtnombre.setText(nombre);
                            txtcantidad.setText(cantidad);
                            txtprecio.setText(precio);
                            txtcolor.setText(color);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getContext(), "Error al procesar la respuesta del servidor", Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                    Toast.makeText(getContext(), "Error al conectar con el servidor", Toast.LENGTH_SHORT).show();
                }
            });

            RequestQueue queue = Volley.newRequestQueue(getContext());
            queue.add(request);
        } else {
            Toast.makeText(getContext(), "Introduce el id del producto", Toast.LENGTH_SHORT).show();
        }
    }
}