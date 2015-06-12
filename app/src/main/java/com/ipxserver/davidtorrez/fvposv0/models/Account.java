package com.ipxserver.davidtorrez.fvposv0.models;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by David-Pc on 07/06/2015.
 */
public class Account
{
    private String TAG="David";
    private String branch,first_name,last_name;
    //TODO: tratar productos XD ver logica de envio
    private String productos;
    private ArrayList<Categoria> categorias;

    public Account(String jsonText)
    {
        try {
            JSONObject json = new JSONObject(jsonText);
            if(json.has("branch"))
            {
                setBranch(json.getString("branch"));
            }
            if(json.has("first_name"))
            {
                setFirst_name(json.getString("first_name"));
            }
            if(json.has("last_name"))
            {
                setLast_name(json.getString("last_name"));
            }
            if(json.has("productos"))
            {
//                JSONArray jsonArrayProductos = new JSONArray(json.getString("productos"));
                JSONObject jsonProducto = new JSONObject(json.getString("productos"));
                JSONArray jsonArrayCategorias = new JSONArray(json.getString("categorias"));
                categorias = new ArrayList<Categoria>();
                for(int i=0;i<jsonArrayCategorias.length();i++)
                {
                     JSONObject jsonObject = jsonArrayCategorias.getJSONObject(i);
                     String nombreCategoria = jsonObject.getString("categoria");
                     String arrayProduto=null;
                     if(jsonProducto.has(nombreCategoria))
                     {
                         arrayProduto = jsonProducto.getString(nombreCategoria);
                     }
//                     JSONObject jsonArray = jsonArrayProductos.getJSONObject(i);
//                     String jsonproductos = jsonArray.getString(nombreCategoria);
                    Log.i(TAG, "categoria: "+nombreCategoria);
                    Log.i(TAG, "productos: "+arrayProduto);
                    Categoria categoria = new Categoria(nombreCategoria,arrayProduto);
                    categorias.add(categoria);
                }

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getProductos() {
        return productos;
    }

    public void setProductos(String productos) {
        this.productos = productos;
    }

    public ArrayList<Categoria> getCategorias() {
        return categorias;
    }
}
