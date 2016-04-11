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

public class Venta_Activity extends AppCompatActivity {
    private TextView nombre;
    private TextView precio;
    private TextView posicion;
    private TextView tvFondos;
    private String[] j;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venta_);

        j = getIntent().getStringArrayExtra("jugador");

        tvFondos = (TextView) findViewById(R.id.tvfondos);

        WebView imagen = (WebView) findViewById(R.id.ivventa);
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

        nombre = (TextView) findViewById(R.id.tvnombrejugadorventa);
        precio = (TextView) findViewById(R.id.tvpreciojugadorventa);
        posicion = (TextView) findViewById(R.id.tvposicionjugadorventa);

        obtDatos(j[1]);
        creditoUsuario(j[0]);
    }

    public void confirmacionCompra(View v){
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setMessage("¿Desea comprar este jugador?");
        b.setTitle("Confirmación de compra");
        b.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                TextView  tvFondos2 = (TextView) findViewById(R.id.tvfondos);
                TextView tvPrecio = (TextView) findViewById(R.id.tvpreciojugadorventa);
                String fondos = tvFondos2.getText().toString();
                String coste = tvPrecio.getText().toString();
                int fondosUsr = Integer.parseInt(fondos);
                int precio = Integer.parseInt(coste);

                if (fondosUsr < precio) {
                    Toast.makeText(Venta_Activity.this, "No tienes suficientes fondos", Toast.LENGTH_LONG).show();
                } else { //Compra
                    fondosUsr = fondosUsr - precio;
                    tvFondos2.setText(Integer.toString(fondosUsr));
                    actualizarFondos(j[0], Integer.toString(fondosUsr));
                    compraJugador(j[1], j[0]);
                    Toast.makeText(Venta_Activity.this, "Jugador comprado", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(Venta_Activity.this, Mercado_Activity.class);
                    i.putExtra("user", j[0]);
                    startActivity(i);
                    finish();
                }

            }
        });

        b.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(Venta_Activity.this, "Operación cancelada", Toast.LENGTH_LONG).show();

            }
        });
        AlertDialog d = b.create();
        d.show();
    }

    //Carga datos del jugador
    public void obtDatos(String ju){
        AsyncHttpClient client = new AsyncHttpClient();
        String url = "http://comuniops.hol.es/index.php?funcion=getPlayer&player=" +ju;
        RequestParams params = new RequestParams();
        client.get(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    ponerDatos(nombreJSON(new String(responseBody)),
                            posicionJSON(new String(responseBody)), precioJSON(new String(responseBody)));
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    //Permite la compra de jugadores
    public void compraJugador(String ju, String u){
        AsyncHttpClient client = new AsyncHttpClient();
        String url = "http://comuniops.hol.es/index.php?funcion=updatePlayer&player=" +ju +"&username=" +u;
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

    public void actualizarFondos(String u, String f){
        AsyncHttpClient client = new AsyncHttpClient();
        String url = "http://comuniops.hol.es/index.php?funcion=updateUser&username=" +u +"&credit=" +f;
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
    public void creditoUsuario(String u){
        AsyncHttpClient client = new AsyncHttpClient();
        String url = "http://comuniops.hol.es/index.php?funcion=getUser&username=" +u;
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
    public String creditoJSON(String response){
        String texto = "";
        try{
            JSONArray jsonArray = new JSONArray(response);
            texto = jsonArray.getJSONObject(0).getString("credit");

        }catch(Exception e){

        }
        return texto;
    }

    public void ponerFondos(String fondos){
        tvFondos.setText(fondos);
    }

    //Actualiza los textos con los datos del jugador
    public void ponerDatos(String name, String position, String coste){
        nombre.setText(name);
        precio.setText(coste);
        posicion.setText(position);
    }

    //Métodos para devolver los datos del jugador
    public String nombreJSON(String response){
        String texto = "";
        try{
            JSONArray jsonArray = new JSONArray(response);
            texto = jsonArray.getJSONObject(0).getString("name");

        }catch(Exception e){

        }
        return texto;
    }


    public String precioJSON(String response){
        String precio = "";
        try{
            JSONArray jsonArray = new JSONArray(response);
            precio = jsonArray.getJSONObject(0).getString("price");

        }catch(Exception e){

        }
        return precio;
    }

    public String posicionJSON(String response){
        String texto = "";
        try{

            JSONArray jsonArray = new JSONArray(response);
            texto = jsonArray.getJSONObject(0).getString("position");

        }catch(Exception e){

        }
        return texto;
    }


    @Override
    public void onBackPressed(){
        Intent i = new Intent(Venta_Activity.this, Mercado_Activity.class);
        i.putExtra("user", j[0]);
        startActivity(i);
        finish();
    }
}
