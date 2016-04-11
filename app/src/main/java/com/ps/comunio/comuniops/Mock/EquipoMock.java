package com.ps.comunio.comuniops.Mock;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Virginia on 18/02/2016.
 */
public class EquipoMock {
    private static final int NUM_MAX_JUGADORES = 24;
    private static final int NUM_MIN_JUGADORES = 0;
    private String nombre;
    private List<JugadorMock> jugadores;

    public EquipoMock equipo_Lleno() {
        JugadorMock jugador;
        EquipoMock equipo = new EquipoMock();
        equipo.jugadores = new ArrayList<JugadorMock>();
        for (int i = NUM_MIN_JUGADORES; i < NUM_MAX_JUGADORES; i++) {
            jugador = new JugadorMock();
            equipo.jugadores.add(jugador);
        }

        equipo.nombre = "Equipo lleno";
        return equipo;
    }

    public EquipoMock equipo_Vacio() {
        JugadorMock jugador;
        EquipoMock equipo = new EquipoMock();
        equipo.jugadores = new ArrayList<JugadorMock>();
        equipo.nombre = "Equipo vacio";
        return equipo;
    }
}
