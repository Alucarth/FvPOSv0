package com.ipxserver.davidtorrez.fvposv0.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.ipxserver.davidtorrez.fvposv0.Listeners.ProductReceiver;
import com.ipxserver.davidtorrez.fvposv0.R;
import com.ipxserver.davidtorrez.fvposv0.adapter.GridbarAdapter;
import com.ipxserver.davidtorrez.fvposv0.adapter.PagerAdapter;

/**
 * Created by David-Pc on 25/05/2015.
 */
public class FragmentTabswipe extends Fragment
{
    private PagerAdapter pagerAdapter;
    private GridbarAdapter gridbarAdapter;
    private GridView gridbar;
    private ProductReceiver reciver;
    ViewPager viewPager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        pagerAdapter = new PagerAdapter(this.getChildFragmentManager());
//
        View rootView = inflater.inflate(R.layout.frament_tabswipe,container,false);
        viewPager =(ViewPager) rootView.findViewById(R.id.pager);
        viewPager.setAdapter(pagerAdapter);
//
//        //Todo: Revisar el tipo de contexto al cual pertenese el gridbarAdapter
        gridbarAdapter = new GridbarAdapter(rootView.getContext());
        gridbar= (GridView) rootView.findViewById(R.id.barraSaldo);
        gridbar.setAdapter(gridbarAdapter);
//        gridbar.setVisibility(View.INVISIBLE);
        setHasOptionsMenu(true);
        return rootView;
    }

    public GridbarAdapter getGridbarAdapter() {
        if(gridbarAdapter == null)
        {

        }
        return gridbarAdapter;
    }

    public GridView getGridbar() {
        return gridbar;
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        reciver = new ProductReceiver(gridbarAdapter,null,this);
//        getActivity().registerReceiver(reciver, new IntentFilter("addproducto"));
//
//    }
//    @Override
//    public void onPause() {
//        super.onPause();
//
//        getActivity().unregisterReceiver(reciver);
//    }


}
