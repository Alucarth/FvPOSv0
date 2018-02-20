package com.ipxserver.davidtorrez.fvpos.davidprint;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.ipxserver.davidtorrez.fvpos.R;
import com.ipxserver.davidtorrez.fvpos.Util.Converter;
import com.ipxserver.davidtorrez.fvpos.Util.DateUtil;
import com.ipxserver.davidtorrez.fvpos.Util.PrintPic;
import com.ipxserver.davidtorrez.fvpos.Util.Tokenizer;
import com.ipxserver.davidtorrez.fvpos.davidqr.AndroidBmpUtil;
import com.ipxserver.davidtorrez.fvpos.davidqr.Contents;
import com.ipxserver.davidtorrez.fvpos.davidqr.QRCodeEncoder;
import com.ipxserver.davidtorrez.fvpos.models.Factura;
import com.ipxserver.davidtorrez.fvpos.models.InvoiceItem;
import com.nbbse.mobiprint3.Printer;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Vector;

/**
 * Created by david on 01-02-16.
 */
public class EmizorPrint {

    public String bmp_path = "qrcode.bmp";
    Printer imprimir;
    Context context;
    public static final String TAG ="Emizor Print XD";


    public static final byte[] INIT = {27, 64};
    public static byte[] FEED_LINE = {10};

    public static byte[] SELECT_FONT_A = {27, 33, 0};
    public static byte[] SELECT_FONT_B = {0x1b, 0x21, 4};
    public static byte[] ALIGN_LEFT = {0x1b, 0x61, 0};
    public static byte[] ALIGN_CENTER = {0x1b, 0x61, 1};

    public static byte[] SET_BAR_CODE_HEIGHT = {29, 104, 100};
    public static byte[] PRINT_BAR_CODE_1 = {29, 107, 2};
    public static byte[] SEND_NULL_BYTE = {0x00};

    public static byte[] SELECT_PRINT_SHEET = {0x1B, 0x63, 0x30, 0x02};
    public static byte[] FEED_PAPER_AND_CUT = {0x1D, 0x56, 66,0};

    public static byte[] SELECT_CYRILLIC_CHARACTER_CODE_TABLE = {0x1B, 0x74, 0x11};

    public static byte[] SELECT_BIT_IMAGE_MODE = {0x1B, 0x76, 0x30};
    public static byte[] SET_LINE_SPACING_24 = {0x1B, 0x33, 24};
    public static byte[] SET_LINE_SPACING_30 = {0x1B, 0x33, 30};

    public static byte[] TRANSMIT_DLE_PRINTER_STATUS = {0x10, 0x04, 0x01};
    public static byte[] TRANSMIT_DLE_OFFLINE_PRINTER_STATUS = {0x10, 0x04, 0x02};
    public static byte[] TRANSMIT_DLE_ERROR_STATUS = {0x10, 0x04, 0x03};
    public static byte[] TRANSMIT_DLE_ROLL_PAPER_SENSOR_STATUS = {0x10, 0x04, 0x04};
    public EmizorPrint(Context context)
    {
        this.context = context;
    }
    public void Imprimir(final Factura factura, final String ip, final int puerto)
    {
        Log.d(TAG,"imprimiendo con esc/bema");
//        String ip = "192.168.0.32";
//        int puerto = 9100;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Socket mSocket = new Socket(ip, puerto);
                    OutputStream mPrinter = mSocket.getOutputStream();
                    PrintWriter printer = new PrintWriter(mSocket.getOutputStream(), true);
//            Bitmap bmp = getQr("Para mi amiga Nadia que tu hermosa sonrisa siga iluminando el mundo Atte: David  ");
//            Bitmap bmp = getQr("Para mi amiga Carla que tu fortaleza y belleza perduren en el tiempo  Atte: David  ");
                    String datos =factura.getAccount().getNit()+"|"+factura.getInvoiceNumber()+"|"+factura.getNumAuto()+"|"+factura.getInvoiceDate()+"|"+factura.getAmount()+"|"+factura.getFiscal()+"|"+factura.getControlCode()+"|"+factura.getCliente().getNit()+"|0|"+redondeo((Double.parseDouble(factura.getSubtotal())-Double.parseDouble(factura.getAmount())),6)+"|"+redondeo((Double.parseDouble(factura.getSubtotal())-Double.parseDouble(factura.getAmount())),6);
                    Bitmap bmp = getQr(datos);
                    byte[] cmd = new byte[3];
                    byte[] imagenByte = null;
                    Vector prods = new Vector();
                    for(int i=0;i<factura.getInvoiceItems().size();i++)
                    {
                        InvoiceItem product = (InvoiceItem) factura.getInvoiceItems().get(i);
                        double subTotal = (Double.parseDouble(product.getCost())*Double.parseDouble(product.getQty()));

                        String  producto = ConstruirFila(""+product.getQty(),""+product.getCost(),""+redondeo(subTotal,2));
                        prods.addElement(producto);
                    }
                    Converter conv= new Converter();

                    mPrinter.write(INIT);
                    mPrinter.write(SELECT_FONT_B);
                    mPrinter.write(ALIGN_CENTER);
                    printer.println(factura.getAccount().getName());

                    mPrinter.write(SELECT_FONT_A);
                    mPrinter.write(ALIGN_CENTER);
                    printer.println(factura.getAddress1());
                    printer.println(factura.getAddress2());
                    printer.println("Factura");
                    printer.println("--------------------------------------------------");

                    printer.println("NIT: " + factura.getAccount().getNit());
                    printer.println("FACTURA No.: " + factura.getInvoiceNumber());
                    printer.println("AUTORIZACION No.: " + factura.getNumAuto());
                    printer.println("--------------------------------------------------");
                    mPrinter.write(ALIGN_LEFT);
                    printer.println(factura.getActividad());

                    printer.println("FECHA: "+factura.getInvoiceDate()+" Hora:"+ DateUtil.dateToString1());
                    printer.println("NIT/CI: "+factura.getCliente().getNit()+"    Cod.:"+factura.getCliente().getPublic_id());
                    printer.println("NOMBRE: " + factura.getCliente().getName());
                    printer.println(ConstruirFila("Cant.","Precio","Importe"));

                    for(int i=0;i<factura.getInvoiceItems().size();i++)
                    {
                        InvoiceItem invitem = (InvoiceItem) factura.getInvoiceItems().elementAt(i);

                        Vector vl = TextLine(invitem.getNotes(),36);
                        for(int y = 0;y<vl.size();y++)
                        {
                            String l = (String) vl.elementAt(y);
                            printer.println(l);
                        }
//                                        imprimir.printText(invitem.getNotes(), 1);
                        String  producto = (String) prods.elementAt(i);
                        printer.println(producto);
//                      imprimir.printBitmap(b);

                    }
                    printer.println("--------------------------------------------------");
                    printer.println("                  TOTAL: Bs "+factura.getAmount());

                    double descuento = Double.parseDouble(factura.getSubtotal())-Double.parseDouble(factura.getAmount());

                    printer.println("             DESCUENTOS: Bs "+redondeo(descuento,2)+" ");

                    printer.println("          MONTO A PAGAR: Bs "+factura.getAmount());
                    printer.println("SON: "+conv.getStringOfNumber(factura.getAmount()));


                    /**Impresion de imagen**/
                    PrintPic pg = new PrintPic();
                    pg.initCanvas(384);
                    pg.initPaint();
                    pg.drawImage(0, 0,bmp);
                    imagenByte = pg.printDraw();
                    mPrinter.write(ALIGN_CENTER);
//                    cmd[0] = 0x1b;
//                    cmd[1] = 0x76;
//                    cmd[2] = 0x30;
                    mPrinter.write(SELECT_BIT_IMAGE_MODE);
                    mPrinter.write(imagenByte);
                    mPrinter.write(ALIGN_LEFT);
                    printer.println(factura.getLaw());
                    printer.println("www.emizor.com");
                    mPrinter.write(FEED_PAPER_AND_CUT);

            /*-----------**/
                    //mPrinter.write(FEED_LINE);
//                    cmd[0] = 0x1b;
//                    cmd[1] = 0x61;
//                    cmd[2] = 1;
//                    mPrinter.write(cmd);
//
//                    printer.println("hola mundo XD");
//                    // mPrinter.write(FEED_PAPER_AND_CUT);
//                    cmd[0] = 0x1d;
//                    cmd[1] = 0x56;
//                    cmd[2] = 0;
//                    mPrinter.write(cmd);
                    //printer.write(String.valueOf(FEED_PAPER_AND_CUT));
//            mPrinter.write(cmd);

                    mPrinter.flush();
                    mPrinter.close();
                    mSocket.close();
                } catch (Exception e) {
                    Log.i("error: ", e.toString());
                }
            }
        }).start();

    }

    //---------------------------------------------------Modulo de Impresion--------------------------------------//
    public void Imprimir(Factura factura)
    {
        Converter conv= new Converter();
        Vector vnombre = TextLine("NOMBRE: " + factura.getCliente().getName(), 36);

        Vector literal = TextLine("SON: "+conv.getStringOfNumber(factura.getAmount()),34);
        Vector vactividad = TextLine(factura.getActividad(),34);
        Vector vtitulo =  TextLine(factura.getAccount().getName(),16);
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
//impresion en base respuesta
//        for(int i=0;i<factura.getInvoiceItems().size();i++)
//        {
//
//                InvoiceItem invitem = (InvoiceItem) factura.getInvoiceItems().elementAt(i);
//
////                                        String concepto =(String) conceptos.elementAt(i);
////                                            imprimir.printText(invitem.getNotes(), 1);
//
//
//                double subTotal = (Double.parseDouble(invitem.getCost())*Double.parseDouble(invitem.getQty()));
//                double costo =Double.parseDouble(invitem.getCost());
//
//
////                                            double c = Double.parseDouble(invitem.getQty());
//
//
////                                        ba = (byte[]) DetalleProductos.elementAt(i);
//                String  producto = ConstruirFila(""+invitem.getQty(),""+invitem.getCost(),""+redondeo(subTotal,2));
//                prods.addElement(producto);
//
//
//        }
        //impresion en base lista seleccionada
        for(int i=0;i<factura.getInvoiceItems().size();i++)
        {
            InvoiceItem product = (InvoiceItem) factura.getInvoiceItems().get(i);
            //InvoiceItem invitem = (InvoiceItem) factura.getInvoiceItems().elementAt(i);

//                                        String concepto =(String) conceptos.elementAt(i);
//                                            imprimir.printText(invitem.getNotes(), 1);


            double subTotal = (Double.parseDouble(product.getCost())*Double.parseDouble(product.getQty()));
//            double costo =Double.parseDouble(product.getCost());


//                                            double c = Double.parseDouble(invitem.getQty());


//                                        ba = (byte[]) DetalleProductos.elementAt(i);
            String  producto = ConstruirFila(""+product.getQty(),""+product.getCost(),""+redondeo(subTotal,2));
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
                    for(int j=0;j<vtitulo.size();j++)
                    {
                        String linea = (String) vtitulo.elementAt(j);
//                                        imprimir.printText(linea, 1);
                        imprimir.printText(ConstruirFilaA(linea), 2);
                    }
//                    imprimir.printText(factura.getAccount().getName(),2);

//                    imprimir.printText(ConstruirFilaA(factura.getAccount().getName()), 2);
//                                     try{
//                                         imprimir.printBitmap(this.ba.readImage(BMPGenerator.encodeBMP(imprimirTitulo(""))));
//
//                                     }catch(IOException e){}
//                                    imprimir.printTextWidthHeightZoom(ConstruirFilaA(), 2, 1);
//                                    imprimir.printText(ConstruirFila(factura.getAccount().getName()), 1);
                    String dir1 =factura.getAddress1();
                    String dir2=factura.getAddress2();

//                    imprimir.printText("           Casa Matriz");
                    imprimir.printText(ConstruirFila(dir1), 1);
                    imprimir.printText(ConstruirFila(dir2), 1);
//                                    imprimir.printText(ConstruirFila("SFC-001"), 1);
                    imprimir.printText(ConstruirFila("FACTURA"), 1);
                    imprimir.printBitmap(context.getApplicationContext().getResources().openRawResource(R.raw.linea));
                    //Datos de la Empresa
                    imprimir.printText("              NIT: " + factura.getAccount().getNit(), 1);
                    imprimir.printText("      FACTURA No.: "+factura.getInvoiceNumber(), 1);
                    imprimir.printText(" AUTORIZACION No.: "+factura.getNumAuto(), 1);
                    imprimir.printBitmap(context.getApplicationContext().getResources().openRawResource(R.raw.linea));
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
                    imprimir.printBitmap(context.getApplicationContext().getResources().openRawResource(R.raw.linea));
//


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



                    imprimir.printBitmap(context.getApplicationContext().getResources().openRawResource(R.raw.linea));

                    imprimir.printText("              TOTAL: Bs "+factura.getAmount(), 1);

                    double descuento = Double.parseDouble(factura.getSubtotal())-Double.parseDouble(factura.getAmount());

                    imprimir.printText("         DESCUENTOS: Bs "+redondeo(descuento,2)+" ", 1);

                    imprimir.printText("      MONTO A PAGAR: Bs "+factura.getAmount(),1);

                    for(int j=0;j<literal.size();j++)
                    {
                        String linea = (String) literal.elementAt(j);

                        imprimir.printText(linea, 1);
                    }


                    imprimir.printText("CODIGO DE CONTROL:"+factura.getControlCode(),1);
                    imprimir.printText("FECHA LIMITE EMISION:" + factura.getFechaLimite(), 1);

//                    imprimir.printBitmap(getResources().openRawResource(R.raw.bus_ticket_qr));
                    bmp_path = context.getApplicationContext().getFilesDir().getAbsolutePath()+"/qrcode.bmp";
                    try {
                        String datos =factura.getAccount().getNit()+"|"+factura.getInvoiceNumber()+"|"+factura.getNumAuto()+"|"+factura.getInvoiceDate()+"|"+factura.getAmount()+"|"+factura.getFiscal()+"|"+factura.getControlCode()+"|"+factura.getCliente().getNit()+"|0|"+redondeo((Double.parseDouble(factura.getSubtotal())-Double.parseDouble(factura.getAmount())),6)+"|"+redondeo((Double.parseDouble(factura.getSubtotal())-Double.parseDouble(factura.getAmount())),6);
//                        String datos =factura.getAccount().getNit()+"|"+factura.getInvoiceNumber()+"|"+factura.getNumAuto()+"|"+factura.getInvoiceDate()+"|"+factura.getAmount()+"|"+factura.getFiscal()+"|"+factura.getControlCode()+"|"+factura.getCliente().getNit()+"|0|"+redondeo((Double.parseDouble(factura.getSubtotal())-Double.parseDouble(factura.getAmount())),6)+"|"+redondeo((Double.parseDouble(factura.getSubtotal())-Double.parseDouble(factura.getAmount())),6);

                        if (AndroidBmpUtil.save(getQr(datos), bmp_path))
                        {
                            Log.e("guardando qr", "sin Errores :) soy muy pendejo XD ");
                        }
                        else
                        {
                            Log.e("guardando qr", "Error al generar qr mensaje: por queeeeeeeeeeeeeee XD jajaja");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
//
                    imprimir.printBitmap(bmp_path);
//                                    imprimir.printEndLine();
//                                    imprimir.printText("CSD:"+operador.getId()+" "+operador.getUsuario() +"-"+factura.getDatecom(), 1);
//                                    imprimir.printText("CSD:143 farbo-18:00:35", 1);


                    try {

//                        imprimir.printBitmap(bmp_path);


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
                    //imprimiendo leyenda
                    Vector vlaw = TextLine(factura.getLaw(), 36);
                    for(int y = 0;y<vlaw.size();y++)
                    {
                        String l = (String) vlaw.elementAt(y);
                        imprimir.printText(l, 1);
                    }
                    imprimir.printBitmap(context.getApplicationContext().getResources().openRawResource(R.raw.leyenda_generica));


//                    imprimir.printBitmap(deviceOps.readImage("/linea.bmp", 0));
                    imprimir.printBitmap(context.getApplicationContext().getResources().openRawResource(R.raw.pie_de_pagina));
//                    imprimir.printText(ConstruirFila("www.emizor.com"), 1);
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
        int size = (18-cad1.length())/2;
        for(int i=0;i<size;i++)
        {
            fila = espacio+fila ;
        }

        return fila;
    }
    public String ConstruirFila(String cantidad,String concepto,String monto)
    {
        String linea=""+cantidad+"     "+concepto;
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
        int size = (34-cad1.length())/2;
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

    public static String convertiraISO(String s) {
        String out = null;
        try {
            out = new String(s.getBytes("ISO-8859-1"), "UTF-8");
        } catch (java.io.UnsupportedEncodingException e) {

            return null;
        }
        return out;
    }
    public Bitmap getQr(String texto)
    {
        String qrInputText = texto;
        Log.v(TAG, qrInputText);
        Bitmap bitmap=null;
        //Find screen size
        WindowManager manager = (WindowManager) context.getSystemService(context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        int width = point.x;
        int height = point.y;
        int smallerDimension = width < height ? width : height;
        //smallerDimension = smallerDimension * 3/4;//para MobiPrint3
        smallerDimension = smallerDimension * 1/3;//para Esc/bema

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
