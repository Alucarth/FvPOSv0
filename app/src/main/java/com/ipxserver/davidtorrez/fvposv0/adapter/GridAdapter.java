package com.ipxserver.davidtorrez.fvposv0.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ipxserver.davidtorrez.fvposv0.R;
import com.ipxserver.davidtorrez.fvposv0.models.Product;

import java.text.DecimalFormat;
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
        for(int i=0;i<20;i++)
        {
            Product producto = new Product();
            producto.setId("a"+i);
            producto.setNotes(" Producto n " + i);
            producto.setCost((i + 2) + "");
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
        TextView txtNotes;
        TextView txtCost;
        TextView txtQty;
        TextView txtSubtotal;
        ImageView img;

        if (rootView ==null)
        {
            rootView = inflater.inflate(R.layout.gridview_item,viewGroup,false);
            txtNotes= (TextView) rootView.findViewById(R.id.txtNotes);
            txtCost = (TextView) rootView.findViewById(R.id.txtCost);
            txtQty = (TextView) rootView.findViewById(R.id.txtQty);
            txtSubtotal = (TextView) rootView.findViewById(R.id.txtSubtotal);
            img = (ImageView) rootView.findViewById(R.id.img_ic_remove);
            rootView.setTag(new ViewHolder(img,txtNotes,txtCost,txtQty,txtSubtotal));
        }
        else
        {
            ViewHolder viewHolder =(ViewHolder) rootView.getTag();
            txtNotes =viewHolder.textNotes;
            txtCost =viewHolder.textCost;
            txtQty = viewHolder.textQty;
            txtSubtotal = viewHolder.textSubtotal;
            img = viewHolder.imageButton;
        }
        final Product producto = lista.get(i);

        final int contador =i;


        txtNotes.setText(producto.getNotes());




        if(Integer.parseInt(producto.getQty())>0)
        {

            img.setVisibility(View.VISIBLE);
            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.i("David", "posision " + contador);
                    Product prod = (Product) lista.get(contador);
                    int cantidad = Integer.parseInt(prod.getQty());
                    cantidad--;
                    prod.setQty("" + cantidad);
                    notifyDataSetChanged();

                }
            });
            txtQty.setText("x" + producto.getQty());
            int cantidad = Integer.parseInt(producto.getQty());
            int costo= Integer.parseInt(producto.getCost());

            double subtotal = (double)(cantidad*costo);
            //Double subtotal = (double)(Double.parseDouble(producto.getCost())*Integer.parseInt(producto.getQty()));
            txtSubtotal.setText("Bs "+redondear(subtotal));
            itemSeleccionado(rootView);

            txtNotes.setTextColor(Color.WHITE);
            txtQty.setTextColor(Color.WHITE);
            txtCost.setTextColor(Color.WHITE);
            txtSubtotal.setTextColor(Color.WHITE);

            txtQty.setVisibility(View.VISIBLE);
            txtSubtotal.setVisibility(View.VISIBLE);
        }else{
            itemNormal(rootView);
            txtCost.setText("Bs "+producto.getCost());

            txtNotes.setTextColor(Color.BLACK);
            txtQty.setTextColor(Color.BLACK);
            txtCost.setTextColor(Color.parseColor("#304FFE"));
            txtSubtotal.setTextColor(Color.BLACK);

            txtQty.setVisibility(View.INVISIBLE);
            txtSubtotal.setVisibility(View.INVISIBLE);

//            float elevation = 2;
//            rootView.setElevation(elevation);
            img.setVisibility(View.INVISIBLE);
        }







        return rootView;
    }

    private void itemNormal(View rootView) {

        rootView.setBackgroundResource(R.drawable.layer_card_background);

    }

    private void itemSeleccionado(View rootView) {

        rootView.setBackgroundResource(R.color.actionbar_background);
    }

    public ArrayList<Product> getLista()
    {
        ArrayList<Product> listProduct=null;
        for(int i=0;i<lista.size();i++)
        {
            Product pro = (Product) lista.get(i);
            if(Integer.parseInt(pro.getQty())>0)
            {
                listProduct.add(pro);
            }
        }

        return listProduct;
    }
    private static class ViewHolder
    {
        public final ImageView imageButton;
        public final TextView textNotes,textCost,textSubtotal,textQty;

        private ViewHolder(ImageView imageButton, TextView textNotes, TextView textCost,TextView textQty,TextView textSubtotal) {
            this.imageButton = imageButton;
            this.textNotes = textNotes;
            this.textCost = textCost;
            this.textQty = textQty;
            this.textSubtotal = textSubtotal;
        }


    }
    double redondear(double d)
    {
        DecimalFormat twoDForm = new DecimalFormat("#.##");
        return Double.valueOf(twoDForm.format(d));
    }
}
