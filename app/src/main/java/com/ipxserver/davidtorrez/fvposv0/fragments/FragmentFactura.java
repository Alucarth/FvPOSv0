package com.ipxserver.davidtorrez.fvposv0.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;

import com.ipxserver.davidtorrez.fvposv0.R;
import com.ipxserver.davidtorrez.fvposv0.adapter.GridbarAdapter;
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
    public int monto;
//    FacturaReceiver facturaReceiver;
   public static FragmentFactura newInstance(ArrayList<Product> listaSeleccionados, int monto)
   {
       FragmentFactura  fragmentFactura = new FragmentFactura();
       Bundle arg = new Bundle();
       //Todo: Adicionar un parametro de monto total para tenerlo todo en el fragmento

       arg.putSerializable("lista",listaSeleccionados);
       arg.putInt("monto",monto);
        fragmentFactura.setArguments(arg);
       return fragmentFactura;
   }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        listaSeleccionados = (ArrayList<Product>)getArguments().getSerializable("lista");
        monto = getArguments().getInt("monto");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_factura,container,false );

        ListView lista =(ListView) rootView.findViewById(R.id.listProductos);

//
        listAdapter = new ListAdapter(rootView.getContext(),listaSeleccionados);

        lista.setAdapter(listAdapter);

        GridView gridBar = (GridView) rootView.findViewById(R.id.barra_factura_saldo);
        GridbarAdapter gridbarAdapter = new GridbarAdapter(rootView.getContext());
        gridBar.setAdapter(gridbarAdapter);

        gridbarAdapter.incrementar(monto);


        setHasOptionsMenu(true);



        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_factura,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       switch (item.getItemId())
       {
           case R.id.action_factura_cancelar: atras();
               return true;
           case R.id.action_factura_cliente: adicionarCliente();
               return true;
           case R.id.action_factura_imprimir: imprimir();
               return true;
           default: return super.onOptionsItemSelected(item);

       }

    }

    private void imprimir() {
    }

    private void adicionarCliente() {
        FragmentManager fm = getChildFragmentManager();
        FragmentUserDialog fragmentUserDialog = new FragmentUserDialog();
        fragmentUserDialog.show(fm,"fragment_factura");
    }

    private void atras() {

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
