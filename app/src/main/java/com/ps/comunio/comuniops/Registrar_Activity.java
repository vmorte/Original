package com.ps.comunio.comuniops;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class Registrar_Activity extends AppCompatActivity {
    private EditText etUserNew;
    private EditText etPassNew;
    private EditText etPass2New;
    private ArrayList<String> listaU = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_);
        checkDatos();

        Button registrar = (Button) findViewById(R.id.buttonregistarregistrar);
        etUserNew = (EditText) findViewById(R.id.editTextusername);
        etPassNew = (EditText) findViewById(R.id.editTextpass1);
        etPass2New = (EditText) findViewById(R.id.editTextpass2);

        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Login", "Boton Login pulsado");
                //Log.d("Login", String.valueOf(tvUser.getText()));
                String aux = String.valueOf(etPass2New.getText());
                String usr = String.valueOf(etUserNew.getText());
                if((!(aux.equals(""))) && (!(usr.equals("")))) {
                    if (!(tieneUsuario(usr))) {
                        if (String.valueOf(etPassNew.getText()).equals(aux)) {
                            obtDatos(usr, aux);
                            Toast.makeText(Registrar_Activity.this, "Usuario Registrado!", Toast.LENGTH_SHORT).show();

                            Intent i = new Intent(Registrar_Activity.this, Login_Activity.class);
                            i.putExtra("user", etUserNew.getText().toString());
                            i.putExtra("pass", etPassNew.getText().toString());
                            startActivity(i);
                            finish();

                        } else {
                            Toast.makeText(Registrar_Activity.this, "Las contraseñas no coinciden",
                                    Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(Registrar_Activity.this, "El usuario ya existe",
                                Toast.LENGTH_SHORT).show();

                    }
                }
                else{
                    Toast.makeText(Registrar_Activity.this, "Introduce un usuario/contraseña válido",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //Este método permitirá comprobar si ya existe el usuario
    public void checkDatos(){
        AsyncHttpClient client = new AsyncHttpClient();
        String url = "http://comuniops.hol.es/index.php?funcion=getUsers";
        RequestParams params = new RequestParams();
        client.get(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    listaU = usersJSON(new String(responseBody));
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    //Este método permitirá añadir un nuevo usiario a la base de datos
    public void obtDatos(String s, String s1){
        AsyncHttpClient client = new AsyncHttpClient();
        String url = "http://comuniops.hol.es/index.php?funcion=newUser&username=" +s +"&password=" +s1;
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

    //Devuelve un arrayList con todos los usuarios existentes
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

    //Devuelve true si existe el usuario en la base de datos
    public Boolean tieneUsuario(String u){
        for (String i:listaU) {
            if (i.equals(u)){
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }
}
