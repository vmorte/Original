package com.ps.comunio.comuniops.Mock;

/**
 * Created by Virginia on 18/02/2016.
 */
public class JugadorMock {
    private String nombre;
    private int precio;
    //etc

    public JugadorMock crearFernandoTorres() {
        JugadorMock fernando = new JugadorMock();
        fernando.setNombre("Fernando Torres");
        fernando.setPrecio(100);
        return fernando;
    }

    public JugadorMock crearMessi() {
        JugadorMock fernando = new JugadorMock();
        fernando.setNombre("Fernando Alonso");
        fernando.setPrecio(100000);
        return fernando;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }
}
