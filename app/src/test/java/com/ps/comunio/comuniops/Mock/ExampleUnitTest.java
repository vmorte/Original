package com.ps.comunio.comuniops;

import com.ps.comunio.comuniops.Mock.CalendarioMock;

import junit.framework.TestCase;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
//@RunWith(MockitoJUnitRunner.class)
public class ExampleUnitTest{
    @Test
    public void addition_isCorrect() throws Exception {
        CalendarioMock hola = new CalendarioMock();
        System.out.println("caca");
        assertEquals(4, 2 + 2);
    }
}