package com.ipxserver.davidtorrez.fvposv0.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ipxserver.davidtorrez.fvposv0.R;

/**
 * Created by David-Pc on 09/06/2015.
 */
public class FragmentEmpresa extends Fragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_empresa,container,false);

        return rootView;
    }
}
