package com.ipxserver.davidtorrez.fvposv0.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ipxserver.davidtorrez.fvposv0.R;
import com.ipxserver.davidtorrez.fvposv0.models.Product;

import java.util.ArrayList;

/**
 * Created by David-Pc on 01/06/2015.
 */
public class ListAdapter extends BaseAdapter
{
    public ArrayList<Product> lista;
    Context context;
    public ListAdapter (Context context,ArrayList<Product> lista)
    {
        this.context = context;
        this.lista = lista;
//        Product product = new Product();
//        product.setCost("3 ");
//        product.setQty("2");
//        product.setId("p1");
//        product.setNotes("its work");
//        adcionarProducto(product);

    }
    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public Object getItem(int position) {
        return lista.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View item = inflater.inflate(R.layout.list_producto_item, null);
        TextView txtQty = (TextView) item.findViewById(R.id.txtCantidad);
        TextView txtNotes = (TextView) item.findViewById(R.id.txtDescripcion);
        TextView txtCosto = (TextView) item.findViewById(R.id.txtCosto);

        Product producto = (Product) lista.get(position);
        txtQty.setText(producto.getQty());
        txtNotes.setText(producto.getNotes());
        txtCosto.setText(producto.getCost());

        return item;

    }
    public void adcionarProducto(Product product)
    {
        lista.add(product);

    }
}
