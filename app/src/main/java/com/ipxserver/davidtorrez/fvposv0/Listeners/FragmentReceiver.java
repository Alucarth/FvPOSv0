package com.ipxserver.davidtorrez.fvposv0.Listeners;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.ipxserver.davidtorrez.fvposv0.PrincipalActivity;
import com.ipxserver.davidtorrez.fvposv0.adapter.GridbarAdapter;

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
        main.cambiarFragmento(fragment_id);

    }
}
