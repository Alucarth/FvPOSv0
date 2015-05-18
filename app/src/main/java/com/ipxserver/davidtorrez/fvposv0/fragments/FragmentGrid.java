package com.ipxserver.davidtorrez.fvposv0.fragments;

import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.ipxserver.davidtorrez.fvposv0.R;
import com.ipxserver.davidtorrez.fvposv0.adapter.GridAdapter;



/**
 * Created by David Torrez on 13/05/2015.
 */
public class FragmentGrid extends Fragment
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
        return rootView;
    }
}
