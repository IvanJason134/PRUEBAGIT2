package com.example.examen_segundoparcial;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.hardware.SensorManager;
import android.hardware.camera2.CameraAccessException;
import android.media.Image;
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


public class MainActivity6 extends AppCompatActivity implements miMenu{
    Fragment[] misFragmentos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main6);

        misFragmentos = new Fragment[5];
        misFragmentos[0]=new Agregar();
        misFragmentos[1]=new Eliminar();
        misFragmentos[2]=new Modificar();
        misFragmentos[3]=new Consultar();
        misFragmentos[4]=new Inventario();
        int corrida = 0;

        Bundle extra=getIntent().getExtras();
        menuboton(extra.getInt("botonpul"));
    }

    public void menuboton(int boton) {

        FragmentManager mimanejador=getSupportFragmentManager();
        FragmentTransaction mitransision=mimanejador.beginTransaction();

        Fragment menuIluminado=new Menu();
        Bundle datos=new Bundle();
        datos.putInt("botonpulsado2",boton);
        menuIluminado.setArguments(datos);
        mitransision.replace(R.id.menu,menuIluminado);

        mitransision.replace(R.id.contenedor, misFragmentos[boton]);
        mitransision.commit();
    }

}
