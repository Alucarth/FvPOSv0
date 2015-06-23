package com.ipxserver.davidtorrez.fvposv0;


import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ipxserver.davidtorrez.fvposv0.Listeners.FragmentReceiver;
import com.ipxserver.davidtorrez.fvposv0.adapter.GridbarAdapter;
import com.ipxserver.davidtorrez.fvposv0.adapter.NavAdapter;
import com.ipxserver.davidtorrez.fvposv0.fragments.FragmentEmpresa;
import com.ipxserver.davidtorrez.fvposv0.fragments.FragmentFactura;
import com.ipxserver.davidtorrez.fvposv0.fragments.FragmentLista;
import com.ipxserver.davidtorrez.fvposv0.fragments.FragmentTabswipe;
import com.ipxserver.davidtorrez.fvposv0.models.Account;
import com.ipxserver.davidtorrez.fvposv0.models.NavItem;
import com.ipxserver.davidtorrez.fvposv0.models.User;

import java.util.ArrayList;


public class PrincipalActivity extends ActionBarActivity {


    GridbarAdapter gridbarAdapter;

   FragmentReceiver reciver;
    FragmentLista fragmentLista=null;
//    FragmentFactura fragmentFactura=null;
    FragmentTabswipe fragmentTabswipe=null;
    FragmentEmpresa fragmentEmpresa=null;

    User usuario;




    //Variables para el navigation drawer
    private DrawerLayout drawerLayout;
    private ListView navList;
    private CharSequence mTitle;

    private ActionBarDrawerToggle drawerToggle;
    private LinearLayout drawer_child;

    private String respuesta;
    private Account cuenta;
    String[] names;

    private ArrayList<NavItem> navmenu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        Intent intent = getIntent();
        respuesta = intent.getStringExtra("cuenta");
        usuario =(User) intent.getSerializableExtra("usuario");
        cuenta = new Account(respuesta);

//        cuenta = new Account("{\"productos\":{\"categoria1\":[{\"id\":9,\"product_key\":\"AM11\",\"notes\":\" producto 1\",\"cost\":\"10.00\"},{\"id\":13,\"product_key\":\"AM15\",\"notes\":\" producto con descripsion\",\"cost\":\"99.00\"},{\"id\":11,\"product_key\":\"AM13\",\"notes\":\" producto 3\",\"cost\":\"10.00\"}],\"categoria2\":[{\"id\":12,\"product_key\":\"AM14\",\"notes\":\" producto 4\",\"cost\":\"10.00\"},{\"id\":10,\"product_key\":\"AM12\",\"notes\":\" producto 2\",\"cost\":\"10.00\"},{\"id\":14,\"product_key\":\"AM16\",\"notes\":\" producto 6\",\"cost\":\"10.00\"}],\"categoria3\":[{\"id\":16,\"product_key\":\"AM18\",\"notes\":\" producto 8\",\"cost\":\"10.00\"},{\"id\":17,\"product_key\":\"AM19\",\"notes\":\" producto 9\",\"cost\":\"10.00\"},{\"id\":18,\"product_key\":\"AM20\",\"notes\":\" producto 0\",\"cost\":\"10.00\"}]},\"categorias\":[{\"categoria\":\"categoria1\"},{\"categoria\":\"categoria2\"},{\"categoria\":\"categoria3\"}],\"first_name\":\"Aurora\",\"last_name\":\"Bustillo Bravo\",\"branch\":\"Casa Matriz\"}");
        Log.i("David","respuesta "+respuesta);
        Log.i("David","usuario "+usuario.getUser());
        navigationInit();

       inicializarContenido();
        cargarFragmento(getFragmentLista());

    }

    private void navigationInit() {
        this.drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        this.navList = (ListView) findViewById(R.id.left_drawer);
        this.drawer_child = (LinearLayout) findViewById(R.id.drawer_child);
        // Load an array of options names
        navmenu = new ArrayList<NavItem>();

        NavItem navItem = new NavItem("Nueva Factura",R.drawable.ic_action_new_black);
        navmenu.add(navItem);

        navItem = new NavItem("Reporte Diario",R.drawable.ic_action_report);
        navmenu.add(navItem);

        navItem = new NavItem("Acerca de Factura Virtual",R.drawable.ic_action_empresa_black);
        navmenu.add(navItem);

        navItem = new NavItem("Cerrar Sesion",R.drawable.ic_action_exit);
        navmenu.add(navItem);

//        names = new String[]{"Nueva Factura","Reporte del Dia","Acerca de Factura Virtual","Cerrar Sesion"};
//
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
//                android.R.layout.simple_list_item_1, names);
        NavAdapter navAdapter = new NavAdapter(navList.getContext(),navmenu);
        navList.setAdapter(navAdapter);
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
                getSupportActionBar().setTitle(cuenta.getBranch());
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
            case FragmentReceiver.FRAGMENT_FACTURA: //cargarFragmento(getFragmentFactura());
                FragmentManager manager = getSupportFragmentManager();

                FragmentTransaction transaction = manager.beginTransaction();
//                ArrayList<Product> lista = reciver.getListaProductos();
                FragmentFactura fragmentFactura = FragmentFactura.newInstance(reciver.getListaProductos(),reciver.getMonto(),usuario);
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
            case FragmentReceiver.FRAGMENT_TABSWIPE: cargarFragmento(getFragmentTabswipe());
                setTitle("Productos");
                break;
            case FragmentReceiver.FRAGMENT_LISTA: cargarFragmento(getFragmentLista());
                setTitle("Reporte de Ventas");
                break;
            case FragmentReceiver.FRAGMENT_EMPRESA: cargarFragmento(getFragmentEmpresa());
                setTitle("Factura Virtual");
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
        TextView txtFirstName = (TextView)findViewById(R.id.txt_fist_name);
        TextView txtLastName = (TextView)findViewById(R.id.txt_last_name);
        txtFirstName.setText(cuenta.getFirst_name());
        txtLastName.setText(cuenta.getLast_name());
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
            fragmentTabswipe = FragmentTabswipe.newInstance(cuenta.getCategorias());
        }
        return fragmentTabswipe;
    }
    public  FragmentEmpresa getFragmentEmpresa()
    {
        if(fragmentEmpresa==null)
        {
            fragmentEmpresa = new FragmentEmpresa();
        }
        return fragmentEmpresa;
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

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
            selectItem(position);
        }
    }

    private void selectItem(int position) {

        //Todo Colocar evento para cada opsion de la lista
        switch (position)
        {
            case 0: cargarFragmento(getFragmentTabswipe());
                break;
            case 1: cargarFragmento(getFragmentLista());
                break;
            case 2: cargarFragmento(getFragmentEmpresa());
                break;
        }
        NavItem navItem = (NavItem) navmenu.get(position);
        mTitle = navItem.getTitulo();
        navList.setItemChecked(position, true);
        getSupportActionBar().setTitle(mTitle);
        drawerLayout.closeDrawer(drawer_child);
    }
}
