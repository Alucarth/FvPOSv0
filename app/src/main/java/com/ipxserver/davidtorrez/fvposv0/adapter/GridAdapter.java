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
 * Created by keyrus on 18-05-15.
 */
public class GridAdapter extends BaseAdapter
{
    ArrayList<Product> lista;
    Context context;
    public GridAdapter(Context context){
        this.context = context;
        lista = new ArrayList<Product>();
        for(int i=0;i<10;i++)
        {
            Product producto = new Product();
            producto.setNotes(" Producto n "+i);
            producto.setCost("$ " + (i + 2));
            lista.add(producto);

        }
    }

    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public Object getItem(int i) {
        return lista.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rootView = view;
        if (rootView ==null)
        {
            rootView = inflater.inflate(R.layout.gridview_item,viewGroup,false);
        }
        Product producto = lista.get(i);

        TextView tv= (TextView) rootView.findViewById(R.id.txtNotes);
        tv.setText(producto.getNotes());
        TextView tc = (TextView) rootView.findViewById(R.id.txtCost);
        tc.setText(producto.getCost()+"x"+producto.getQty());



        return rootView;
    }
}
