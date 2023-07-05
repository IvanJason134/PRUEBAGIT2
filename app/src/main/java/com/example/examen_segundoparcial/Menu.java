package com.example.examen_segundoparcial;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;

/*
 * A simple {@link Fragment} subclass.
 * Use the {@link Menu#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Menu extends Fragment {

    private final int [] botones={R.id.btnagregar, R.id.btneliminar, R.id.btnmodificar, R.id.btnbuscar, R.id.btninventario};
    private final int [] botoniluminado={R.drawable.add2, R.drawable.delete2, R.drawable.edit2, R.drawable.search2, R.drawable.invent2};
    private int boton=-1;



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Menu() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
   /* public static Menu newInstance(String param1, String param2) {
        Menu fragment = new Menu();
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
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mimenu=inflater.inflate(R.layout.fragment_menu, container, false);

        if(getArguments()!=null){
            boton = getArguments().getInt("botonpulsado2");
        }

        ImageButton botonmenu;
        for(int i=0; i<botones.length; i++){

            botonmenu=(ImageButton) mimenu.findViewById(botones[i]);
            if(boton==i){
                botonmenu.setImageResource(botoniluminado[i]);
            }
            final int queboton=i;

            botonmenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Activity miactividad=getActivity();
                    ((miMenu)miactividad).menuboton(queboton);

                }
            });
        }
        return mimenu;
    }
}