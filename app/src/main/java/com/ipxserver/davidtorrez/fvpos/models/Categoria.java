package com.ipxserver.davidtorrez.fvpos.models;

import java.util.ArrayList;

/**
 * Created by David-Pc on 12/06/2015.
 */
public class Categoria
{
    private String nombre;
    private String jsonArray;

    public Categoria(String nombre, String jsonArray)
    {
        this.nombre = nombre;
        this.jsonArray = jsonArray;


    }

    public String getNombre() {
        return nombre;
    }

    public ArrayList<Product> getProductos() {
        ArrayList<Product> productos = Product.fromJsonArray(jsonArray);
        return productos;
    }
}
