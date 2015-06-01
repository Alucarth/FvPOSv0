package com.ipxserver.davidtorrez.fvposv0.Listeners;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.ipxserver.davidtorrez.fvposv0.PrincipalActivity;
import com.ipxserver.davidtorrez.fvposv0.adapter.GridbarAdapter;
import com.ipxserver.davidtorrez.fvposv0.models.Product;

import java.util.ArrayList;

/**
 * Created by David-Pc on 26/05/2015.
 */
public class FragmentReceiver extends BroadcastReceiver
{
    public static final int FRAGMENT_FACTURA=5;
    public static final int FRAGMENT_TABSWIPE=6;
    public static final int FRAGMENT_LISTA=7;

    private final GridbarAdapter gridAdapter;
    private final PrincipalActivity main;
    private ArrayList<Product> listaProductos=null;
    private int monto;
    public FragmentReceiver( GridbarAdapter gridAdapter,PrincipalActivity main)
    {
        this.gridAdapter = gridAdapter;
        this.main = main;
    }
    @Override
    public void onReceive(Context context, Intent intent) {
//        int operacion = intent.getIntExtra("operacion",-1);

        cambiarFragmento(intent);
    }
    private void cambiarFragmento(Intent intent) {
        int fragment_id = intent.getIntExtra("operacion",-1);

        if(fragment_id==FRAGMENT_FACTURA)
        {
            listaProductos = (ArrayList<Product>)intent.getSerializableExtra("lista_seleccionados");
            monto = intent.getIntExtra("monto",-1);
            Log.i("David", "tamao del list product size " + listaProductos.size());
//            Product product = new Product();
//            product.setCost("23 ");
//            product.setQty("222");
//            product.setId("pa1");
//            product.setNotes("its work2");
//            main.getFragmentFactura().listAdapter.adcionarProducto(product);
//
        }
        main.cambiarFragmento(fragment_id);



    }

    public ArrayList<Product> getListaProductos() {
        Log.i("David", "retornando lista  size " + listaProductos.size());
        return listaProductos;
    }

    public int getMonto() {
        return monto;
    }
}
