package com.ipxserver.davidtorrez.fvposv0.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.ClientInfoStatus;

/**
 * Created by David Torrez on 01/06/2015.
 */
public class Client
{
    public static final String ID="id";
    public static final String NOMBRE="nombre";
    public static final String NIT="nit";
    public static final String EMAIL="email";
    public static final String RESULTADO="resultado";
    public static final String MENSAJE="mensaje";
    String id;
    String nombre;
    String nit;
    String email;
    String resultado;
    String mensaje;
    public Client()
    {

    }
    public static Client fromJson(String jsonText)
    {
        Client cliente = new Client();
        try {
            JSONObject json = new JSONObject(jsonText);
            if(json.has(Client.ID))
            {
                cliente.setId(json.getString(Client.ID));
            }
            if(json.has(Client.NOMBRE))
            {
                cliente.setNombre(json.getString(Client.NOMBRE));
            }
            if(json.has(Client.NIT))
            {
                cliente.setNit(json.getString(Client.NIT));
            }
            if(json.has(Client.EMAIL))
            {
                cliente.setEmail(json.getString(Client.EMAIL));
            }
            if(json.has(Client.RESULTADO))
            {
                cliente.setResultado(json.getString(Client.RESULTADO));
            }
            if(json.has(Client.MENSAJE))
            {
                cliente.setMensaje(json.getString(Client.MENSAJE));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return cliente;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
