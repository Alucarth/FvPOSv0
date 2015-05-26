package com.ipxserver.davidtorrez.fvposv0.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.ipxserver.davidtorrez.fvposv0.Listeners.ProductReceiver;
import com.ipxserver.davidtorrez.fvposv0.R;
import com.ipxserver.davidtorrez.fvposv0.adapter.GridAdapter;
import com.ipxserver.davidtorrez.fvposv0.models.Product;

import java.util.ArrayList;


/**
 * Created by David Torrez on 13/05/2015.
 */
public class FragmentGrid extends Fragment implements AdapterView.OnItemClickListener
{
    View rootView;
    GridView gv;
    GridAdapter gridAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_grid,container,false);
        gv = (GridView) rootView.findViewById(R.id.gridview);


        gridAdapter = new GridAdapter(rootView.getContext());
        gv.setAdapter(gridAdapter);
        gv.setOnItemClickListener(this);
//        gv.setOnClickListener(new AdapterView.OnItemClickListener() {
//            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
////                Toast.makeText(gv, "" + position,Toast.LENGTH_SHORT).show();
//                Product prod = parent.getSelectedItem();
//                Log.d("david",""+position);
////     Toast.makeText(getApplicationContext(),gv.get(position).getTitle(), Toast.LENGTH_SHORT).show();
//            }
//        });
        setHasOptionsMenu(true);
        return rootView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Product pro = (Product) gridAdapter.getItem(position);

        int cant = Integer.parseInt(pro.getQty().toString())+1;
        pro.setQty(""+cant);
//        pro.setCost(""+pro.getCost()+"x "+pro.getQty());
        gridAdapter.notifyDataSetChanged();

        //enviando al broadcast
        Intent intent = new Intent("addproducto");
        intent.putExtra("operacion", ProductReceiver.PRODUCTO_AGREGADO);
        intent.putExtra("cantidad",cant);
        intent.putExtra("producto",pro);
        getActivity().sendBroadcast(intent);

//        Toast.makeText(view.getContext(),""+pro.getQty(), Toast.LENGTH_SHORT).show();
    }
    public ArrayList<Product> getLista()
    {
        ArrayList<Product> listaSeleccionados=null;


        return listaSeleccionados;
    }
}
