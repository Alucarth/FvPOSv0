package com.ipxserver.davidtorrez.fvposv0.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by keyrus on 17-05-15.
 */
public class Product implements Serializable
{
    public final static String ID ="id";
    public final static String NOTES="notes";
    public final static String COST="cost";
    public final static String PRODUCT_KEY="product_key";
    private String product_key;
    private String notes;
    private String cost;
    private String id;
    private String qty="0";


    public static Product fromJson(String jsonText)
    {
        Product product = new Product();

        try {
            JSONObject json = new JSONObject(jsonText);

            if(json.has(Product.ID))
            {
                product.setId(json.getString(Product.ID));
            }
            if(json.has(Product.NOTES))
            {
                product.setNotes(json.getString(Product.NOTES));
            }
            if(json.has(Product.COST))
            {
                product.setCost(json.getString(Product.COST));
            }
            if(json.has(Product.PRODUCT_KEY))
            {
                product.setProduct_key(json.getString(Product.PRODUCT_KEY));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return product;
    }

    public static ArrayList<Product> fromJsonArray(String jsonArray)
    {
        ArrayList<Product> productos= new ArrayList<Product>();
        Product producto;
        try {
            JSONArray array = new JSONArray(jsonArray);

            for(int i =0;i<array.length();i++)
            {
                JSONObject json = array.getJSONObject(i);
                producto = Product.fromJson(json.toString());
                productos.add(producto);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return productos;
    }

    private static JSONObject toJSONObject(Product producto) {
        JSONObject json = new JSONObject();
        try{

            json.put("id", producto.getId());
//            json.put("amount", producto.getCost());
            json.put("qty",producto.getQty());
//
//            json.put("boni", producto.getBoni());
//            json.put("desc", producto.getDesc());

        }catch (JSONException ex) {
            ex.printStackTrace();
        }
        return json;
    }

    public static JSONArray toJSONs(ArrayList<Product> productos) {
        JSONArray productsArray = new JSONArray();
        for (int i = 0; i < productos.size(); i++) {
            Product producto = (Product)productos.get(i);

            JSONObject jsonObject = toJSONObject(producto);
            productsArray.put(jsonObject);
        }
//        return productsArray.toString();
        return productsArray;
    }

    public String getProduct_key() {
        return product_key;
    }

    public void setProduct_key(String product_key) {
        this.product_key = product_key;
    }

    private boolean habilitado=false;

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

    public boolean isHabilitado() {
        return habilitado;
    }

    public void setHabilitado(boolean habilitado) {
        this.habilitado = habilitado;
    }
}
