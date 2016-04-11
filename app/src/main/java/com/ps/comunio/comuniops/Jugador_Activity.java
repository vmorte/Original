package com.ps.comunio.comuniops;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;


import cz.msebera.android.httpclient.Header;

public class Jugador_Activity extends AppCompatActivity {
    private TextView nombre;
    private TextView coste;
    private TextView posicion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jugador_);

        String j = getIntent().getStringExtra("jugador");

        WebView imagen = (WebView) findViewById(R.id.ivjugador);
        WebSettings webSettings = imagen.getSettings();
        webSettings.setJavaScriptEnabled(true);

        if((j.equals("Juan Antonio")) || (j.equals("Alvaro")) || (j.equals("Victor Munoz")) ||
                (j.equals("Felix")) || (j.equals("Chuck Norris")) || (j.equals("Paco Pium")) || (j.equals("Schweisteiger"))){
            imagen.loadUrl("http://comuniops.hol.es/jugadores/none.png");
        }
        else if ((j.equals("Aguero"))){
            imagen.loadUrl("http://comuniops.hol.es/jugadores/Kun.jpg");
        }
        else if (j.equals("Ibrahimovic")){
            imagen.loadUrl("http://comuniops.hol.es/jugadores/Ibra.jpg");
        }
        else if(j.equals("Lewandowski")) {
            imagen.loadUrl("http://comuniops.hol.es/jugadores/Lewi.jpg");
        }
        else if(j.equals("Muller")){
            imagen.loadUrl("http://comuniops.hol.es/jugadores/Mullered.jpg");
        }
        else{
            imagen.loadUrl("http://comuniops.hol.es/jugadores/" +j +".jpg");
        }

        nombre = (TextView) findViewById(R.id.tvnombrejugador);
        coste = (TextView) findViewById(R.id.tvpreciojugador);
        posicion = (TextView) findViewById(R.id.tvposicionjugador);

        obtDatos(j);

    }

    public void obtDatos(String j){
        AsyncHttpClient client = new AsyncHttpClient();
        String url = "http://comuniops.hol.es/index.php?funcion=getPlayer&player=" +j;
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

    public void ponerDatos(String name, String position, String precio){
        nombre.setText(name);
        coste.setText(precio);
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
}
