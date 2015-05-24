package com.ipxserver.davidtorrez.fvposv0.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ipxserver.davidtorrez.fvposv0.R;
import com.ipxserver.davidtorrez.fvposv0.models.Product;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by keyrus on 23-05-15.
 */
public class ProductListAdapter extends ArrayAdapter
{

    private  Context context;
    private ArrayList<Product> listaSeleccionados;
    public ProductListAdapter(Context context, ArrayList<Product> listaSeleccionados) {
        super(context, R.layout.list_producto_item,listaSeleccionados);
        this.context = context;
        this.listaSeleccionados =listaSeleccionados;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View item= inflater.inflate(R.layout.list_producto_item, null);
        TextView txtQty = (TextView) item.findViewById(R.id.txtCantidad);
        TextView txtNotes =(TextView) item.findViewById(R.id.txtDescripcion);
        TextView txtCosto = (TextView) item.findViewById(R.id.txtCosto);

        Product producto = (Product) listaSeleccionados.get(position);
        txtQty.setText(producto.getQty());
        txtNotes.setText(producto.getNotes());
        txtCosto.setText(producto.getCost());

        return item;
    }
}
