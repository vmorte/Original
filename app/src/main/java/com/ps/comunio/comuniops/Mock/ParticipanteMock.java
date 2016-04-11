package com.ps.comunio.comuniops.Mock;

import java.util.ArrayList;

/**
 * Created by Virginia on 18/02/2016.
 */
public class ParticipanteMock {
    private String nombre;
    private int dinero;
    private EquipoMock equipo;
    private int puntos;

    public ParticipanteMock nuevo_Participante_Pobre() {
        ParticipanteMock virginia = new ParticipanteMock();
        virginia.nombre = "Virginia";
        virginia.dinero = 1;
        EquipoMock equipovir = new EquipoMock();
        virginia.equipo = equipovir;
        virginia.puntos = 10000;
        return virginia;
    }

    public ParticipanteMock nuevo_Participante_Rico() {
        ParticipanteMock virginia = new ParticipanteMock();
        virginia.nombre = "VirginiaConPasta";
        virginia.dinero = 100000;
        EquipoMock equipovir = new EquipoMock();
        virginia.equipo = equipovir;
        virginia.puntos = 1;
        return virginia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getDinero() {
        return dinero;
    }

    public void setDinero(int dinero) {
        this.dinero = dinero;
    }

    public EquipoMock getEquipo() {
        return equipo;
    }

    public void setEquipo(EquipoMock equipo) {
        this.equipo = equipo;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }
}
