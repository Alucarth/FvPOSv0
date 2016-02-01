package com.ipxserver.davidtorrez.fvpos.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.ipxserver.davidtorrez.fvpos.models.Factura;
import com.ipxserver.davidtorrez.fvpos.models.FacturaCardItem;

import java.util.ArrayList;

/**
 * Created by David Torrez on 26/10/2015.
 */
public class SqliteController extends SQLiteOpenHelper {
    public static String TAG="DavidDB";
    public SqliteController(Context applicationcontext) {
        super(applicationcontext, "ipx.db", null, 1);
        Log.d(TAG, "Created");
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query;

        Log.d(TAG, "tabla formulario Created");
        query = "CREATE TABLE cuentas (id INTEGER PRIMARY KEY, name TEXT, branch_id TEXT, branch_name TEXT, subdominio TEXT);";
        sqLiteDatabase.execSQL(query);

        query = "CREATE TABLE invoices (id INTEGER PRIMARY KEY AUTOINCREMENT, invoice_number TEXT, invoice_date TEXT, amount TEXT,invoice_json TEXT);";
        sqLiteDatabase.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String query;
        query = "DROP TABLE IF EXISTS cuentas";
        sqLiteDatabase.execSQL(query);
        query = "DROP TABLE IF EXISTS invoices";
        sqLiteDatabase.execSQL(query);
    }
    public void insertCuenta(String name, String branch_id, String branc_name,String subdominio) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("branch_id", branch_id);
        values.put("branch_name", branc_name);
        values.put("subdominio",subdominio);
        database.insert("cuentas", null, values);
        database.close();
    }
    public String  getSucursal() {
        String selectQuery = "SELECT  * FROM cuentas where id=1";

        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        String brach_id=null;
        cursor.moveToFirst();
        brach_id =  cursor.getString(2);
//        if (cursor.moveToFirst()) {
//            do {
//                Log.w(TAG, "id:" + cursor.getString(0));
//                Log.w(TAG, "nombre:" + cursor.getString(1));
//                Log.w(TAG, "estructura:" + cursor.getString(2));
//
//            } while (cursor.moveToNext());
//        }
        return brach_id;
    }
    public String  getSubdominio() {
        String selectQuery = "SELECT  * FROM cuentas where id=1";

        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        String subdominio=null;
        cursor.moveToFirst();
        subdominio =  cursor.getString(4);
//        if (cursor.moveToFirst()) {
//            do {
//                Log.w(TAG, "id:" + cursor.getString(0));
//                Log.w(TAG, "nombre:" + cursor.getString(1));
//                Log.w(TAG, "estructura:" + cursor.getString(2));
//
//            } while (cursor.moveToNext());
//        }
        return subdominio;
    }
    public void modificarSucursal(String branch_id) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("branch_id", branch_id);
        database.update("cuentas", values, "id=1", null);
        database.close();
    }
    public void insertInvoice(String json_invoice, Factura factura)
    {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        Log.i(TAG,"json_invoice"+json_invoice);
        Log.i(TAG,"numero de factura "+factura.getInvoiceNumber());
        Log.i(TAG,"fecha "+factura.getInvoiceDate());
        Log.i(TAG,"amount"+factura.getAmount());
        values.put("invoice_number", factura.getInvoiceNumber());
        values.put("invoice_date",factura.getInvoiceDate());
        values.put("amount",factura.getAmount());
        values.put("invoice_json",json_invoice);

        database.insert("invoices", null, values);
        database.close();

    }
    public ArrayList getFacturas()
    {
        ArrayList<FacturaCardItem> facturas = new ArrayList<>();

        String selectQuery = "SELECT  * FROM invoices ORDER BY id DESC";
        SQLiteDatabase database = this.getWritableDatabase();

        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Log.w(TAG, "id: " + cursor.getString(0));
                Log.w(TAG, "invoice_number: " + cursor.getString(1));
                Log.w(TAG, "inovices_date: " + cursor.getString(2));
                Log.w(TAG, "amount: "+cursor.getString(3));
                Log.w(TAG, "invoice_json" + cursor.getString(4));
                FacturaCardItem item = new FacturaCardItem();
                item.setId(cursor.getString(0));
                item.setNumero(cursor.getString(1));
                item.setFecha(cursor.getString(2));
                item.setMonto(cursor.getString(3));
                item.setJson_invoice(cursor.getString(4));

                facturas.add(item);

            } while (cursor.moveToNext());
        }


        return facturas;
    }

}
