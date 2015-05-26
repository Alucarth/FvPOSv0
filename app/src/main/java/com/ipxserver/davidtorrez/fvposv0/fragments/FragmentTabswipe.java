package com.ipxserver.davidtorrez.fvposv0.fragments;

import android.content.IntentFilter;
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
//    @Override
//    public void onPause() {
//        super.onPause();
//        getActivity().unregisterReceiver(reciver);
//    }
//
//
//    @Override
//    public void onTabSelected(ActionBar.Tab tab, android.app.FragmentTransaction ft) {
//        viewPager.setCurrentItem(tab.getPosition());
//    }
//
//    @Override
//    public void onTabUnselected(ActionBar.Tab tab, android.app.FragmentTransaction ft) {
//
//    }
//
//    @Override
//    public void onTabReselected(ActionBar.Tab tab, android.app.FragmentTransaction ft) {
//
//    }
}
