package com.ipxserver.davidtorrez.fvposv0.Listeners;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.ArrayAdapter;

import com.ipxserver.davidtorrez.fvposv0.models.Product;

import java.util.ArrayList;

/**
 * Created by David Torrez on 20/05/2015.
 */
public class ProductReceiver extends BroadcastReceiver
{

    public static final int PRODUCTO_AGREGADO=0;
    public static final int PRODUCTO_ELIMINADO=1;
    public static final int PRODUCTO_ACTUALIZADO=2;
   ArrayAdapter <Product> arrayAdapter;

    public ProductReceiver(ArrayAdapter<Product> arrayAdapter)
    {
        this.arrayAdapter =arrayAdapter;

    }
    @Override
    public void onReceive(Context context, Intent intent) {

    }
}
