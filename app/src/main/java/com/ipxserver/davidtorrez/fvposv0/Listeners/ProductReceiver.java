package com.ipxserver.davidtorrez.fvposv0.Listeners;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.ipxserver.davidtorrez.fvposv0.PrincipalActivity;
import com.ipxserver.davidtorrez.fvposv0.adapter.GridbarAdapter;
import com.ipxserver.davidtorrez.fvposv0.fragments.FragmentTabswipe;
import com.ipxserver.davidtorrez.fvposv0.models.Product;

import java.util.ArrayList;

/**
 * Created by David Torrez on 20/05/2015.
 */
public class ProductReceiver extends BroadcastReceiver
{
    //Todo: ver si esto ayudara en el envio del broadcast
    public static final int PRODUCTO_AGREGADO=0;

    public static final int PRODUCTO_ELIMINADO=1;
    public static final int PRODUCTO_ACTUALIZADO=2;
    public static final int FRAGMENT_FACTURA=5;
    public static final int FRAGMENT_TABSWIPE=6;
    public static final int FRAGMENT_LISTA=7;
//   ArrayAdapter <Product> arrayAdapter;
    ArrayList<Product> listaProductos;
    private final GridbarAdapter gridAdapter;
    private final PrincipalActivity main;
    private final FragmentTabswipe fragmentTabswipe;

    public ProductReceiver(GridbarAdapter gridAdapter,PrincipalActivity main,FragmentTabswipe fragmentTabswipe)
    {
        this.gridAdapter = gridAdapter;
        this.main = main;
        this.fragmentTabswipe =fragmentTabswipe;
        listaProductos = new ArrayList<Product>();

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        int operacion = intent.getIntExtra("operacion",-1);
        switch (operacion)
        {
            case PRODUCTO_AGREGADO: agregarProducto(intent);
                break;
            case FRAGMENT_FACTURA: cambiarFragmento(intent);
                break;

        }

        Log.i("David","on recieve se activo XD metodo "+operacion);
    }

    private void cambiarFragmento(Intent intent) {
        int fragment_id = intent.getIntExtra("operacion",-1);
        main.cambiarFragmento(fragment_id);

    }

    private void agregarProducto(Intent intent)
    {
        int cantidad= intent.getIntExtra("cantidad",-1);
        Product producto = (Product) intent.getSerializableExtra("producto");

        //gridAdapter.incrementar(cantidad);
        boolean enlista=false;
        for(int i=0;i<listaProductos.size();i++)
        {
            Product p = (Product) listaProductos.get(i);
            if(p.getId().equals(producto.getId()))
            {
                enlista = true;
                p.setQty(producto.getQty());
            }

        }
        if(!enlista)
        {
            listaProductos.add(producto);
        }

    }

}
