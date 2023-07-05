package com.example.examen_segundoparcial;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.View;

import androidx.annotation.Nullable;

public class sqlite1  extends SQLiteOpenHelper {

    public sqlite1(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version)
    {
        super(context,"Registro", factory, version);

    }

    @Override
    public void onCreate(SQLiteDatabase basedatos) {
        basedatos.execSQL("create table empleados(Usuario text primary key, Nombre text, Clave text, Correo text, Telefono text, Edad text, Direccion text, Puesto text)");
        basedatos.execSQL("create table productos(Id text primary key, Nombre text, Cantidad int, Precio int, Marca text, Color text, Peso text, Material text)");
        basedatos.execSQL("create table ventas(id INTEGER PRIMARY KEY AUTOINCREMENT, cantidad_total int, precio_total int)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}