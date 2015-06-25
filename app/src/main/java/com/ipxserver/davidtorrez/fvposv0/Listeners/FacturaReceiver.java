package com.ipxserver.davidtorrez.fvposv0.Listeners;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.ipxserver.davidtorrez.fvposv0.adapter.FacturaCardAdapter;
import com.ipxserver.davidtorrez.fvposv0.models.FacturaCardItem;

/**
 * Created by David-Pc on 25/06/2015.
 */
public class FacturaReceiver extends BroadcastReceiver
{
    private final FacturaCardAdapter facturaCardAdapter;
    public FacturaReceiver(FacturaCardAdapter facturaCardAdapter)
    {
        this.facturaCardAdapter = facturaCardAdapter;
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        FacturaCardItem facturaCardItem = (FacturaCardItem)intent.getSerializableExtra("factura_item");
        facturaCardAdapter.addFactura(facturaCardItem);
    }
}
