package com.ps.comunio.comuniops;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Facebook_Activity extends AppCompatActivity {
    Button btAcept;
    EditText etFacebook;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook);

        etFacebook = (EditText) findViewById(R.id.editText2);
        btAcept= (Button) findViewById(R.id.button4);
        btAcept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!String.valueOf(etFacebook.getText()).isEmpty()){
                    Toast.makeText(Facebook_Activity.this, "Se ha enviado un correo de confirmación", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(Facebook_Activity.this, "Introduce una cuenta de Facebook o correo asociado válido", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}

