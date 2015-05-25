package com.ipxserver.davidtorrez.fvposv0.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;

import com.ipxserver.davidtorrez.fvposv0.Listeners.ProductReceiver;
import com.ipxserver.davidtorrez.fvposv0.R;

/**
 * Created by David Torrez on 13/05/2015.
 */
public class FragmentLista extends Fragment
{
    View rootView;
    ListView lista;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_lista,container,false);
        lista = (ListView) rootView.findViewById(R.id.listProductos);
        ImageButton imageButton= (ImageButton) rootView.findViewById(R.id.imageButton);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent("addproducto");

                intent.putExtra("operacion", ProductReceiver.FRAGMENT_TABSWIPE);
                getActivity().sendBroadcast(intent);
            }
        });
        setHasOptionsMenu(true);

        return rootView;

    }
}
