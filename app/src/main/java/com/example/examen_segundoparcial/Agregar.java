package com.example.examen_segundoparcial;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Agregar#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Agregar extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Agregar() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Agregar.
     */
    // TODO: Rename and change types and number of parameters
    public static Agregar newInstance(String param1, String param2) {
        Agregar fragment = new Agregar();
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
        View view = inflater.inflate(R.layout.fragment_agregar, container, false);
        ImageButton agregar = (ImageButton) view.findViewById(R.id.agregar);
        ImageButton btnscanner = (ImageButton) view.findViewById(R.id.btnscanner);

        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Agregar("https://arcigurumi.000webhostapp.com/agregar.php");
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

        IntentIntegrator integrator = IntentIntegrator.forSupportFragment(Agregar.this);
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

    @SuppressLint("NotConstructor")
    public void Agregar(String url){
        EditText txtid = getView().findViewById(R.id.txtid);
        EditText txtnombre = getView().findViewById(R.id.txtnombre);
        EditText txtcantidad = getView().findViewById(R.id.txtcantidad);
        EditText txtprecio = getView().findViewById(R.id.txtprecio);
        EditText txtcolor = getView().findViewById(R.id.txtcolor);

        String id = txtid.getText().toString();
        String nombre = txtnombre.getText().toString();
        int cantidad = 0;
        int precio = 0;
        String color = txtcolor.getText().toString();

        try {
            cantidad = Integer.parseInt(txtcantidad.getText().toString());
            precio = Integer.parseInt(txtprecio.getText().toString());
        } catch (NumberFormatException e) {
            Toast.makeText(getContext(), "La cantidad y el precio deben ser números enteros", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!id.isEmpty() && !nombre.isEmpty()  && !color.isEmpty() ) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String mensaje = jsonObject.getString("mensaje");
                        Toast.makeText(getContext(), mensaje, Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        Toast.makeText(getContext(), "Error al agregar empleado", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String,String> getParams(){
                    Map<String,String> params = new HashMap<String, String>();
                    params.put("Id", txtid.getText().toString());
                    params.put("Nombre", txtnombre.getText().toString());
                    params.put("Cantidad", txtcantidad.getText().toString());
                    params.put("Precio", txtprecio.getText().toString());
                    params.put("Color", txtcolor.getText().toString());
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(getContext());
            requestQueue.add(stringRequest);
        } else {
            Toast.makeText(getContext(), "Todos los campos tienen que estar llenados", Toast.LENGTH_SHORT).show();
        }
    }
}