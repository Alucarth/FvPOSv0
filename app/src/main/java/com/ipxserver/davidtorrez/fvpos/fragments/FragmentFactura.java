package com.ipxserver.davidtorrez.fvpos.fragments;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ipxserver.davidtorrez.fvpos.Listeners.FragmentReceiver;
import com.ipxserver.davidtorrez.fvpos.R;
import com.ipxserver.davidtorrez.fvpos.adapter.GridbarAdapterFactura;
import com.ipxserver.davidtorrez.fvpos.adapter.ListAdapter;
import com.ipxserver.davidtorrez.fvpos.database.SqliteController;
import com.ipxserver.davidtorrez.fvpos.davidprint.EmizorPrint;
import com.ipxserver.davidtorrez.fvpos.models.Client;
import com.ipxserver.davidtorrez.fvpos.models.Factura;
import com.ipxserver.davidtorrez.fvpos.models.Product;
import com.ipxserver.davidtorrez.fvpos.models.User;
import com.ipxserver.davidtorrez.fvpos.models.solicitudFactura;
import com.ipxserver.davidtorrez.fvpos.rest.Conexion;
import com.nbbse.mobiprint3.Printer;

import java.util.ArrayList;

//import android.util.Printer;

/**
 * Created by keyrus on 23-05-15.
 */
public class FragmentFactura extends android.support.v4.app.Fragment //implements //DialogUser.UserDialgoListener
{


    public ArrayList<Product> listaSeleccionados;
//    public ProductListAdapter productListAdapter;
    public ListAdapter listAdapter;
    public Double monto;
    TextView nit,name;
    CheckBox nit0;

    Client cliente;
    Printer imprimir;

    //Dialogos
    public ProgressDialog pDialog;
//    private Conexion conexion=null;
    String clienteNit;

    User usuario;

    int servicio;
    private SqliteController base;


    public  static final String LOG_TAG="factura";
    public String bmp_path = "qrcode.bmp";

//    FacturaReceiver facturaReceiver;
   public static FragmentFactura newInstance(ArrayList<Product> listaSeleccionados, Double monto,User usuario)
   {
       FragmentFactura  fragmentFactura = new FragmentFactura();

       Bundle arg = new Bundle();
       //Todo: Adicionar un parametro de monto total para tenerlo todo en el fragmento

       arg.putSerializable("lista", listaSeleccionados);
       arg.putDouble("monto", monto);
       arg.putSerializable("usuario", usuario);
        fragmentFactura.setArguments(arg);
       return fragmentFactura;
   }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        listaSeleccionados = (ArrayList<Product>)getArguments().getSerializable("lista");
        usuario =(User) getArguments().getSerializable("usuario");
        Log.i("David", "usuario=" + usuario.getUser());
        monto = getArguments().getDouble("monto");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        base = new SqliteController(this.getActivity().getApplication());
        View rootView = inflater.inflate(R.layout.fragment_factura,container,false );

        nit = (TextView) rootView.findViewById(R.id.txt_factura_nit);
        name = (TextView)rootView.findViewById(R.id.txt_factura_nombre);
        ListView lista =(ListView) rootView.findViewById(R.id.listProductos);
        nit0 = (CheckBox)rootView.findViewById(R.id.checkNit0);
        nit0.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    cliente = new Client();
                    cliente = Client.fromJson("{\"resultado\":0,\"cliente\":{\"id\":0,\"public_id\":1,\"name\":\"Sin Nombre\",\"nit\":\"0\",\"vat_number\":\"Sin Nombre\"}}");
                    Log.i("Client","id "+cliente.getId());
                    Log.i("Client","nombre"+ cliente.getNombre());

                    name.setText("NOMBRE: "+cliente.getNombre());
                    nit.setText("NIT: "+cliente.getNit());
                }else
                {
                    name.setText("NOMBRE: ");
                    nit.setText("NIT: ");
                    cliente = null;
                }

            }
        });
//
        listAdapter = new ListAdapter(rootView.getContext(),listaSeleccionados);

        lista.setAdapter(listAdapter);

        GridView gridBar = (GridView) rootView.findViewById(R.id.barra_factura_saldo);
        GridbarAdapterFactura gridbarAdapter = new GridbarAdapterFactura(rootView.getContext());
        gridBar.setAdapter(gridbarAdapter);

        gridbarAdapter.incrementar(monto);


        setHasOptionsMenu(true);



        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_factura,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       switch (item.getItemId())
       {
           case R.id.action_factura_cancelar: atras();
               return true;
           case R.id.action_factura_cliente: adicionarCliente();
               return true;
           case R.id.action_factura_imprimir: imprimir();
               return true;
           default: return super.onOptionsItemSelected(item);

       }

    }

    private void imprimir() {
        if(cliente==null)
        {
            alerta("Ingresar usuario","Por Favor ingrese un usuario o selecione nit 0");
        }
        else{
            servicio =Conexion.GUARDARFACTURA;
            getGuardarFacturaWS servicio2 = new getGuardarFacturaWS();
            servicio2.execute();

        }

//        Factura factura = new Factura(convertiraISO("{\"invoice_number\":\"0058\",\"control_code\":\"86-CE-FC-74\",\"invoice_date\":\"17-06-2015\",\"activity_pri\":\"ENSE\\u00d1ANZA DE ADULTOS Y OTROS TIPOS DE ENSE\\u00d1ANZA\",\"amount\":\"900.00\",\"subtotal\":\"900.00\",\"fiscal\":\"0.00\",\"client\":{\"id\":19,\"nit\":\"256352\",\"name\":\"Alejandro\",\"public_id\":3},\"account\":{\"id\":4,\"timezone_id\":null,\"date_format_id\":null,\"datetime_format_id\":null,\"currency_id\":1,\"created_at\":\"2015-01-24 07:02:21\",\"updated_at\":\"2015-06-07 05:50:14\",\"deleted_at\":null,\"nit\":\"131555028\",\"name\":\"Centro de Capacitaci\\u00f3n T\\u00e9cnica Golden Bridge S.A.\",\"ip\":\"190.129.127.93\",\"account_key\":\"lyoArnN7Sl3c3bDI37qR452K9tABb9Vv\",\"last_login\":\"2015-06-07 05:50:14\",\"address1\":\"Sopocachi\",\"address2\":\"Calle Presbitero Medina N\\u00ba 2431\",\"city\":\"\",\"state\":\"\",\"postal_code\":\"\",\"country_id\":1,\"invoice_terms\":null,\"email_footer\":null,\"industry_id\":null,\"size_id\":null,\"work_phone\":\"2410650 - 2410640 - 2410665\",\"work_email\":\"\",\"pro_plan_paid\":\"2015-01-24\",\"cod_search\":0,\"aux1\":null,\"aux2\":null,\"custom_label1\":\"\",\"custom_value1\":\"\",\"custom_label2\":\"\",\"custom_value2\":\"\",\"custom_client_label1\":\"N\\u00famero de Cuenta\",\"custom_client_label2\":\"Celular\",\"custom_client_label3\":\"N\\u00ba de Carnet\",\"custom_client_label4\":\"N\\u00famero de Matr\\u00edcula\",\"custom_client_label5\":\"Plan\",\"custom_client_label6\":\"N\\u00famero de Cuotas\",\"custom_client_label7\":\"Cuota Mensual\",\"custom_client_label8\":\"Cuota Inicial\",\"custom_client_label9\":\"Ejec. Publicidad\",\"custom_client_label10\":\"\",\"custom_client_label11\":\"Fecha de Contrato\",\"custom_client_label12\":\"Primer Vencimiento\",\"open\":0,\"hide_quantity\":0,\"hide_paid_to_date\":0,\"custom_invoice_label1\":null,\"custom_invoice_label2\":null,\"custom_invoice_taxes1\":null,\"custom_invoice_taxes2\":null,\"vat_number\":\"\",\"invoice_taxes\":1,\"invoice_item_taxes\":0,\"fill_products\":1,\"update_products\":0,\"language_id\":1},\"law\":\"-\",\"invoice_items\":[{\"notes\":\"Abono a Matr\\u00edcula\",\"cost\":\"60.00\",\"qty\":\"15.00\"}],\"address1\":\"Sopocachi\",\"address2\":\"Calle Presbitero Medina N\\u00ba 2431\",\"num_auto\":\"2904001336362\",\"fecha_limite\":\"31-05-2015\"}"));
//            Imprimir(factura);

//        Printer print= Printer.getInstance();
//        print.printText("Hola mundo ",2);
////        print.printBitmap(getResources().openRawResource(R.raw.linea));
////        print.printText("verificando imagen de impresion");
////        print.printEndLine();
//        String filename = getActivity().getFilesDir()+"/imagen.bmp";
//        String cadena = "imagen de lo que sea";
//        Toast.makeText(getActivity().getApplicationContext(),getActivity().getFilesDir()+"",Toast.LENGTH_LONG).show();
//
//        Bitmap imagen = drawText(cadena, 200, 20);
//
//        try {
//            if (AndroidBmpUtil.save(imagen, filename))
//            {
//                Log.e("David", "sin Errores :)");
//                print.printBitmap(filename);
//            }
//            else
//            {
//                Log.e("David", "por queeeeeeeeeeeeeee XD");
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        Bitmap bi = BitmapFactory.decodeFile(filename);
//        print.printBitmap(filename);
//        print.printEndLine();
//        FileOutputStream outputStream;
//
//        try {
//            outputStream = getActivity().openFileOutput(filename, Context.MODE_PRIVATE);
//            outputStream.write(string.getBytes());
//            outputStream.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

//        try{
//            FileInputStream fin = getActivity().openFileInput(filename);
//            int c;
//            String temp="";
//
//            while( (c = fin.read()) != -1){
//                temp = temp + Character.toString((char)c);
//            }
//
//            Toast.makeText(getActivity().getApplicationContext(),"file read: "+temp,Toast.LENGTH_SHORT).show();
//        }
//        catch(Exception e){
//        }

    }
    public void alerta(String titulo,String mensaje) {
        //se prepara la alerta creando nueva instancia
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
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
    private void adicionarCliente() {

        //Todo: Dialog builder de momento hasta que se encuentre uno con mayor control XD
        servicio = Conexion.CLIENTE;
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("Buscar Cliente");

        LinearLayout lila1= new LinearLayout(getActivity());
        lila1.setOrientation(LinearLayout.VERTICAL); //1 is for vertical orientation
        final EditText input = new EditText(getActivity());
      //  final EditText input2 = new EditText(getActivity());
        lila1.addView(input);
        //lila1.addView(input2);
        input.setHint("NIT|CI");


        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        input.requestFocus();

        builder.setView(lila1);



        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
              //Todo iniciar Progress con la consulta

                clienteNit = input.getText().toString();
               // nit.setText(cliente.getNit());
                getClientSW servicio = new getClientSW();
                servicio.execute();
                //mostrarCliente(input.getText().toString());
            }


        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        dialog.show();

        //
        //FragmentManager fm = getChildFragmentManager();
   //     DialogUser dialogUser = new DialogUser();
//        dialogUser.show(fm,"fragment_factura");
//        FragmentUserDialog fragmentUserDialog = new FragmentUserDialog();
//        fragmentUserDialog.show(fm,"fragment_factura");
    }





    private void mostrarCliente() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        if(cliente.getResultado().equals("0"))
        {
            builder.setTitle("Datos Cliente");
        }
        else
        {
            builder.setTitle("Registro de Cliente");
        }


        LinearLayout ld= new LinearLayout(getActivity());
        ld.setOrientation(LinearLayout.VERTICAL); //1 is for vertical orientation
        final EditText txtNitDialog = new EditText(getActivity());
        final EditText txtNombreDialog = new EditText(getActivity());
       // final EditText txtEmailDialog = new EditText(getActivity());
        //  final EditText input2 = new EditText(getActivity());
        ld.addView(txtNitDialog);
        ld.addView(txtNombreDialog);
        //ld.addView(txtEmailDialog);
        //lila1.addView(input2);

        txtNitDialog.setText(clienteNit);
        txtNombreDialog.setHint("Nombre/Razon Social");
       // txtEmailDialog.setHint("Correo Electronico");
        if(cliente.getResultado().equals("0")) {
            txtNombreDialog.setText(cliente.getNombre());

        }
        txtNitDialog.setInputType(InputType.TYPE_CLASS_NUMBER);
        txtNombreDialog.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
       // txtEmailDialog.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_SUBJECT);
        txtNombreDialog.requestFocus();

        builder.setView(ld);



        builder.setPositiveButton("aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Todo iniciar Progress con la consulta de registro en caso de ser necesario

                //.setText(input.getText().toString());
                //buscarCliente(input.getText().toString());
                //mostrarCliente(input.getText().toString());

                cliente.setNit(txtNitDialog.getText().toString());
                cliente.setNombre(txtNombreDialog.getText().toString());
               //cliente.setEmail(txtEmailDialog.getText().toString());

                if(!cliente.getResultado().equals("0")) {
                    //Todo: Registrar cliente XD
                    Log.e("David",""+Client.toJson(cliente).toString());
                    //ejecutar consulta de registro para el cliente
                    servicio = Conexion.REGISTROCLIENTE;
                    getRegistroClienteWS  servicio3 = new getRegistroClienteWS();
                    servicio3.execute();
                }


                name.setText("NOMBRE: "+cliente.getNombre());
                nit.setText("NIT: "+cliente.getNit());
            }


        });
        builder.setNegativeButton("cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        dialog.show();
    }

    private void atras() {
        Intent intent = new Intent("cambiar_fragmento");

        intent.putExtra("operacion", FragmentReceiver.FRAGMENT_EDITTABSWIPE);
        getActivity().sendBroadcast(intent);
    }

    public static Bitmap drawText(String text, int textWidth, int textSize) {
// Get text dimensions
        TextPaint textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG
                | Paint.LINEAR_TEXT_FLAG);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(textSize);
        StaticLayout mTextLayout = new StaticLayout(text, textPaint,
                textWidth, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);

// Create bitmap and canvas to draw to
        Bitmap b = Bitmap.createBitmap(textWidth, mTextLayout.getHeight(), Bitmap.Config.RGB_565);
        Canvas c = new Canvas(b);

// Draw background
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG
                | Paint.LINEAR_TEXT_FLAG);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.WHITE);
        c.drawPaint(paint);

// Draw text
        c.save();
        c.translate(0, 0);
        mTextLayout.draw(c);
        c.restore();

        return b;
    }

    private class getClientSW extends AsyncTask<String, Void, Void> {
       Conexion conexion;
        @Override
        protected Void doInBackground(String... params) {
            Log.i("consultaWS", "doInBackground");
//	            getFahrenheit(celcius);
            //getCobro();
//	            calcularEdad();
            switch(servicio)
            {
                case  Conexion.CLIENTE:
                        conexion = new Conexion(usuario.getUser(),usuario.getPassword(),getActivity().getBaseContext());
                        conexion.enviarGet(Conexion.CLIENTE, clienteNit);
                        break;
                case Conexion.GUARDARFACTURA:

                    solicitudFactura sf= new solicitudFactura();
                    sf.setClient_id(cliente.getId());
                    sf.setName(cliente.getNombre());
                    sf.setNit(cliente.getNit());
                    sf.setProductos(listaSeleccionados);

                    Log.i("David", "solicitud json: " + solicitudFactura.toJSON(sf));
                    conexion.enviarPost(Conexion.GUARDARFACTURA,solicitudFactura.toJSON(sf));
                    break;
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            Log.i("consultaWS", "onPostExecute");
            pDialog.dismiss();

            if(conexion.getCodigo()==200) {

                switch(servicio) {
                    case Conexion.CLIENTE:
                        cliente = null;
                        cliente= Client.fromJson(conexion.getRespuesta());
                        Log.i("David"," cliente:id "+cliente.getId());
                        Log.i("David"," cliente:public_id  "+cliente.getPublic_id());
                        Log.i("David"," cliente:nit "+cliente.getNit());
                        Log.i("David","cliente:name "+cliente.getNombre());
                        mostrarCliente();
                        break;
                    case Conexion.GUARDARFACTURA:
                        Log.i("David","respuesta factura; "+conexion.getRespuesta());
                        //Todo Imprimnir factura en caso de que este todo blue

                        Intent intent = new Intent("cambiar_fragmento");

                        intent.putExtra("operacion", FragmentReceiver.FRAGMENT_LISTA);
                        getActivity().sendBroadcast(intent);
                        break;
                }

            }


//              Toast.makeText(MulticobroPrincipal.this, "Tarea finalizada!",
//              Toast.LENGTH_SHORT).show();
//	            mostrar.setText(david);
            //Todo Alert con info del cliente registrarlo en el caso de no existir
            //alerta("MultiCobro",cobro.getMenssage());


        }

        @Override
        protected void onPreExecute() {
            Log.i("consultaWS", "onPreExecute");
//	            mostrar.setText("Calculating...");
            pDialog = new ProgressDialog(getActivity());
            pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            switch(servicio) {
                case Conexion.CLIENTE:
                    pDialog.setTitle("Buscando Cliente");
                    break;
                case Conexion.GUARDARFACTURA:
                    pDialog.setTitle("Generando Factura");
                    break;
                default: pDialog.setTitle("Prosesando");
            }

            pDialog.setMessage("Por favor Espere ...");
            pDialog.setCancelable(true);
            pDialog.setProgress(0);
            pDialog.show();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            Log.i("consultaWS", "onProgressUpdate");
        }

    }
    // metodo de consulta al servidor
    private class getGuardarFacturaWS extends AsyncTask<String, Void, Void> {
        Conexion conexion;
        @Override
        protected Void doInBackground(String... params) {
            Log.i("consultaWS", "doInBackground");
//	            getFahrenheit(celcius);
            //getCobro();
//	            calcularEdad();
            switch(servicio)
            {

                case Conexion.GUARDARFACTURA:

                    solicitudFactura sf= new solicitudFactura();
                    sf.setClient_id(cliente.getId());
                    Log.e("David", "Cliente id" + cliente.getId());
                    sf.setName(cliente.getNombre());
                    sf.setNit(cliente.getNit());
                    sf.setBranch_id(base.getSucursal());
                    sf.setProductos(listaSeleccionados);

                    Log.i("David", "solicitud json: " + solicitudFactura.toJSON(sf));

                    conexion = new Conexion(usuario.getUser(),usuario.getPassword(),getActivity().getBaseContext());
                    conexion.enviarPost(Conexion.GUARDARFACTURA, solicitudFactura.toJSON(sf));
                    break;
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            Log.i("consultaWS", "onPostExecute");
            pDialog.dismiss();
            Log.i("consultaWS", " codigo " + conexion.getCodigo());
            Log.i("consultaWS", " respuesta " + conexion.getRespuesta());
            if(conexion.getCodigo()==200) {

                switch(servicio) {

                    case Conexion.GUARDARFACTURA:
                        Log.e("David", "respuesta factura; " + conexion.getRespuesta());
                        Factura factura = new Factura(conexion.getRespuesta());
                        EmizorPrint emizorPrint = new EmizorPrint(getActivity());
                        emizorPrint.Imprimir(factura,"192.168.0.32",9100);
//                        Imprimir(factura);
                        //Todo Imprimnir factura en caso de que este todo blue
                        base.insertInvoice(conexion.getRespuesta(),factura);
                        base.getFacturas();

                        Intent intent = new Intent("cambiar_fragmento");

                        intent.putExtra("operacion", FragmentReceiver.FRAGMENT_LISTA);
                        getActivity().sendBroadcast(intent);


//                        FacturaCardItem facturaCardItem = new FacturaCardItem();
//                        facturaCardItem.setNumero(factura.getInvoiceNumber());
//                        facturaCardItem.setFecha(factura.getInvoiceDate());
//                        facturaCardItem.setMonto(factura.getAmount());
//                        Intent intent2 = new Intent("factura");
//                        intent2.putExtra("factura_item", facturaCardItem);
//                        getActivity().sendBroadcast(intent2);

                        break;
                }

            }


//              Toast.makeText(MulticobroPrincipal.this, "Tarea finalizada!",
//              Toast.LENGTH_SHORT).show();
//	            mostrar.setText(david);
            //Todo Alert con info del cliente registrarlo en el caso de no existir
            //alerta("MultiCobro",cobro.getMenssage());


        }

        @Override
        protected void onPreExecute() {
            Log.i("consultaWS", "onPreExecute");
//	            mostrar.setText("Calculating...");
            pDialog = new ProgressDialog(getActivity());
            pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            switch(servicio) {

                case Conexion.GUARDARFACTURA:
                    pDialog.setTitle("Generando Factura");
                    break;
                default: pDialog.setTitle("Prosesando");
            }

            pDialog.setMessage("Por favor Espere ...");
            pDialog.setCancelable(true);
            pDialog.setProgress(0);
            pDialog.show();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            Log.i("consultaWS", "onProgressUpdate");
        }

    }

    private class getRegistroClienteWS extends AsyncTask<String, Void, Void> {
        Conexion conexion;
        @Override
        protected Void doInBackground(String... params) {
            Log.i("consultaWS", "doInBackground");
//	            getFahrenheit(celcius);
            //getCobro();
//	            calcularEdad();
            switch(servicio)
            {

                case Conexion.REGISTROCLIENTE:



                    Log.i("David", "solicitud json: " + Client.toJson(cliente));
                    conexion = new Conexion(usuario.getUser(),usuario.getPassword(),getActivity().getBaseContext());
                    conexion.enviarPost(Conexion.REGISTROCLIENTE,Client.toJson(cliente).toString());
                    break;
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            Log.i("consultaWS", "onPostExecute");
            pDialog.dismiss();
            Log.i("consultaWS", " codigo " + conexion.getCodigo());
            Log.i("consultaWS", " respuesta "+ conexion.getRespuesta());
            if(conexion.getCodigo()==200) {

                switch(servicio) {

                    case Conexion.REGISTROCLIENTE:
                        Log.i("David", "respuesta del registro; " + conexion.getRespuesta());

                        cliente = Client.fromJson(conexion.getRespuesta());
                        break;
                }

            }


//              Toast.makeText(MulticobroPrincipal.this, "Tarea finalizada!",
//              Toast.LENGTH_SHORT).show();
//	            mostrar.setText(david);
            //Todo Alert con info del cliente registrarlo en el caso de no existir
            //alerta("MultiCobro",cobro.getMenssage());


        }

        @Override
        protected void onPreExecute() {
            Log.i("consultaWS", "onPreExecute");
//	            mostrar.setText("Calculating...");
            pDialog = new ProgressDialog(getActivity());
            pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            switch(servicio) {

                case Conexion.REGISTROCLIENTE:
                    pDialog.setTitle("Registrando Cliente");
                    break;
                default: pDialog.setTitle("Prosesando");
            }

            pDialog.setMessage("Por favor Espere ...");
            pDialog.setCancelable(true);
            pDialog.setProgress(0);
            pDialog.show();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            Log.i("consultaWS", "onProgressUpdate");
        }

    }



}
