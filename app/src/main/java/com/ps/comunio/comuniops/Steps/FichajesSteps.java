package com.ps.comunio.comuniops.Steps;

import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.api.java.Before;
import cucumber.api.java.After;

import com.ps.comunio.comuniops.Mock.*;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by Virginia on 16/02/2016.
 */

public class FichajesSteps {

    private ParticipanteMock usuario;
    private CalendarioMock calendario;
    private JugadorMock jugador;

    @Before
    public void before_test() {
        usuario = new ParticipanteMock();
        calendario = new CalendarioMock();
        jugador = new JugadorMock();
    }

    @Test
    @Given("^that the user has enough money to pay for Fernando Torres$")
    public void that_the_user_has_enough_money_to_pay_for_Fernando_Torres() throws Throwable {
        JugadorMock fernando = jugador.crearFernandoTorres();
        ParticipanteMock usuarioRico = usuario.nuevo_Participante_Rico();
        //ParticipanteMock usuarioPobre = usuario.nuevo_Participante_Pobre();
        boolean valor = usuarioRico.getDinero() >= fernando.getPrecio();
        assertTrue(valor);
    }
}
