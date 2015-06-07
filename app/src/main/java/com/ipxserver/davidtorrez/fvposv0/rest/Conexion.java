package com.ipxserver.davidtorrez.fvposv0.rest;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by David-Pc on 07/06/2015.
 */
public class Conexion {

    public final static int LOGIN=0;
    public final static  String LOGIN_URL="http://192.168.1.14/cloud/public/loginPOS";


    private String respuesta;
    private int codigo;
    private String error;


    private String user;
    private String pass;


    public Conexion(String user,String pass)
    {
        this.user = user;
        this.pass = pass;
    }

    public void enviarGet(int servicio)
    {
        String url = null;
        switch (servicio)
        {
            case LOGIN:
                url = LOGIN_URL;
                break;
            default: url="sin direccion";
        }
        StringBuilder builder = new StringBuilder();
        HttpClient client = new DefaultHttpClient();
        Log.i("David",""+url);
        HttpGet httpGet = new HttpGet(url);
        Log.i("David","user"+user);
        Log.i("David","pass"+pass);
// Add authorization header

        httpGet.addHeader(BasicScheme.authenticate(new UsernamePasswordCredentials(user, pass), "UTF-8", false));

// Set up the header types needed to properly transfer JSON
        httpGet.setHeader("Content-Type", "application/json");
        try {
            HttpResponse response = client.execute(httpGet);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            setCodigo(statusCode);
            if (statusCode == 200) {
                HttpEntity entity = response.getEntity();
                InputStream content = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(content));
                String line;
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }
                setRespuesta(builder.toString());
            } else {
               // Log.e(ParseJSON.class.toString(), "Failed to download file");
                 Log.e("Conexion","Error en la comunicacion");
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public static int getLOGIN() {
        return LOGIN;
    }


    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
