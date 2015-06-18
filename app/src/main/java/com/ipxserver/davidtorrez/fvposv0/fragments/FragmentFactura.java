package com.ipxserver.davidtorrez.fvposv0.fragments;


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
import android.support.v4.app.Fragment;
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
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ipxserver.davidtorrez.fvposv0.Listeners.FragmentReceiver;
import com.ipxserver.davidtorrez.fvposv0.R;
import com.ipxserver.davidtorrez.fvposv0.Util.Converter;
import com.ipxserver.davidtorrez.fvposv0.Util.DateUtil;
import com.ipxserver.davidtorrez.fvposv0.Util.Tokenizer;
import com.ipxserver.davidtorrez.fvposv0.adapter.GridbarAdapter;
import com.ipxserver.davidtorrez.fvposv0.adapter.ListAdapter;
import com.ipxserver.davidtorrez.fvposv0.models.Client;
import com.ipxserver.davidtorrez.fvposv0.models.Factura;
import com.ipxserver.davidtorrez.fvposv0.models.InvoiceItem;
import com.ipxserver.davidtorrez.fvposv0.models.Product;
import com.nbbse.mobiprint3.Printer;

import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by keyrus on 23-05-15.
 */
public class FragmentFactura extends Fragment //implements //DialogUser.UserDialgoListener
{
    public ArrayList<Product> listaSeleccionados;
//    public ProductListAdapter productListAdapter;
    public ListAdapter listAdapter;
    public Double monto;
    TextView nit,name;

    Client cliente;
    Printer imprimir;

    //Dialogos
    public ProgressDialog pDialog;
//    FacturaReceiver facturaReceiver;
   public static FragmentFactura newInstance(ArrayList<Product> listaSeleccionados, Double monto)
   {
       FragmentFactura  fragmentFactura = new FragmentFactura();
       Bundle arg = new Bundle();
       //Todo: Adicionar un parametro de monto total para tenerlo todo en el fragmento

       arg.putSerializable("lista", listaSeleccionados);
       arg.putDouble("monto",monto);
        fragmentFactura.setArguments(arg);
       return fragmentFactura;
   }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        listaSeleccionados = (ArrayList<Product>)getArguments().getSerializable("lista");
        monto = getArguments().getDouble("monto");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_factura,container,false );

        nit = (TextView) rootView.findViewById(R.id.txt_factura_nit);
        name = (TextView)rootView.findViewById(R.id.txt_factura_nombre);
        ListView lista =(ListView) rootView.findViewById(R.id.listProductos);

//
        listAdapter = new ListAdapter(rootView.getContext(),listaSeleccionados);

        lista.setAdapter(listAdapter);

        GridView gridBar = (GridView) rootView.findViewById(R.id.barra_factura_saldo);
        GridbarAdapter gridbarAdapter = new GridbarAdapter(rootView.getContext());
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


        Factura factura = new Factura(convertiraISO("{\"invoice_number\":\"0058\",\"control_code\":\"86-CE-FC-74\",\"invoice_date\":\"17-06-2015\",\"activity_pri\":\"ENSE\\u00d1ANZA DE ADULTOS Y OTROS TIPOS DE ENSE\\u00d1ANZA\",\"amount\":\"900.00\",\"subtotal\":\"900.00\",\"fiscal\":\"0.00\",\"client\":{\"id\":19,\"nit\":\"256352\",\"name\":\"Alejandro\",\"public_id\":3},\"account\":{\"id\":4,\"timezone_id\":null,\"date_format_id\":null,\"datetime_format_id\":null,\"currency_id\":1,\"created_at\":\"2015-01-24 07:02:21\",\"updated_at\":\"2015-06-07 05:50:14\",\"deleted_at\":null,\"nit\":\"131555028\",\"name\":\"Centro de Capacitaci\\u00f3n T\\u00e9cnica Golden Bridge S.A.\",\"ip\":\"190.129.127.93\",\"account_key\":\"lyoArnN7Sl3c3bDI37qR452K9tABb9Vv\",\"last_login\":\"2015-06-07 05:50:14\",\"address1\":\"Sopocachi\",\"address2\":\"Calle Presbitero Medina N\\u00ba 2431\",\"city\":\"\",\"state\":\"\",\"postal_code\":\"\",\"country_id\":1,\"invoice_terms\":null,\"email_footer\":null,\"industry_id\":null,\"size_id\":null,\"work_phone\":\"2410650 - 2410640 - 2410665\",\"work_email\":\"\",\"pro_plan_paid\":\"2015-01-24\",\"cod_search\":0,\"aux1\":null,\"aux2\":null,\"custom_label1\":\"\",\"custom_value1\":\"\",\"custom_label2\":\"\",\"custom_value2\":\"\",\"custom_client_label1\":\"N\\u00famero de Cuenta\",\"custom_client_label2\":\"Celular\",\"custom_client_label3\":\"N\\u00ba de Carnet\",\"custom_client_label4\":\"N\\u00famero de Matr\\u00edcula\",\"custom_client_label5\":\"Plan\",\"custom_client_label6\":\"N\\u00famero de Cuotas\",\"custom_client_label7\":\"Cuota Mensual\",\"custom_client_label8\":\"Cuota Inicial\",\"custom_client_label9\":\"Ejec. Publicidad\",\"custom_client_label10\":\"\",\"custom_client_label11\":\"Fecha de Contrato\",\"custom_client_label12\":\"Primer Vencimiento\",\"open\":0,\"hide_quantity\":0,\"hide_paid_to_date\":0,\"custom_invoice_label1\":null,\"custom_invoice_label2\":null,\"custom_invoice_taxes1\":null,\"custom_invoice_taxes2\":null,\"vat_number\":\"\",\"invoice_taxes\":1,\"invoice_item_taxes\":0,\"fill_products\":1,\"update_products\":0,\"language_id\":1},\"law\":\"-\",\"invoice_items\":[{\"notes\":\"Abono a Matr\\u00edcula\",\"cost\":\"60.00\",\"qty\":\"15.00\"}],\"address1\":\"Sopocachi\",\"address2\":\"Calle Presbitero Medina N\\u00ba 2431\",\"num_auto\":\"2904001336362\",\"fecha_limite\":\"31-05-2015\"}"));
            Imprimir(factura);
        Intent intent = new Intent("cambiar_fragmento");

        intent.putExtra("operacion", FragmentReceiver.FRAGMENT_LISTA);
        getActivity().sendBroadcast(intent);
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

    private void adicionarCliente() {

        //Todo: Dialog builder de momento hasta que se encuentre uno con mayor control XD

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
                cliente = new Client();
                cliente.setNit(input.getText().toString());
               // nit.setText(cliente.getNit());
                buscarCliente(cliente.getNit());
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

    private void buscarCliente(String nit) {
        pDialog = new ProgressDialog(getActivity());
        pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pDialog.setTitle("Buscando Cliente");
        pDialog.setMessage("Por favor Espere ...");
        pDialog.setCancelable(true);
        pDialog.setMax(100);
//        numero= txtEntrada.getText().toString();
//        monto= txtSalida.getText().toString();

        AsyncCallWS task = new AsyncCallWS();


        //Call execute
        task.execute();
    }

    private void mostrarCliente() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("Datos Cliente");

        LinearLayout ld= new LinearLayout(getActivity());
        ld.setOrientation(LinearLayout.VERTICAL); //1 is for vertical orientation
        final EditText txtNitDialog = new EditText(getActivity());
        final EditText txtNombreDialog = new EditText(getActivity());
        final EditText txtEmailDialog = new EditText(getActivity());
        //  final EditText input2 = new EditText(getActivity());
        ld.addView(txtNitDialog);
        ld.addView(txtNombreDialog);
        ld.addView(txtEmailDialog);
        //lila1.addView(input2);

        txtNitDialog.setText(cliente.getNit());
        txtNombreDialog.setHint("Nombre/Razon Social");
        txtEmailDialog.setHint("Correo Electronico");

        txtNitDialog.setInputType(InputType.TYPE_CLASS_NUMBER);
        txtNombreDialog.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
        txtEmailDialog.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_SUBJECT);
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
                cliente.setEmail(txtEmailDialog.getText().toString());

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

    private class AsyncCallWS extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            Log.i("consultaWS", "doInBackground");
//	            getFahrenheit(celcius);
            //getCobro();
//	            calcularEdad();

            //Todo: simulando comunicacion
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            Log.i("consultaWS", "onPostExecute");
            pDialog.dismiss();
            mostrarCliente();
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

            pDialog.setProgress(0);
            pDialog.show();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            Log.i("consultaWS", "onProgressUpdate");
        }

    }

    //---------------------------------------------------Modulo de Impresion--------------------------------------//
    public void Imprimir(Factura factura)
    {
   Converter conv= new Converter();
        Vector vnombre = TextLine("NOMBRE: "+factura.getCliente().getName(),36);

        Vector literal = TextLine("SON: "+conv.getStringOfNumber(factura.getAmount()),37);
        Vector vactividad = TextLine(factura.getActividad(),34);
        Vector vtitulo =  TextLine(factura.getAccount().getName(),35);
        byte printLine[] =null;
//        try {
//            printLine = ba.readImage(BMPGenerator.encodeBMP(getLinea()));
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        }
//        try {
//            titulos = ba.readImage(BMPGenerator.encodeBMP(getInvoiceItemTitulo("Cant.","Precio","Importe")));
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        }

        Vector prods = new Vector();

        for(int i=0;i<factura.getInvoiceItems().size();i++)
        {

                InvoiceItem invitem = (InvoiceItem) factura.getInvoiceItems().elementAt(i);

//                                        String concepto =(String) conceptos.elementAt(i);
//                                            imprimir.printText(invitem.getNotes(), 1);


                double subTotal = (Double.parseDouble(invitem.getCost())*Double.parseDouble(invitem.getQty()));
                double costo =Double.parseDouble(invitem.getCost());


//                                            double c = Double.parseDouble(invitem.getQty());


//                                        ba = (byte[]) DetalleProductos.elementAt(i);
                String  producto = ConstruirFila(""+invitem.getQty(),""+invitem.getCost(),""+redondeo(subTotal,2));
                prods.addElement(producto);


        }
        Vector s = TextLine("\"LA REPRODUCCION TOTAL O PARCIAL Y/O EL USO NO AUTORIZADO DE ESTA NOTA FISCAL, CONSTITUYE UN DELITO A SER SANSIONADO CONFORME A LA LEY\"",32);
//        byte vs[] = null;
//        try {
////            vs= ba.readImage(BMPGenerator.encodeBMP(getLeyenda(s)));
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        }

        imprimir = Printer.getInstance();
        switch (imprimir.getPaperStatus()) // check paper status
        {
            case Printer.PRINTER_EXIST_PAPER:
                if (imprimir.voltageCheck()) // check voltage, if it is allowed to
                // print
                {
                    //Imprimiendo Factura
//                    DeviceOps deviceOps = DeviceOps.getInstance();
//                                    imprimir.printBitmap(deviceOps.readImage("/FAC_tigo2.bmp", 0));
//                                    //imprimir.printBitmap(deviceOps.readImage("/viva.bmp", 0));
                    //Encabezado
//                    for(int j=0;j<vtitulo.size();j++)
//                    {
//                        String linea = (String) vtitulo.elementAt(j);
////                                        imprimir.printText(linea, 1);
//                        imprimir.printText(ConstruirFilaA(linea), 1);
//                    }
                    imprimir.printText("   IPX Server ",2);

//                                    imprimir.printTextWidthHeightZoom(ConstruirFilaA(factura.getAccount().getName()), 2, 1);
//                                     try{
//                                         imprimir.printBitmap(this.ba.readImage(BMPGenerator.encodeBMP(imprimirTitulo(""))));
//
//                                     }catch(IOException e){}
//                                    imprimir.printTextWidthHeightZoom(ConstruirFilaA(), 2, 1);
//                                    imprimir.printText(ConstruirFila(factura.getAccount().getName()), 1);
                    String dir1 ="Edificio Caracas Of. 201";
                    String dir2= "Av. 16 de Julio No 1456";

                    imprimir.printText("           Casa Matriz");
                    imprimir.printText(ConstruirFila(dir1), 1);
                    imprimir.printText(ConstruirFila(dir2), 1);
//                                    imprimir.printText(ConstruirFila("SFC-001"), 1);
                    imprimir.printText(ConstruirFila("FACTURA"), 1);
                    imprimir.printBitmap(getResources().openRawResource(R.raw.linea));
                    //Datos de la Empresa
                    imprimir.printText("              NIT: " + factura.getAccount().getNit(), 1);
                    imprimir.printText("      FACTURA No.: "+factura.getInvoiceNumber(), 1);
                    imprimir.printText(" AUTORIZACION No.: "+factura.getNumAuto(), 1);
                    imprimir.printBitmap(getResources().openRawResource(R.raw.linea));
                    //Datos del cliente
                    //Colocar Actividad Economica  PRODUCCI\u00D3N DE AGUAS MINERALES

//                                    imprimir.printText("ELABORACI\u00D3N DE BEBIDAS NO ALCOH\u00D3LICAS",1);
//                                    imprimir.printText("PRODUCCI\u00D3N DE AGUAS MINERALES.",1);
//                                    ImprimirActividad();
//                                    imprimir.printBitmap(actividad);
                    for(int j=0;j<vactividad.size();j++)
                    {
                        String linea = (String) vactividad.elementAt(j);
                        imprimir.printText(linea, 1);

//                                         imprimir.printTextWidthHeightZoom(ConstruirFilaA(linea), 2, 1);
                    }
//                                    imprimir.printText(ConstruirFila(""+factura.getActividad()), 1);
                    imprimir.printText("FECHA: "+factura.getInvoiceDate()+" Hora:"+ DateUtil.dateToString1(), 1);
                    imprimir.printText("NIT/CI: "+factura.getCliente().getNit()+"    Cod.:"+factura.getCliente().getPublic_id(), 1);
//                                    imprimir.printText("NOMBRE: "+factura.getCliente().getName(), 1);
                    for(int j=0;j<vnombre.size();j++)
                    {
                        String linea = (String) vnombre.elementAt(j);
                        imprimir.printText(linea, 1);
                    }


//                                    imprimir.printBitmap(deviceOps.readImage("/linea.bmp", 0));
                    //invoice items
                    imprimir.printText(ConstruirFila("Cant.","Precio","Importe"), 1);
                    imprimir.printBitmap(getResources().openRawResource(R.raw.linea));
//
                    //productos impresos por hardware
//                                    for(int i =0;i<factura.getInvoiceItems().size();i++)
//                                        {
//                                        InvoiceItem invitem = (InvoiceItem) factura.getInvoiceItems().elementAt(i);
//
//
////                                        String cantidad= Integer.parseInt(invitem.getQty());
//                                        String cantidad = invitem.getQty();
//
//                                        double subTotal = (Double.parseDouble(invitem.getCost())*Double.parseDouble(invitem.getQty()));
//                                        double costo =Double.valueOf(invitem.getCost()).doubleValue();
//                                        String concepto = invitem.getNotes();
//                                        double c = Double.valueOf(cantidad).doubleValue();
//
//                                        imprimir.printText(ConstruirFila(""+(int)c,concepto,redondeo(costo,2)+" "+redondeo(subTotal,2)), 1);
//
////
//
//                                      }
                    //productos impresos  por software
                    //nota solo se imprimira en texto corrido la evalucion de la descripsion


//                                     ba = (byte[]) DetalleProductos.elementAt(0);
//                    imprimir.printBitmap(titulos);
//                                        imprimir.printBitmap(printLine);
//                                        imprimir.printText("numero de invoice items: "+factura.getInvoiceItems().size()+"", 1);

                    for(int i=0;i<factura.getInvoiceItems().size();i++)
                    {
                        InvoiceItem invitem = (InvoiceItem) factura.getInvoiceItems().elementAt(i);

                        Vector vl = TextLine(invitem.getNotes(),36);
                        for(int y = 0;y<vl.size();y++)
                        {
                            String l = (String) vl.elementAt(y);
                            imprimir.printText(l, 1);
                        }
//                                        imprimir.printText(invitem.getNotes(), 1);
                        String  producto = (String) prods.elementAt(i);
                      imprimir.printText(producto);
//                      imprimir.printBitmap(b);

                    }

//                                    for(int i=1;i<DetalleProductos.size();i++)
//                                    {
//
////                                        String concepto =(String) conceptos.elementAt(i);
////                                        imprimir.printText(concepto, 1);
//                                        ba = (byte[]) DetalleProductos.elementAt(i);
//                                        imprimir.printBitmap(ba);
//
//                                    }
                    imprimir.printBitmap(getResources().openRawResource(R.raw.linea));
//                                    imprimir.printBitmap(deviceOps.readImage("/linea.bmp", 0));
//                                  segundo metodo
//                                    for(int i=0;i<bi.length;i++)
//                                    {
//                                        imprimir.printBitmap(bi[i].getBi());
//                                    }
//

//                                  imprimir.printBitmap(deviceOps.readImage("/linea.bmp", 0));

                    imprimir.printText("                TOTAL: Bs "+factura.getAmount(), 1);

                    double descuento = Double.parseDouble(factura.getSubtotal())-Double.parseDouble(factura.getAmount());
//                                    imprimir.printText("ICE: "+factura.getIce(), 1);

                    imprimir.printText("           DESCUENTOS: Bs "+redondeo(descuento,2)+" ", 1);

//                                    imprimir.printText("IMPORTE BASE CREDITO FISCAL: "+factura.getFiscal(),1);
                    imprimir.printText("        MONTO A PAGAR: Bs "+factura.getAmount(),1);


//                                    imprimir.printText("SON:"+NumeroLiteral(""+monto)+"Bolivianos",1);
//                                    imprimir.printText("SON: "+conv.getStringOfNumber(factura.getAmount()),1);
                    for(int j=0;j<literal.size();j++)
                    {
                        String linea = (String) literal.elementAt(j);

                        imprimir.printText(linea, 1);
                    }
                    imprimir.printBitmap(getResources().openRawResource(R.raw.linea));
                    //imprimir impuesto ic
                    // imprimir.printText()

                    imprimir.printText("CODIGO DE CONTROL: "+factura.getControlCode(),1);
                    imprimir.printText("FECHA LIMITE EMISION: "+factura.getFechaLimite(),1);

                    imprimir.printBitmap(getResources().openRawResource(R.raw.bus_ticket_qr));

//                                    imprimir.printEndLine();
//                                    imprimir.printText("CSD:"+operador.getId()+" "+operador.getUsuario() +"-"+factura.getDatecom(), 1);
//                                    imprimir.printText("CSD:143 farbo-18:00:35", 1);


                    try {

//                        imprimir.printBitmap(ImagenQr);


                    } catch (Exception ex) {


                    }
//                                  BmpArray b = new BmpArray();
//                                      Vector leyenda= TextLine("'ESTA FACTURA CONTRIBUYE AL DESARROLLO DEL PAIS, EL USO ILICITO DE ESTA SERA SANCIONADO DE ACUERDO A LEY'");

//                    try{
////                                         imprimir.printBitmap(this.ba.readImage(BMPGenerator.encodeBMP(getLeyenda(s))));
////                        imprimir.printBitmap(vs);
//                        for(int y = 0;y<s.size();y++)
//                        {
//                            String l = (String) s.elementAt(y);
//                            imprimir.printText(l, 1);
//                        }
//
//                    }catch(Exception e){}
                    imprimir.printBitmap(getResources().openRawResource(R.raw.fac3));
//                    imprimir.printBitmap(deviceOps.readImage("/linea.bmp", 0));
                    imprimir.printBitmap(getResources().openRawResource(R.raw.linea));
                    imprimir.printText(ConstruirFila("www.facturavirtual.com.bo"), 1);
//                                      Vector vec = TextLine(factura.getLaw());
//                                       BmpArray b2 = new BmpArray(this);
//                                    try {
//                                        imprimir.printBitmap(this.ba.readImage(BMPGenerator.encodeBMP(getLeyenda(vec))));
//                           //                                     imprimir.printBitmap(b.readImage(BMPGenerator.encodeBMP(getLeyenda(vec))),0);
//                                    } catch (IOException ex) {
//                                        ex.printStackTrace();
//                                    }

                    imprimir.printEndLine();




                } else {
//                    tickerLogin.setText("Bateria baja!! ");

                }
                break;
            case Printer.PRINTER_NO_PAPER:
//                tickerLogin.setText("Verifique el estado del papel!! ");
                break;
            case Printer.PRINTER_PAPER_ERROR:
//                tickerLogin.setText("Error de impresion!! ");
                break;
        }

    }
    private String redondeo(double num,int numDecim){
        long p=1;
        for(int i=0; i<numDecim; i++)p*=10;
        double resultado =  (double)(int)(p * num + 0.5) / p;
        Tokenizer tk = new Tokenizer(""+resultado,".");
        String entero = tk.nextToken();
        String decimal = tk.nextToken();
        while(decimal.length()<2)
        {
            decimal = decimal +"0";
        }
        return entero+"."+decimal;
    }
    public String ConstruirFilaA(String cad1)
    {
        String fila=cad1;
        String espacio =" ";
        int size = (28-cad1.length())/2;
        for(int i=0;i<size;i++)
        {
            fila = espacio+fila ;
        }

        return fila;
    }
    public String ConstruirFila(String cantidad,String concepto,String monto)
    {
        String linea=""+cantidad+" "+concepto;
        String espacio =" ";

        int size=32-linea.length()-monto.length();
        for(int i=0;i<size;i++)
        {
            linea = linea+ espacio;
        }
        linea = linea +monto;

        return linea;
    }
    public String ConstruirFila(String cad1)
    {
        String fila=cad1;
        String espacio =" ";
        int size = (36-cad1.length())/2;
        for(int i=0;i<size;i++)
        {
            fila = espacio+fila ;
        }

        return fila;
    }
    public static Vector  TextLine(String texto,int caracteres)
    {
        String vec[] =Split(texto," ");
//            imprimir.printText("hola mundo vector"+vec.length, 1);
        String linea="";
        String p;
        Vector v = new Vector();
        boolean sw=false;
        for(int i=0;i<vec.length;i++)
        {
//               linea = (String) v.elementAt(i);
            p = vec[i]+" ";
            //60 es el valor maximo que se deberia imprimir para las letras tipo Small

            if((p.length()+linea.length())<caracteres)
            {
                linea = linea +p;
                sw =false;
            }
            else{
                sw = true;
            }
            if(sw)
            {
//                   imprimir.printText(linea, 1);
                v.addElement(linea);
                linea =p;
            }

//               imprimir.printText(vec[i], 1);
        }
        if(linea.length()>0)
        {
//               imprimir.printText(linea, 1);
            v.addElement(linea);
        }
//
        return v;
    }
    public static String[] Split(String splitStr, String delimiter) {
        StringBuffer token = new StringBuffer();
        Vector tokens = new Vector();
        // split
        char[] chars = splitStr.toCharArray();
        for (int i=0; i < chars.length; i++) {
            if (delimiter.indexOf(chars[i]) != -1) {
                // we bumbed into a delimiter
                if (token.length() > 0) {
                    tokens.addElement(token.toString());
                    token.setLength(0);
                }
            } else {
                token.append(chars[i]);
            }
        }
        // don't forget the "tail"...
        if (token.length() > 0) {
            tokens.addElement(token.toString());
        }
        // convert the vector into an array
        String[] splitArray = new String[tokens.size()];
        for (int i=0; i < splitArray.length; i++) {
            splitArray[i] = (String)tokens.elementAt(i);
        }
        return splitArray;
    }
//    @Override
//    public void onDialogPositiveClick(DialogUser dialogUser) {
//        cliente = new Client();
//        View view  = (View) dialogUser.getView();
//        TextView txtNombreUser = (TextView) view.findViewById(R.id.txtNameUser);
//        TextView txtNitUser =(TextView)view.findViewById(R.id.txtNitUser);
//        TextView txtEmail = (TextView)view.findViewById(R.id.txtEmailUser);
//        cliente.setNombre(txtNombreUser.getText().toString());
//        cliente.setNit(txtNitUser.toString());
//        cliente.setEmail(txtEmail.toString());
//        nit.setText(cliente.getNit());
//        name.setText(cliente.getNombre());
//
//    }
//
//    @Override
//    public void onDialgoNegativeClick(DialogUser dialogUser) {
//        dialogUser.getDialog().cancel();
//    }
    //
//    @Override
//    public void onResume() {
//        facturaReceiver = new FacturaReceiver(this);
//        getActivity().registerReceiver(facturaReceiver, new IntentFilter("cast_factura"));
//        Log.i("David","resume Factura aqui esta escuchando");
//        super.onResume();
//    }
//
//    @Override
//    public void onPause() {
//        getActivity().unregisterReceiver(facturaReceiver);
//        super.onPause();
//    }
public static String convertiraISO(String s) {
    String out = null;
    try {
        out = new String(s.getBytes("ISO-8859-1"), "UTF-8");
    } catch (java.io.UnsupportedEncodingException e) {

        return null;
    }
    return out;
}
}
