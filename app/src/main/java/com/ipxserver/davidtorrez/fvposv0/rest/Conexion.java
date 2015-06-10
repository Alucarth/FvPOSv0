package com.ipxserver.davidtorrez.fvposv0.rest;

import android.util.Base64;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by David-Pc on 07/06/2015.
 */
public class Conexion {

    public final static int LOGIN=0;
//    public final static  String LOGIN_URL="http://192.168.2.194/cloud/public/loginPOS";
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
        String urls = null;
        switch (servicio)
        {
            case LOGIN:
                urls = LOGIN_URL;
                break;
            default: urls="sin direccion";
        }
        try {
            URL url = new URL(urls);


            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            Log.i("David","user:"+user);
            Log.i("David","pass:"+pass);
            String basicAuth = "Basic " + new String(Base64.encode((user + ":" + pass).getBytes(), Base64.NO_WRAP));
            con.setRequestProperty("Authorization", basicAuth);
            con.setConnectTimeout(30000);
            con.setReadTimeout(30000);
            con.setInstanceFollowRedirects(true);
            int codestatus =con.getResponseCode() ;
            setCodigo(codestatus);
            readStream(con.getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
//        StringBuilder builder = new StringBuilder();
//        HttpClient client = new DefaultHttpClient();
//        Log.i("David",""+url);
//        HttpGet httpGet = new HttpGet(url);
//        Log.i("David","user"+user);
//        Log.i("David","pass"+pass);
//// Add authorization header
//
//        httpGet.addHeader(BasicScheme.authenticate(new UsernamePasswordCredentials(user, pass), "UTF-8", false));
//
//// Set up the header types needed to properly transfer JSON
//        httpGet.setHeader("Content-Type", "application/json");
//        try {
//            HttpResponse response = client.execute(httpGet);
//            StatusLine statusLine = response.getStatusLine();
//            int statusCode = statusLine.getStatusCode();
//            setCodigo(statusCode);
//            if (statusCode == 200) {
//                HttpEntity entity = response.getEntity();
//                InputStream content = entity.getContent();
//                BufferedReader reader = new BufferedReader(new InputStreamReader(content));
//                String line;
//                while ((line = reader.readLine()) != null) {
//                    builder.append(line);
//                }
//                setRespuesta(builder.toString());
//            } else {
//               // Log.e(ParseJSON.class.toString(), "Failed to download file");
//                 Log.e("Conexion","Error en la comunicacion");
//            }
//        } catch (ClientProtocolException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    private void readStream(InputStream in) {
        BufferedReader reader = null;
        StringBuilder builder = new StringBuilder();
        try {
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
                builder.append(line);
            }
            setRespuesta(builder.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
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
