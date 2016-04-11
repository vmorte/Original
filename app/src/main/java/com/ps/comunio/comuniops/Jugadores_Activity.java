package com.ps.comunio.comuniops;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class Jugadores_Activity extends AppCompatActivity {
    private ListView list;
    private ArrayList<String> jugadores = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jugadores_);

        list = (ListView)findViewById(R.id.lvjugadores);
        obtDatos();

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {

                Toast.makeText(getApplicationContext(), "Cargando: " +jugadores.get(position) , Toast.LENGTH_SHORT).show();
                Intent i = new Intent(Jugadores_Activity.this, Jugador_Activity.class);
                String j = jugadores.get(position);
                i.putExtra("jugador", j);
                startActivity(i);
            }

        });
    }

    public void obtDatos(){
        AsyncHttpClient client = new AsyncHttpClient();
        String url = "http://comuniops.hol.es/index.php?funcion=getPlayers";
        RequestParams params = new RequestParams();
        client.get(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    cargarLista(playersJSON(new String(responseBody)));
                    jugadores = playersJSON(new String(responseBody));
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    //Devuelve un ArrayList con todos los jugadores del comunio
    public ArrayList<String> playersJSON(String response){

        ArrayList<String> lista = new ArrayList<>();
        try{
            JSONArray jsonArray = new JSONArray(response);
            String texto;
            for(int i=0; i<jsonArray.length(); i++){
                texto = jsonArray.getJSONObject(i).getString("name");
                lista.add(texto);
            }

        }catch(Exception e){

        }
        return lista;
    }

    public void cargarLista(ArrayList<String> players){
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, players);
        list.setAdapter(adaptador);
    }
}
