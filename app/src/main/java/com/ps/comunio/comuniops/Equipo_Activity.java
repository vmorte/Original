package com.ps.comunio.comuniops;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class Equipo_Activity extends AppCompatActivity {
    private ListView plantilla;
    private int cont = 1;
    private ArrayList<String> equipo = new ArrayList<>();
    String e;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipo_);

        e = getIntent().getStringExtra("user");

        //Mostrar plantilla de mi equipo
        plantilla = (ListView)findViewById(R.id.listView);
        obtDatos(e);

        ImageView i1 = (ImageView) findViewById(R.id.ivAlineacion);
        i1.setImageResource(R.mipmap.alineacion1);

        //Pestañas de equipo
        Resources res1 = getResources();
        TabHost tabs = (TabHost) findViewById(android.R.id.tabhost);
        tabs.setup();
        TabHost.TabSpec spec = tabs.newTabSpec("Pestaña 1");
        spec.setContent(R.id.tab3);
        spec.setIndicator("Plantilla");
        tabs.addTab(spec);

        tabs.setup();
        TabHost.TabSpec spec1 = tabs.newTabSpec("Pestaña 2");
        spec1.setContent(R.id.tab4);
        spec1.setIndicator("Alineación");
        tabs.addTab(spec1);

        plantilla.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {

                Toast.makeText(getApplicationContext(), "Cargando: " + equipo.get(position), Toast.LENGTH_SHORT).show();
                String s = getIntent().getStringExtra("user");
                Intent i = new Intent(Equipo_Activity.this, Ficha_Activity.class);
                String v = equipo.get(position);
                String [] extra = {s, v};
                i.putExtra("jugador", extra);
                startActivity(i);
                finish();
            }

        });

    }

    public void obtDatos(String e){
        AsyncHttpClient client = new AsyncHttpClient();
        String url = "http://comuniops.hol.es/index.php?funcion=getPlayersXuser&username=" +e;
        RequestParams params = new RequestParams();
        client.get(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    cargarLista(equipoJSON(new String(responseBody)));
                    equipo = equipoJSON(new String(responseBody));
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }


    public ArrayList<String> equipoJSON(String response){

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
        plantilla.setAdapter(adaptador);
    }


    //Cambio de alineación
    public void confirmacionCambio(View v) {
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle("Cambio de alineación");
        b.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if(cont == 1) {
                    ImageView imagen = (ImageView) findViewById(R.id.ivAlineacion);
                    imagen.setImageResource(R.mipmap.alineacion2);
                    cont = 2;
                    Toast.makeText(Equipo_Activity.this, "Alineación 3-4-3", Toast.LENGTH_LONG).show();
                }
                else{
                    ImageView imagen1 = (ImageView) findViewById(R.id.ivAlineacion);
                    imagen1.setImageResource(R.mipmap.alineacion1);
                    cont = 1;
                    Toast.makeText(Equipo_Activity.this, "Alineación 1-5-4", Toast.LENGTH_LONG).show();
                }
            }
        });
        b.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(Equipo_Activity.this, "Operación cancelada", Toast.LENGTH_LONG).show();
            }
        });
        AlertDialog d = b.create();
        d.show();
    }
}
