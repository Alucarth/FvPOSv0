package com.ipxserver.davidtorrez.fvposv0.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ipxserver.davidtorrez.fvposv0.R;
import com.ipxserver.davidtorrez.fvposv0.models.NavItem;

import java.util.ArrayList;

/**
 * Created by David-Pc on 09/06/2015.
 */
public class NavAdapter extends BaseAdapter
{
    Context context;
    final ArrayList<NavItem> menu;
    public NavAdapter(Context context,final ArrayList<NavItem> menu)
    {
        this.context = context;
        this.menu = menu;
    }
    @Override
    public int getCount() {
        return menu.size();
    }

    @Override
    public Object getItem(int position) {
        return menu.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View item = inflater.inflate(R.layout.nav_item, null);
        TextView txtTitulo = (TextView)item.findViewById(R.id.txtTitulo);
        ImageView icono = (ImageView)item.findViewById(R.id.icono);
        NavItem navItem = (NavItem) menu.get(position);
        Log.i("David Adapter:",navItem.getTitulo());
        txtTitulo.setText(navItem.getTitulo());
        icono.setImageResource(navItem.getImage());
        return item;
    }
}
