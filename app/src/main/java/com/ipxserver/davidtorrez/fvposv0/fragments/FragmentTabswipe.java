package com.ipxserver.davidtorrez.fvposv0.fragments;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.ipxserver.davidtorrez.fvposv0.Listeners.FragmentReceiver;
import com.ipxserver.davidtorrez.fvposv0.Listeners.ProductReceiver;
import com.ipxserver.davidtorrez.fvposv0.R;
import com.ipxserver.davidtorrez.fvposv0.adapter.GridbarAdapter;
import com.ipxserver.davidtorrez.fvposv0.adapter.PagerAdapter;

/**
 * Created by David-Pc on 25/05/2015.
 */
public class FragmentTabswipe extends Fragment //implements ActionBar.TabListener
{
    private PagerAdapter pagerAdapter;
    private GridbarAdapter gridbarAdapter;
    private GridView gridbar;
    private ProductReceiver reciver;
    //ActionBar actionBar;
    ViewPager viewPager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        pagerAdapter = new PagerAdapter(this.getChildFragmentManager());
//
        View rootView = inflater.inflate(R.layout.frament_tabswipe, container, false);
        viewPager =(ViewPager) rootView.findViewById(R.id.pager);
        viewPager.setAdapter(pagerAdapter);
//        actionBar = getActivity().getActionBar();
//
//        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
//        actionBar.setHomeButtonEnabled(false);
////        inicializarTabs();
//        viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
//
//            @Override
//            public void onPageSelected(int position) {
//                actionBar.setSelectedNavigationItem(position);
//            }
//        });
//
//        for (int i = 0; i < pagerAdapter.getCount(); i++) {
//            actionBar.addTab(
//                    actionBar.newTab()
//                            .setText(pagerAdapter.getPageTitle(i))
//                            .setTabListener(this));
//        }
//
//        //Todo: Revisar el tipo de contexto al cual pertenese el gridbarAdapter
        gridbarAdapter = new GridbarAdapter(rootView.getContext());
        gridbar= (GridView) rootView.findViewById(R.id.barraSaldo);
        gridbar.setAdapter(gridbarAdapter);
//        gridbar.setVisibility(View.INVISIBLE);


        setHasOptionsMenu(true);
        return rootView;
    }

//    private void inicializarTabs() {
//        final ActionBar actionBar = getActivity().getActionBar();
//        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
//        actionBar.setHomeButtonEnabled(false);
//
//    }


    @Override
    public void onResume() {
        super.onResume();
        reciver = new ProductReceiver(gridbarAdapter,this);
        getActivity().registerReceiver(reciver, new IntentFilter("addproducto"));

    }
    @Override
    public void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(reciver);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_tabswipe, menu);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.i("David", "entro al item selected");
        switch (item.getItemId())
        {
            case R.id.action_cancelar: cancelarFactura(item); return true;
            case R.id.action_facturar: llamarFacturar(item); return true;
            default:  return super.onOptionsItemSelected(item);
        }

    }

    private void llamarFacturar(MenuItem item) {
        Intent intent = new Intent("cambiar_fragmento");

        intent.putExtra("operacion", FragmentReceiver.FRAGMENT_FACTURA);
        getActivity().sendBroadcast(intent);
    }

    private void cancelarFactura(MenuItem item) {


        Intent intent = new Intent("cambiar_fragmento");

        intent.putExtra("operacion", FragmentReceiver.FRAGMENT_LISTA);
        getActivity().sendBroadcast(intent);
        Log.i("David","entro a la funcion de cancelar factura");
    }
}
