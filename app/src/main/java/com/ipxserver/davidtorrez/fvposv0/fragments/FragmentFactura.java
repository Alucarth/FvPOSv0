package com.ipxserver.davidtorrez.fvposv0.fragments;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ipxserver.davidtorrez.fvposv0.R;
import com.ipxserver.davidtorrez.fvposv0.adapter.GridbarAdapter;
import com.ipxserver.davidtorrez.fvposv0.adapter.ListAdapter;
import com.ipxserver.davidtorrez.fvposv0.models.Client;
import com.ipxserver.davidtorrez.fvposv0.models.Product;

import java.util.ArrayList;

/**
 * Created by keyrus on 23-05-15.
 */
public class FragmentFactura extends Fragment //implements //DialogUser.UserDialgoListener
{
    public ArrayList<Product> listaSeleccionados;
//    public ProductListAdapter productListAdapter;
    public ListAdapter listAdapter;
    public int monto;
    TextView nit,name;

    Client cliente;
//    FacturaReceiver facturaReceiver;
   public static FragmentFactura newInstance(ArrayList<Product> listaSeleccionados, int monto)
   {
       FragmentFactura  fragmentFactura = new FragmentFactura();
       Bundle arg = new Bundle();
       //Todo: Adicionar un parametro de monto total para tenerlo todo en el fragmento

       arg.putSerializable("lista",listaSeleccionados);
       arg.putInt("monto",monto);
        fragmentFactura.setArguments(arg);
       return fragmentFactura;
   }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        listaSeleccionados = (ArrayList<Product>)getArguments().getSerializable("lista");
        monto = getArguments().getInt("monto");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_factura,container,false );

        nit = (TextView) rootView.findViewById(R.id.txt_factura_nit);
        name = (TextView)rootView.findViewById(R.id.txt_factura_nombre);
        ListView lista =(ListView) rootView.findViewById(R.id.listProductos);

//
        listAdapter = new ListAdapter(rootView.getContext(),listaSeleccionados);

        lista.setAdapter(listAdapter);

        GridView gridBar = (GridView) rootView.findViewById(R.id.barra_factura_saldo);
        GridbarAdapter gridbarAdapter = new GridbarAdapter(rootView.getContext());
        gridBar.setAdapter(gridbarAdapter);

        gridbarAdapter.incrementar(monto);


        setHasOptionsMenu(true);



        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_factura,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       switch (item.getItemId())
       {
           case R.id.action_factura_cancelar: atras();
               return true;
           case R.id.action_factura_cliente: adicionarCliente();
               return true;
           case R.id.action_factura_imprimir: imprimir();
               return true;
           default: return super.onOptionsItemSelected(item);

       }

    }

    private void imprimir() {
    }

    private void adicionarCliente() {

        //Todo: Dialog builder de momento hasta que se encuentre uno con mayor control XD

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("Buscar Cliente");

        LinearLayout lila1= new LinearLayout(getActivity());
        lila1.setOrientation(LinearLayout.VERTICAL); //1 is for vertical orientation
        final EditText input = new EditText(getActivity());
        final EditText input2 = new EditText(getActivity());
        lila1.addView(input);
        lila1.addView(input2);
        input.setHint("NIT|CI");


        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        input.requestFocus();

        builder.setView(lila1);



        builder.setPositiveButton("aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
              //Todo iniciar Progress con la consulta
                nit.setText(input.getText().toString());
                mostrarCliente(input.getText().toString());
            }
        });
        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        dialog.show();

        //
        //FragmentManager fm = getChildFragmentManager();
   //     DialogUser dialogUser = new DialogUser();
//        dialogUser.show(fm,"fragment_factura");
//        FragmentUserDialog fragmentUserDialog = new FragmentUserDialog();
//        fragmentUserDialog.show(fm,"fragment_factura");
    }

    private void mostrarCliente(String nit) {


    }

    private void atras() {

    }

//    @Override
//    public void onDialogPositiveClick(DialogUser dialogUser) {
//        cliente = new Client();
//        View view  = (View) dialogUser.getView();
//        TextView txtNombreUser = (TextView) view.findViewById(R.id.txtNameUser);
//        TextView txtNitUser =(TextView)view.findViewById(R.id.txtNitUser);
//        TextView txtEmail = (TextView)view.findViewById(R.id.txtEmailUser);
//        cliente.setNombre(txtNombreUser.getText().toString());
//        cliente.setNit(txtNitUser.toString());
//        cliente.setEmail(txtEmail.toString());
//        nit.setText(cliente.getNit());
//        name.setText(cliente.getNombre());
//
//    }
//
//    @Override
//    public void onDialgoNegativeClick(DialogUser dialogUser) {
//        dialogUser.getDialog().cancel();
//    }
    //
//    @Override
//    public void onResume() {
//        facturaReceiver = new FacturaReceiver(this);
//        getActivity().registerReceiver(facturaReceiver, new IntentFilter("cast_factura"));
//        Log.i("David","resume Factura aqui esta escuchando");
//        super.onResume();
//    }
//
//    @Override
//    public void onPause() {
//        getActivity().unregisterReceiver(facturaReceiver);
//        super.onPause();
//    }
}