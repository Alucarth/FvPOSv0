package com.ipxserver.davidtorrez.fvposv0.models;

import android.widget.BaseAdapter;

/**
 * Created by keyrus on 17-05-15.
 */
public class Product
{
    private String notes;
    private String cost;
    private String id;
    private String qty="0";

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }
}
