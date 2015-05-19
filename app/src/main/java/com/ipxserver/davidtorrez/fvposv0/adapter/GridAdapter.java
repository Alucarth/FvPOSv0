package com.ipxserver.davidtorrez.fvposv0.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


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
    ImageView img;

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
        final Product producto = lista.get(i);

        final int contador =i;

        tv= (TextView) rootView.findViewById(R.id.txtNotes);
        tv.setText(producto.getNotes());
        tc = (TextView) rootView.findViewById(R.id.txtCost);
//        tc.setText(producto.getCost()+"x"+producto.getQty());
        img = (ImageView) rootView.findViewById(R.id.img_ic_remove);
        if(Integer.parseInt(producto.getQty())>0)
        {
            img.setVisibility(View.VISIBLE);
            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.i("David","posision "+ contador);
                    Product prod = (Product)lista.get(contador);
                    int cantidad = Integer.parseInt(prod.getQty());
                    cantidad--;
                    prod.setQty(""+cantidad);
                    notifyDataSetChanged();

                }
            });
            tc.setText(producto.getCost() + "x" + producto.getQty());
            rootView.setBackgroundColor(Color.BLUE);

        }else{
            tc.setText(producto.getCost());
            rootView.setBackgroundColor(Color.LTGRAY);
            img.setVisibility(View.INVISIBLE);
        }







        return rootView;
    }
}
