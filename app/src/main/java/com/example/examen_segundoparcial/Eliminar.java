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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Eliminar#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Eliminar extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Eliminar() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Eliminar.
     */
    // TODO: Rename and change types and number of parameters
    public static Eliminar newInstance(String param1, String param2) {
        Eliminar fragment = new Eliminar();
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
        View view = inflater.inflate(R.layout.fragment_eliminar, container, false);
        ImageButton eliminar = (ImageButton) view.findViewById(R.id.eliminar);
        ImageButton btnscanner = (ImageButton) view.findViewById(R.id.btnscanner);

        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText txtid = getView().findViewById(R.id.txtid);
                String id = txtid.getText().toString();
                Eliminar("https://arcigurumi.000webhostapp.com/eliminar.php?id=" + id);
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

        IntentIntegrator integrator = IntentIntegrator.forSupportFragment(Eliminar.this);
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
    public void Eliminar(String url) {
        EditText txtid2 = getView().findViewById(R.id.txtid);
        String id = txtid2.getText().toString();
        if (!id.isEmpty()) {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        String mensaje = response.getString("mensaje");
                        if(mensaje.equals("El Id no existe")) {
                            Toast.makeText(getContext(), mensaje, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), mensaje, Toast.LENGTH_SHORT).show();
                            txtid2.setText("");
                        }

                    } catch (JSONException e) {
                        Toast.makeText(getContext(), "Error al eliminar empleado", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
                }
            });
            RequestQueue requestQueue = Volley.newRequestQueue(getContext());
            requestQueue.add(jsonObjectRequest);
        } else {
            Toast.makeText(getContext(), "Introduce el Id del producto para poder eliminarlo", Toast.LENGTH_SHORT).show();
        }
    }
}