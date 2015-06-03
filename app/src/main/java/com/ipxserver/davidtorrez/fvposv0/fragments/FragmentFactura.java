package com.ipxserver.davidtorrez.fvposv0.fragments;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.util.Log;
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

    //Dialogos
    public ProgressDialog pDialog;
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
      //  final EditText input2 = new EditText(getActivity());
        lila1.addView(input);
        //lila1.addView(input2);
        input.setHint("NIT|CI");


        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        input.requestFocus();

        builder.setView(lila1);



        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
              //Todo iniciar Progress con la consulta
                cliente = new Client();
                cliente.setNit(input.getText().toString());
               // nit.setText(cliente.getNit());
                buscarCliente(cliente.getNit());
                //mostrarCliente(input.getText().toString());
            }


        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
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

    private void buscarCliente(String nit) {
        pDialog = new ProgressDialog(getActivity());
        pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pDialog.setTitle("Buscando Cliente");
        pDialog.setMessage("Por favor Espere ...");
        pDialog.setCancelable(true);
        pDialog.setMax(100);
//        numero= txtEntrada.getText().toString();
//        monto= txtSalida.getText().toString();

        AsyncCallWS task = new AsyncCallWS();


        //Call execute
        task.execute();
    }

    private void mostrarCliente() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("Datos Cliente");

        LinearLayout ld= new LinearLayout(getActivity());
        ld.setOrientation(LinearLayout.VERTICAL); //1 is for vertical orientation
        final EditText txtNitDialog = new EditText(getActivity());
        final EditText txtNombreDialog = new EditText(getActivity());
        final EditText txtEmailDialog = new EditText(getActivity());
        //  final EditText input2 = new EditText(getActivity());
        ld.addView(txtNitDialog);
        ld.addView(txtNombreDialog);
        ld.addView(txtEmailDialog);
        //lila1.addView(input2);

        txtNitDialog.setText(cliente.getNit());
        txtNombreDialog.setHint("Nombre/Razon Social");
        txtEmailDialog.setHint("Correo Electronico");

        txtNitDialog.setInputType(InputType.TYPE_CLASS_NUMBER);
        txtNombreDialog.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
        txtEmailDialog.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_SUBJECT);
        txtNombreDialog.requestFocus();

        builder.setView(ld);



        builder.setPositiveButton("aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Todo iniciar Progress con la consulta de registro en caso de ser necesario
                //.setText(input.getText().toString());
                //buscarCliente(input.getText().toString());
                //mostrarCliente(input.getText().toString());
                cliente.setNit(txtNitDialog.getText().toString());
                cliente.setNombre(txtNombreDialog.getText().toString());
                cliente.setEmail(txtEmailDialog.getText().toString());

                name.setText("Cliente:"+cliente.getNombre());
                nit.setText("Nit:"+cliente.getNit());
            }


        });
        builder.setNegativeButton("cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        dialog.show();
    }

    private void atras() {

    }

    private class AsyncCallWS extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            Log.i("consultaWS", "doInBackground");
//	            getFahrenheit(celcius);
            //getCobro();
//	            calcularEdad();

            //Todo: simulando comunicacion
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            Log.i("consultaWS", "onPostExecute");
            pDialog.dismiss();
            mostrarCliente();
//              Toast.makeText(MulticobroPrincipal.this, "Tarea finalizada!",
//              Toast.LENGTH_SHORT).show();
//	            mostrar.setText(david);
            //Todo Alert con info del cliente registrarlo en el caso de no existir
            //alerta("MultiCobro",cobro.getMenssage());


        }

        @Override
        protected void onPreExecute() {
            Log.i("consultaWS", "onPreExecute");
//	            mostrar.setText("Calculating...");

            pDialog.setProgress(0);
            pDialog.show();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            Log.i("consultaWS", "onProgressUpdate");
        }

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
