package com.ipxserver.davidtorrez.fvposv0.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.ipxserver.davidtorrez.fvposv0.fragments.FragmentGrid;
import com.ipxserver.davidtorrez.fvposv0.fragments.FragmentLista;

/**
 * Created by David Torrez on 13/05/2015.
 */
public class PagerAdapter extends FragmentPagerAdapter
{
    //Todo: antes de esto pasar el arrya list del objeto con toda la lista
    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        //Todo: Colocar la lista de objetos utilizados dentro del array de objetos
        String titulo=null;
        switch(position)
        {
            case 0: titulo ="Llista de Pedidos";
                break;
            case 1: titulo ="lista grid";
                break;
            default: titulo="sin fragmento";
        }
//        return super.getPageTitle(position);
        return titulo;
    }

    @Override
    public Fragment getItem(int i) {
        switch (i)
        {
            case 0: return new FragmentLista();

            case 1: return new FragmentGrid();
            default: return null;
        }


    }

    @Override
    public int getCount() {
        return 2;
    }
}
