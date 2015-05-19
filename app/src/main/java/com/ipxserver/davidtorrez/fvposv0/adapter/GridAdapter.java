package com.ipxserver.davidtorrez.fvposv0.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
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
    TextView tv;
    TextView tc;
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

       tv= (TextView) rootView.findViewById(R.id.txtNotes);
        tv.setText(producto.getNotes());
        tc = (TextView) rootView.findViewById(R.id.txtCost);
//        tc.setText(producto.getCost()+"x"+producto.getQty());
        ImageButton b=(ImageButton) rootView.findViewById(R.id.imgButton);
        if(Integer.parseInt(producto.getQty())>0)
        {
            tc.setText(producto.getCost() + "x" + producto.getQty());
            rootView.setBackgroundColor(Color.GREEN);
            b.setVisibility(View.VISIBLE);
        }else{
            tc.setText(producto.getCost());
            rootView.setBackgroundColor(Color.BLUE);
            b.setVisibility(View.INVISIBLE);
        }







        return rootView;
    }
}
