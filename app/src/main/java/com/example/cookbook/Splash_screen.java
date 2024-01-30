package com.example.cookbook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class Splash_screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run()
            {
                if(FirebaseAuth.getInstance().getCurrentUser() != null)
                {
                    //already logged in
                    Toast.makeText(Splash_screen.this, "Already Login", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(Splash_screen.this, Home.class);
                    startActivity(i);
                    finish();
                }
                else
                {
                    //if not logged in
                    Intent i = new Intent(Splash_screen.this, Welcome.class);
                    startActivity(i);
                    finish();
                }


            }
        }, 2000);
    }
}