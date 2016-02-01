package com.ipxserver.davidtorrez.fvpos.models;

import java.io.Serializable;

/**
 * Created by David-Pc on 24/06/2015.
 */
public class FacturaCardItem implements Serializable {
    private String numero;
    private String monto;
    private String fecha;
    private String id;
    private String nit;
    private String razon;
    private String json_invoice;

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getMonto() {
        return monto;
    }

    public void setMonto(String monto) {
        this.monto = monto;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNit() {
        return nit;
    }

    public String getRazon() {
        return razon;
    }

    public void setRazon(String razon) {
        this.razon = razon;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getJson_invoice() {
        return json_invoice;
    }

    public void setJson_invoice(String json_invoice) {
        this.json_invoice = json_invoice;
    }
}
