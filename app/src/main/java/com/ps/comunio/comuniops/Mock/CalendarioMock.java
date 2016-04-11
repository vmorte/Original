package com.ps.comunio.comuniops.Mock;

import java.util.GregorianCalendar;

/**
 * Created by Virginia on 19/02/2016.
 */
public class CalendarioMock {
    private enum tTemporada { TEMPORADA_INVIERNO, TEMPORADA_VERANO};
    private tTemporada temporada;

    public void calendarioAInvierno() {
        temporada = tTemporada.TEMPORADA_INVIERNO;
    }

    public void calendarioAVerano() {
        temporada = tTemporada.TEMPORADA_VERANO;
    }

    public tTemporada getTemporada() {
        return temporada;
    }

    public void setTemporada(tTemporada temporada) {
        this.temporada = temporada;
    }
}
