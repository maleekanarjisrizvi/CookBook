package com.example.cookbook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Welcome extends AppCompatActivity {
    Button btnlogin;
    Button btnregister;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        init();
        btnlogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i=new Intent(Welcome.this,Login.class);
                        startActivity(i);
                        finish();
            }
        });
        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Welcome.this,Register.class);
                startActivity(i);
                finish();
            }

        });

    }
    public void init()
    {
        btnlogin=findViewById(R.id.btnlogin);
        btnregister=findViewById(R.id.btnRegister);
    }

}