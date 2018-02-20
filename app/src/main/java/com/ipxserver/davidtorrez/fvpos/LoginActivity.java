package com.ipxserver.davidtorrez.fvpos;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.ipxserver.davidtorrez.fvpos.Util.PrintPic;
import com.ipxserver.davidtorrez.fvpos.davidqr.AndroidBmpUtil;
import com.ipxserver.davidtorrez.fvpos.davidqr.Contents;
import com.ipxserver.davidtorrez.fvpos.davidqr.QRCodeEncoder;
import com.ipxserver.davidtorrez.fvpos.models.User;
import com.ipxserver.davidtorrez.fvpos.rest.Conexion;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.BitSet;


public class LoginActivity extends FragmentActivity {

    private String TAG = "DavidLog";
    ProgressDialog pDialog;
    EditText ednit,eduser,edpass;
    Button btsesion;
    User usuario;
    Conexion conexion;
    public String bmp_path = "qrcode.bmp";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        inicializarComponentes();
//        Double num= 12.29*2.3;
//        Double num=Double.parseDouble("10.00");
//
//        Log.i("Login conversor","imprimiendo double:"+num);
//        Socket mSocket = null;
//        try {
//            mSocket = new Socket("192.168.0.29", 9100);
//        } catch (IOException e) {
//            e.printStackTrace();
//            OutputStream mPrinter = null;
//            try {
//                mPrinter = mSocket.getOutputStream();
//                mPrinter.write(0x1B);
//                mPrinter.write(0x70);
//                mPrinter.write(0);
//                mPrinter.write(200);  // t1
//                mPrinter.write(255);
//            } catch (IOException e1) {
//                e1.printStackTrace();
//            }
//
//        }
    }
    private void ejecutaCliente() {


        String ip = "192.168.0.32";
        int puerto = 9100;

        try {
            Socket mSocket = new Socket(ip, puerto);
            OutputStream  mPrinter = mSocket.getOutputStream();
            PrintWriter printer = new  PrintWriter(mSocket.getOutputStream(),true);
//            Bitmap bmp = getQr("Para mi amiga Nadia que tu hermosa sonrisa siga iluminando el mundo Atte: David  ");
//            Bitmap bmp = getQr("Para mi amiga Carla que tu fortaleza y belleza perduren en el tiempo  Atte: David  ");

            Bitmap bmp = getQr("Cechus y Karem XD ");
            byte[] cmd = new byte[3];
            byte[] imagenByte = null;


            PrintPic pg = new PrintPic();
            pg.initCanvas(384);
            pg.initPaint();
            pg.drawImage(0, 0,bmp);
            imagenByte = pg.printDraw();
            cmd[0] = 0x1b;
            cmd[1] = 0x40;
            cmd[2] = 0x0;
            mPrinter.write(cmd);
            cmd[0] = 0x1b;
            cmd[1] = 0x76;
            cmd[2] = 0x30;
            mPrinter.write(cmd);
            mPrinter.write(imagenByte);
            //mPrinter.write(FEED_LINE);
            cmd[0] = 0x1b;
            cmd[1] = 0x61;
            cmd[2] = 1;
            mPrinter.write(cmd);

            printer.println("hola mundo XD");
           // mPrinter.write(FEED_PAPER_AND_CUT);
            cmd[0] = 0x1d;
            cmd[1] = 0x56;
            cmd[2] = 0;
            mPrinter.write(cmd);
            //printer.write(String.valueOf(FEED_PAPER_AND_CUT));
//            mPrinter.write(cmd);

            mPrinter.flush();
            mPrinter.close();
            mSocket.close();
        } catch (Exception e) {
            Log.i("error: ", e.toString());
        }
    }
    private void inicializarComponentes() {
        ednit = (EditText) findViewById(R.id.etStore);
        eduser = (EditText)findViewById(R.id.etUserName);
        edpass = (EditText)findViewById(R.id.etPass);
        btsesion =(Button) findViewById(R.id.btnSingIn);

//        ednit.setText("davidcorp");
//        eduser.setText("admin");
//        edpass.setText("123456");
        btsesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                usuario = new User(eduser.getText().toString(), ednit.getText().toString(), edpass.getText().toString());
//                usuario = new User("factura","virtual","cascada");
//                usuario = new User("cascada","factura","virtual");
                conexion = new Conexion(usuario.getUser(), usuario.getPassword(),usuario.getNitempresa());
                pDialog = new ProgressDialog(LoginActivity.this);
                pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                pDialog.setTitle("Autentificando");
                pDialog.setMessage("Por favor espere...");
                pDialog.setCancelable(true);

                AsyncCallWS task = new AsyncCallWS();
                //Call execute
                task.execute();


            }
        });

//        usuario = new User(eduser.getText().toString(), ednit.getText().toString(), edpass.getText().toString());

//        usuario = new User("factura","virtual","cascada");
//        conexion = new Conexion(usuario.getUser(), usuario.getPassword(),usuario.getNitempresa());
//        pDialog = new ProgressDialog(LoginActivity.this);
//        pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//        pDialog.setTitle("Autentificando");
//        pDialog.setMessage("Por favor espere...");
//        pDialog.setCancelable(true);
//
//
//        AsyncCallWS task = new AsyncCallWS();
//        //Call execute
//        task.execute();

        Log.w(TAG,"direccion app:"+ getApplicationContext().getFilesDir().getAbsolutePath());
    }
    public void limpar()
    {
        usuario =null;

        eduser.setText("");
        edpass.setText("");
        ednit.setText("");

    }
    public void Cambiar()
    {
        Intent intent = new Intent(this, PrincipalActivity.class);
        intent.putExtra("cuenta", conexion.getRespuesta());
        intent.putExtra("usuario",usuario);
//		 intent.putExtra("", value)
        startActivity(intent);
    }
    public void alerta(String titulo,String mensaje) {
        //se prepara la alerta creando nueva instancia
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        //seleccionamos la cadena a mostrar
        dialogBuilder.setMessage(mensaje);
        //elegimo un titulo y configuramos para que se pueda quitar
        dialogBuilder.setCancelable(true).setTitle(titulo);
        dialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        //mostramos el dialogBuilder
        dialogBuilder.create().show();

//		  ProgressDialog.show( MulticobroPrincipal.this,titulo,mensaje,true,true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private class AsyncCallWS extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            Log.i(TAG, "doInBackground");
//            ejecutaCliente();
            conexion.enviarGet(Conexion.LOGIN);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            Log.i(TAG, "onPostExecute");
            pDialog.dismiss();
            if(conexion.getCodigo()==200)
            {
                Cambiar();

            }
            else
            {
                alerta("Autentificacion","Por favor verifique que el usuario y password sean correctos");
            }
            Log.i("David", "codifgo" + conexion.getCodigo());
            Log.i("David","conexion"+conexion.getRespuesta());
            limpar();
//              Toast.makeText(MulticobroPrincipal.this, "Tarea finalizada!",
//              Toast.LENGTH_SHORT).show();
//	            mostrar.setText(david);

            //alerta("MultiCobro",cobro.getMenssage());


        }

        @Override
        protected void onPreExecute() {
            Log.i(TAG, "onPreExecute");
//	            mostrar.setText("Calculating...");

            pDialog.setProgress(0);
            pDialog.show();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            Log.i(TAG, "onProgressUpdate");
        }

    }
    public Bitmap getQr(String texto)
    {
        String qrInputText = texto;
        Log.v(TAG, qrInputText);
        Bitmap bitmap=null;
        //Find screen size
        WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        int width = point.x;
        int height = point.y;
        int smallerDimension = width < height ? width : height;
        smallerDimension = smallerDimension * 1/3;

        //Encode with a QR Code image
        QRCodeEncoder qrCodeEncoder = new QRCodeEncoder(qrInputText,
                null,
                Contents.Type.TEXT,
                BarcodeFormat.QR_CODE.toString(),
                smallerDimension);
        try {
            bitmap = qrCodeEncoder.encodeAsBitmap();
//            ImageView myImage = (ImageView) findViewById(R.id.imagen);
//            myImage.setImageBitmap(bitmap);

        } catch (WriterException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

}
