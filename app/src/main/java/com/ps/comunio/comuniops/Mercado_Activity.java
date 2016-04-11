package com.ps.comunio.comuniops;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

public class Mercado_Activity extends AppCompatActivity {
    private ListView ventas;
    private ArrayList<String> enVenta = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mercado);

        ventas = (ListView) findViewById(R.id.listViewMercado);
        obtDatos();

        ventas.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick (AdapterView < ? > arg0, View arg1,int position, long id){

                Toast.makeText(getApplicationContext(), "Cargando: " + enVenta.get(position), Toast.LENGTH_SHORT).show();
                String s = getIntent().getStringExtra("user");
                Intent i = new Intent(Mercado_Activity.this, Venta_Activity.class);
                String v = enVenta.get(position);
                String [] extra = {s, v};
                i.putExtra("jugador", extra);
                startActivity(i);
                finish();
            }

        });
    }

    public void obtDatos(){
        AsyncHttpClient client = new AsyncHttpClient();
        String url = "http://comuniops.hol.es/index.php?funcion=getPlayersXuser&username=mercado";
        RequestParams params = new RequestParams();
        client.get(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    cargarLista(ventaJSON(new String(responseBody)));
                    enVenta = ventaJSON(new String(responseBody));
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    //Devuelve un ArrayList con todos los jugadores en el mercado
    public ArrayList<String> ventaJSON(String response){

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
        ArrayAdapter<String> adaptador = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, players);
        ventas.setAdapter(adaptador);
    }
}



