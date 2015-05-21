package com.ipxserver.davidtorrez.fvposv0.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ipxserver.davidtorrez.fvposv0.R;
import com.ipxserver.davidtorrez.fvposv0.models.ItemBar;

import java.util.ArrayList;

/**
 * Created by David Torrez on 20/05/2015.
 */
public class GridbarAdapter extends BaseAdapter
{
    ArrayList <ItemBar> items;
    Context context;
    public GridbarAdapter(Context context)
    {
        this.context = context;
        items = new ArrayList<ItemBar>();

        ItemBar ib = new ItemBar();
        ib.setNumero(0);
        ib.setDescripcion("Subtotal");
        items.add(ib);

        ib = new ItemBar();
        ib.setNumero(0);
        ib.setDescripcion("Total");
        items.add(ib);

    }
    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rootView = view;
        if(rootView==null)
        {
            rootView =  inflater.inflate(R.layout.gridbar_item,viewGroup,false);
        }
        TextView txtNumero =(TextView)rootView.findViewById(R.id.txtNumero);
        TextView txtDescripcion= (TextView) rootView.findViewById(R.id.txtDescripcion);

        ItemBar item = (ItemBar) items.get(i);
        txtNumero.setText(item.getNumero()+" Bs");
        txtDescripcion.setText(item.getDescripcion());

        return rootView;
    }
}
