package com.ipxserver.davidtorrez.fvposv0.Listeners;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.ipxserver.davidtorrez.fvposv0.PrincipalActivity;
import com.ipxserver.davidtorrez.fvposv0.adapter.GridAdapter;
import com.ipxserver.davidtorrez.fvposv0.adapter.GridbarAdapter;
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
//   ArrayAdapter <Product> arrayAdapter;
    ArrayList<Product> listaProductos;
    private final GridbarAdapter gridAdapter;

    public ProductReceiver(GridbarAdapter gridAdapter)
    {
        this.gridAdapter = gridAdapter;

        listaProductos = new ArrayList<Product>();

    }
    @Override
    public void onReceive(Context context, Intent intent) {
        int cantidad= intent.getIntExtra("cantidad",-1);
        Product p = (Product) intent.getSerializableExtra("producto");
        adcionarProducto(p);
        gridAdapter.incrementar(cantidad);
        Log.i("David","on recieve se activo XD cantidad="+cantidad);
    }
    private void adcionarProducto(Product producto)
    {
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
