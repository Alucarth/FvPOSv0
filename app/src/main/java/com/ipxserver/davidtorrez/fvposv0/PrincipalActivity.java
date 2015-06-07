package com.ipxserver.davidtorrez.fvposv0;


import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.ipxserver.davidtorrez.fvposv0.Listeners.FragmentReceiver;
import com.ipxserver.davidtorrez.fvposv0.adapter.GridbarAdapter;
import com.ipxserver.davidtorrez.fvposv0.fragments.FragmentFactura;
import com.ipxserver.davidtorrez.fvposv0.fragments.FragmentLista;
import com.ipxserver.davidtorrez.fvposv0.fragments.FragmentTabswipe;


public class PrincipalActivity extends ActionBarActivity {


    GridbarAdapter gridbarAdapter;

   FragmentReceiver reciver;
    FragmentLista fragmentLista=null;
//    FragmentFactura fragmentFactura=null;
    FragmentTabswipe fragmentTabswipe=null;


    private static final int FRAGMENT_FACTURA=5;
    private static final int FRAGMENT_TABSWIPE=6;
    private static final int FRAGMENT_LISTA=7;

    //Variables para el navigation drawer
    private DrawerLayout drawerLayout;
    private ListView navList;
    private CharSequence mTitle;

    private ActionBarDrawerToggle drawerToggle;
    private LinearLayout drawer_child;
     String[] names;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        navigationInit();

       inicializarContenido();
        cargarFragmento(getFragmentLista());

    }

    private void navigationInit() {
        this.drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        this.navList = (ListView) findViewById(R.id.left_drawer);
        this.drawer_child = (LinearLayout) findViewById(R.id.drawer_child);
        // Load an array of options names
        names = new String[]{"Nueva Factura","Reporte del Dia","Configuracion","Cerra Sesion"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, names);
        navList.setAdapter(adapter);
        //hasta aqui el esquelo funciona bien XD

        navList.setOnItemClickListener(new DrawerItemClickListener());

        //action bar toogle
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout
                , R.string.open_drawer,
                R.string.close_drawer) {

            /**
             * Called when a drawer has settled in a completely closed state.
             */
            public void onDrawerClosed(View view) {

                // creates call to onPrepareOptionsMenu()
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mTitle);
            }

            /**
             * Called when a drawer has settled in a completely open state.
             */
            public void onDrawerOpened(View drawerView) {

                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("Factura Virtual");
                // creates call to onPrepareOptionsMenu()

            }
        };

        drawerLayout.setDrawerListener(drawerToggle);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

    }

    public void cambiarFragmento(int fragment_id)
    {
        switch (fragment_id)
        {
            case FRAGMENT_FACTURA: //cargarFragmento(getFragmentFactura());
                FragmentManager manager = getSupportFragmentManager();

                FragmentTransaction transaction = manager.beginTransaction();
//                ArrayList<Product> lista = reciver.getListaProductos();
                FragmentFactura fragmentFactura = FragmentFactura.newInstance(reciver.getListaProductos(),reciver.getMonto());
                transaction.replace(R.id.contenedor_fragmnet, fragmentFactura);//reemplazar el id con el del contenedor
                transaction.commit();

//                getFragmentFactura().listAdapter.adcionarProducto(product);

//                for(int i=0;i<reciver.getListaProductos().size();i++)
//                {
//                    Product pro = (Product) reciver.getListaProductos().get(i);
//                    getFragmentFactura().listAdapter.adcionarProducto(pro);
//                }
                setTitle("Factura");
                break;
            case FRAGMENT_TABSWIPE: cargarFragmento(getFragmentTabswipe());
                setTitle("Productos");
                break;
            case FRAGMENT_LISTA: cargarFragmento(getFragmentLista());
                setTitle("Historial");
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

//        actionBar.show();
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

//    public FragmentFactura getFragmentFactura() {
//        if(fragmentFactura==null)
//        {
//            fragmentFactura = new FragmentFactura();
//        }
//
//        return fragmentFactura;
//    }

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
        reciver = new FragmentReceiver(gridbarAdapter,this);
        registerReceiver(reciver, new IntentFilter("cambiar_fragmento"));
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
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        boolean drawerOpen = drawerLayout.isDrawerOpen(drawer_child);
        menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    private class DrawerItemClickListener implements
            ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            selectItem(position);
        }
    }

    private void selectItem(int position) {

        //Todo Colocar evento para cada opsion de la lista

        mTitle = names[position];
        navList.setItemChecked(position, true);
        getSupportActionBar().setTitle(mTitle);
        drawerLayout.closeDrawer(drawer_child);
    }
}
