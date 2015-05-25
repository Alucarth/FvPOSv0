package com.ipxserver.davidtorrez.fvposv0;

import android.app.ActionBar;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;

import com.ipxserver.davidtorrez.fvposv0.Listeners.ProductReceiver;
import com.ipxserver.davidtorrez.fvposv0.adapter.GridbarAdapter;
import com.ipxserver.davidtorrez.fvposv0.adapter.PagerAdapter;
import com.ipxserver.davidtorrez.fvposv0.fragments.FragmentFactura;
import com.ipxserver.davidtorrez.fvposv0.fragments.FragmentLista;
import com.ipxserver.davidtorrez.fvposv0.fragments.FragmentTabswipe;


public class PrincipalActivity extends FragmentActivity {

    PagerAdapter pagerAdapter;
    ViewPager viewPager;
    GridbarAdapter gridbarAdapter;
    GridView gridbar;
    ProductReceiver reciver;
    FragmentLista fragmentLista=null;
    FragmentFactura fragmentFactura=null;
    FragmentTabswipe fragmentTabswipe=null;


    private static final int FRAGMENT_FACTURA=5;
    private static final int FRAGMENT_TABSWIPE=6;
    private static final int FRAGMENT_LISTA=7;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
       inicializarContenido();
        cargarFragmento(getFragmentLista());

    }
    public void cambiarFragmento(int fragment_id)
    {
        switch (fragment_id)
        {
            case FRAGMENT_FACTURA: cargarFragmento(getFragmentFactura());
                break;
            case FRAGMENT_TABSWIPE: cargarFragmento(getFragmentTabswipe());
                break;
            case FRAGMENT_LISTA: cargarFragmento(getFragmentLista());;
                break;
        }
    }
    private void cargarFragmento(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.contenedor_fragmnet, fragment);//reemplazar el id con el del contenedor
        transaction.commit();
    }

    public void inicializarContenido()
    {
        ActionBar actionBar = getActionBar();
        actionBar.show();
//        pagerAdapter = new PagerAdapter(getSupportFragmentManager());
//
//        viewPager =(ViewPager) findViewById(R.id.pager);
//        viewPager.setAdapter(pagerAdapter);
//
//        //Todo: Revisar el tipo de contexto al cual pertenese el gridbarAdapter
//        gridbarAdapter = new GridbarAdapter(getApplicationContext());
//        gridbar= (GridView) findViewById(R.id.barraSaldo);
//        gridbar.setAdapter(gridbarAdapter);
//        gridbar.setVisibility(View.INVISIBLE);
    }
    public FragmentLista getFragmentLista() {
        if(fragmentLista==null)
        {
            fragmentLista = new FragmentLista();
        }

        return fragmentLista;
    }

    public FragmentFactura getFragmentFactura() {
        if(fragmentFactura==null)
        {
            fragmentFactura = new FragmentFactura();
        }
        return fragmentFactura;
    }

    public FragmentTabswipe getFragmentTabswipe() {
        if(fragmentTabswipe==null)
        {
            fragmentTabswipe = new FragmentTabswipe();
        }
        return fragmentTabswipe;
    }

    @Override
    protected void onResume() {
        super.onResume();
        reciver = new ProductReceiver(gridbarAdapter,this);
        registerReceiver(reciver,new IntentFilter("addproducto"));
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(reciver);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_principal, menu);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
