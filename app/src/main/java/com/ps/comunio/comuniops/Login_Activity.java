package com.ps.comunio.comuniops;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.*;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;


public class Login_Activity extends AppCompatActivity {

    private Button btLogin;
    private Button btRegistro;
    private EditText etUser;
    private EditText etPass;
    private ArrayList<String> listaU = new ArrayList<>();
    private ArrayList<String> listaP = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        obtDatos();

        etUser = (EditText) findViewById(R.id.editTextUserLogin);
        etPass = (EditText) findViewById(R.id.editTextPassLogin);
        btLogin = (Button) findViewById(R.id.buttonLoginLogin);

        btLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Log.d("Login", "Boton Login pulsado");
                //Log.d("Login", String.valueOf(tvUser.getText()));
                String sacarUsername = etUser.getText().toString();
                String sacarPassword = etPass.getText().toString();


                if (verifPass(sacarUsername, sacarPassword)){
                    Log.d("Login", "Usuario Valido");
                    Log.d("Login", "Pass Valido");
                    Intent i = new Intent(Login_Activity.this, Home_Activity.class);
                    i.putExtra("user", etUser.getText() + "");

                    startActivity(i);
                    finish();

                }else{
                    Toast.makeText(Login_Activity.this, "Usuario o Contraseña erróneos.",
                            Toast.LENGTH_SHORT).show();

                }
            }
        });
        btRegistro = (Button) findViewById(R.id.buttonRegistrarLogin);
        btRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Login_Activity.this, Registrar_Activity.class);
                startActivity(i);


            }
        });
    }

    public void obtDatos(){
        AsyncHttpClient client = new AsyncHttpClient();
        String url = "http://comuniops.hol.es/index.php?funcion=getUsers";
        RequestParams params = new RequestParams();
        client.get(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    listaU = usersJSON(new String(responseBody));
                    listaP = passJSON(new String(responseBody));
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    public ArrayList<String> usersJSON(String response){

        ArrayList<String> lista = new ArrayList<>();
        try{
            JSONArray jsonArray = new JSONArray(response);
            String texto;
            for(int i=0; i<jsonArray.length(); i++){
                texto = jsonArray.getJSONObject(i).getString("username");
                lista.add(texto);
            }

        }catch(Exception e){

        }
        return lista;
    }

    public ArrayList<String> passJSON(String response){

        ArrayList<String> lista = new ArrayList<>();
        try{
            JSONArray jsonArray = new JSONArray(response);
            String texto;
            for(int i=0; i<jsonArray.length(); i++){
                texto = jsonArray.getJSONObject(i).getString("password");
                lista.add(texto);
            }

        }catch(Exception e){

        }
        return lista;
    }

    public Boolean verifPass(String u, String u2){
        for (int i=0; i<listaU.size(); i++) {
            if ((listaU.get(i).equals(u)) && (listaP.get(i).equals(u2))){
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }



}
