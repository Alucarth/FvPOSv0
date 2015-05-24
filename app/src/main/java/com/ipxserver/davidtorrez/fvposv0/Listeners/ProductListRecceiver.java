package com.ipxserver.davidtorrez.fvposv0.Listeners;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.ipxserver.davidtorrez.fvposv0.models.Product;

import java.util.ArrayList;

/**
 * Created by keyrus on 23-05-15.
 */
public class ProductListRecceiver  extends BroadcastReceiver
{
    ArrayList<Product> listaSeleccionados;
    public ProductListRecceiver()
    {

    }
    @Override
    public void onReceive(Context context, Intent intent) {

    }
}
