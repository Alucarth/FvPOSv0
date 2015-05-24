package com.ipxserver.davidtorrez.fvposv0.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.ipxserver.davidtorrez.fvposv0.R;
import com.ipxserver.davidtorrez.fvposv0.adapter.ProductListAdapter;
import com.ipxserver.davidtorrez.fvposv0.models.Product;

import java.util.ArrayList;

/**
 * Created by keyrus on 23-05-15.
 */
public class FragmentFactura extends Fragment
{
    ArrayList<Product> listaSeleccionados;
    ProductListAdapter productListAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_factura,container,false );

        ListView lista =(ListView) rootView.findViewById(R.id.listProductos);

        listaSeleccionados = new ArrayList<Product>();
        for( int i=0;i<10;i++)
        {
            Product product = new Product();
            product.setId(""+i);
            product.setQty(""+i);
            product.setNotes("Producto "+i);
            product.setCost(""+(i+10));
            listaSeleccionados.add(product);
        }
        productListAdapter = new ProductListAdapter(rootView.getContext(),listaSeleccionados);
        lista.setAdapter(productListAdapter);

        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
