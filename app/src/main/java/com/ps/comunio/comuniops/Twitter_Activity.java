package com.ps.comunio.comuniops;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Twitter_Activity extends AppCompatActivity {
    Button btAcept;
    EditText etTwitter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twitter);

        etTwitter = (EditText) findViewById(R.id.editTextCuenta);
        btAcept= (Button) findViewById(R.id.button3);
        btAcept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(String.valueOf(etTwitter.getText()).contains("@")){
                    Toast.makeText(Twitter_Activity.this, "Se ha enviado un correo de confirmación", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(Twitter_Activity.this, "Introduce una cuenta de Twitter o correo asociado válido", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}
