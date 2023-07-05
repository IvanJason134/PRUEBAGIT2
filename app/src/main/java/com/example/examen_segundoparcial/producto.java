package com.example.examen_segundoparcial;

import java.math.BigInteger;

public class producto {
    private BigInteger id;
    private String nombre;
    private int cantidad;
    private int precio;

    public producto(BigInteger id, String nombre, int cantidad, int precio) {
        this.id = id;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.precio = precio;
    }

    public BigInteger getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public int getCantidad() {
        return cantidad;
    }

    public int getPrecio() {
        return precio;
    }


    @Override
    public String toString() {
        String respuesta="";
        respuesta += "|ID: " +id + " |Nombre: " +nombre + " |Cantidad: " +cantidad + "  |Precio: $" +precio + "|" ;
        respuesta += "\n -------------------------------------------------";
        return respuesta;

    }

}
