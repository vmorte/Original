package com.ps.comunio.comuniops;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;

import cz.msebera.android.httpclient.Header;

public class Ficha_Activity extends AppCompatActivity {
    private TextView nombre;
    private TextView precio;
    private TextView posicion;
    private String[] j;
    private TextView fondos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ficha_);

        j = getIntent().getStringArrayExtra("jugador");

        WebView imagen = (WebView) findViewById(R.id.ivficha);
        WebSettings webSettings = imagen.getSettings();
        webSettings.setJavaScriptEnabled(true);

        if((j[1].equals("Juan Antonio")) || (j[1].equals("Alvaro")) || (j[1].equals("Victor Munoz")) ||
                (j[1].equals("Felix")) || (j[1].equals("Chuck Norris")) || (j[1].equals("Paco Pium")) || (j[1].equals("Schweisteiger"))){
            imagen.loadUrl("http://comuniops.hol.es/jugadores/none.png");
        }
        else if ((j[1].equals("Aguero"))){
            imagen.loadUrl("http://comuniops.hol.es/jugadores/Kun.jpg");
        }
        else if (j[1].equals("Ibrahimovic")){
            imagen.loadUrl("http://comuniops.hol.es/jugadores/Ibra.jpg");
        }
        else if(j[1].equals("Lewandowski")) {
            imagen.loadUrl("http://comuniops.hol.es/jugadores/Lewi.jpg");
        }
        else if(j[1].equals("Muller")){
            imagen.loadUrl("http://comuniops.hol.es/jugadores/Mullered.jpg");
        }
        else{
            imagen.loadUrl("http://comuniops.hol.es/jugadores/" +j[1] +".jpg");
        }

        fondos = (TextView) findViewById(R.id.tvfondosficha);
        nombre = (TextView) findViewById(R.id.tvnombrejugadorficha);
        precio = (TextView) findViewById(R.id.tvpreciojugadorficha);
        posicion = (TextView) findViewById(R.id.tvposicionjugadorficha);

        obtDatos(j[1]);
        creditoUsuario(j[0]);
    }

    public void confirmacionVenta(View v) {
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setMessage("¿Desea vender este jugador? Recuerde que se le descontará un 10% de la venta del jugador");
        b.setTitle("Confirmación de venta");
        b.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                TextView tvPrecio = (TextView) findViewById(R.id.tvpreciojugadorficha);
                String fondosAux = fondos.getText().toString();
                String coste = tvPrecio.getText().toString();
                double precio = Double.parseDouble(coste);
                double fond = Double.parseDouble(fondosAux);
                double impuesto = precio * 0.1;
                double total = fond + (precio - impuesto);

                String f = Double.toString(total);
                actualizarFondos(j[0], f);
                ventaJugador(j[1]);
                Toast.makeText(Ficha_Activity.this, "Jugador vendido", Toast.LENGTH_LONG).show();
                Intent i = new Intent(Ficha_Activity.this, Equipo_Activity.class);
                i.putExtra("user", j[0]);
                startActivity(i);
                finish();
            }

        });

        b.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(Ficha_Activity.this, "Operación cancelada", Toast.LENGTH_LONG).show();

            }
        });
        AlertDialog d = b.create();
        d.show();
    }

    //Carga datos del jugador
    public void obtDatos(String ju) {
        AsyncHttpClient client = new AsyncHttpClient();
        String url = "http://comuniops.hol.es/index.php?funcion=getPlayer&player=" + ju;
        RequestParams params = new RequestParams();
        client.get(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    ponerDatos(nombreJSON(new String(responseBody)), posicionJSON(new String(responseBody)), precioJSON(new String(responseBody)));
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    //Permite la compra de jugadores
    public void ventaJugador(String ju) {
        AsyncHttpClient client = new AsyncHttpClient();
        String url = "http://comuniops.hol.es/index.php?funcion=updatePlayer&player=" + ju + "&username=mercado";
        RequestParams params = new RequestParams();
        client.get(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    public void actualizarFondos(String u, String f) {
        AsyncHttpClient client = new AsyncHttpClient();
        String url = "http://comuniops.hol.es/index.php?funcion=updateUser&username=" + u + "&credit=" + f;
        RequestParams params = new RequestParams();
        client.get(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    //Permite comprobar el crédito del usuario
    public void creditoUsuario(String u) {
        AsyncHttpClient client = new AsyncHttpClient();
        String url = "http://comuniops.hol.es/index.php?funcion=getUser&username=" + u;
        RequestParams params = new RequestParams();
        client.get(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                ponerFondos(creditoJSON(new String(responseBody)));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    //Devuelve el crédito del usuario
    public String creditoJSON(String response) {
        String texto = "";
        try {
            JSONArray jsonArray = new JSONArray(response);
            texto = jsonArray.getJSONObject(0).getString("credit");

        } catch (Exception e) {

        }
        return texto;
    }

    public void ponerFondos(String f){
        fondos.setText(f);
    }

    //Actualiza los textos con los datos del jugador
    public void ponerDatos(String name, String position, String coste) {
        nombre.setText(name);
        precio.setText(coste);
        posicion.setText(position);
    }

    //Métodos para devolver los datos del jugador
    public String nombreJSON(String response) {
        String texto = "";
        try {
            JSONArray jsonArray = new JSONArray(response);
            texto = jsonArray.getJSONObject(0).getString("name");

        } catch (Exception e) {

        }
        return texto;
    }


    public String precioJSON(String response) {
        String precio = "";
        try {
            JSONArray jsonArray = new JSONArray(response);
            precio = jsonArray.getJSONObject(0).getString("price");

        } catch (Exception e) {

        }
        return precio;
    }

    public String posicionJSON(String response) {
        String texto = "";
        try {

            JSONArray jsonArray = new JSONArray(response);
            texto = jsonArray.getJSONObject(0).getString("position");

        } catch (Exception e) {

        }
        return texto;
    }

    @Override
    public void onBackPressed(){
        Intent i = new Intent(Ficha_Activity.this, Equipo_Activity.class);
        i.putExtra("user", j[0]);
        startActivity(i);
        finish();
    }
}