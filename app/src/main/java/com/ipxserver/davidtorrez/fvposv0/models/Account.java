package com.ipxserver.davidtorrez.fvposv0.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by David-Pc on 07/06/2015.
 */
public class Account
{
    private String branch,first_name,last_name;
    //TODO: tratar productos XD ver logica de envio
    private String productos;
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
                setProductos(json.getString("productos"));
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
}
