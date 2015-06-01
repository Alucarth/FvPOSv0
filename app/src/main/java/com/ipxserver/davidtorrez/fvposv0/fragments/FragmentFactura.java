package com.ipxserver.davidtorrez.fvposv0.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;

import com.ipxserver.davidtorrez.fvposv0.R;
import com.ipxserver.davidtorrez.fvposv0.adapter.ListAdapter;
import com.ipxserver.davidtorrez.fvposv0.models.Product;

import java.util.ArrayList;

/**
 * Created by keyrus on 23-05-15.
 */
public class FragmentFactura extends Fragment
{
    public ArrayList<Product> listaSeleccionados;
//    public ProductListAdapter productListAdapter;
    public ListAdapter listAdapter;
//    FacturaReceiver facturaReceiver;
   public static FragmentFactura newInstance(ArrayList<Product> listaSeleccionados)
   {
       FragmentFactura  fragmentFactura = new FragmentFactura();
       Bundle arg = new Bundle();
       //Todo: Adicionar un parametro de monto total para tenerlo todo en el fragmento

       arg.putSerializable("lista",listaSeleccionados);
        fragmentFactura.setArguments(arg);
       return fragmentFactura;
   }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        listaSeleccionados = (ArrayList<Product>)getArguments().getSerializable("lista");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_factura,container,false );

        ListView lista =(ListView) rootView.findViewById(R.id.listProductos);

//        listaSeleccionados = new ArrayList<Product>();
//        for( int i=0;i<10;i++)
//        {
//            Product product = new Product();
//            product.setId(""+i);
//            product.setQty(""+i);
//            product.setNotes("Producto "+i);
//            product.setCost(""+(i+10));
//            listaSeleccionados.add(product);
//        }
//        productListAdapter = new ProductListAdapter(rootView.getContext(),listaSeleccionados);
        listAdapter = new ListAdapter(rootView.getContext(),listaSeleccionados);

        lista.setAdapter(listAdapter);
        setHasOptionsMenu(true);

        ImageButton buttonUser = (ImageButton) rootView.findViewById(R.id.button_user);
        buttonUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getChildFragmentManager();
                FragmentUserDialog fragmentUserDialog = new FragmentUserDialog();
                fragmentUserDialog.show(fm,"fragment_factura");

            }
        });

        return rootView;
    }
//
//    @Override
//    public void onResume() {
//        facturaReceiver = new FacturaReceiver(this);
//        getActivity().registerReceiver(facturaReceiver, new IntentFilter("cast_factura"));
//        Log.i("David","resume Factura aqui esta escuchando");
//        super.onResume();
//    }
//
//    @Override
//    public void onPause() {
//        getActivity().unregisterReceiver(facturaReceiver);
//        super.onPause();
//    }
}
