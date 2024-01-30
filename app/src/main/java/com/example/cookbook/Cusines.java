package com.example.cookbook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.database.Query;

public class Cusines extends AppCompatActivity {



    private RecyclerView rvcusines;
    private CuisineAdapter adapter;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cusines);


        ImageButton ibbackarrow = findViewById(R.id.ibbackarrow);


        ibbackarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Cusines.this, Home.class);
                startActivity(i);
                finish();
            }
        });

        setupRecyclerView();
    }

    private void setupRecyclerView() {

        Query query = FirebaseDatabase.getInstance()
                .getReference("Users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("Cusines");


        FirebaseRecyclerOptions<CuisineModel> options =
                new FirebaseRecyclerOptions.Builder<CuisineModel>()
                        .setQuery(query, CuisineModel.class)
                        .build();


        rvcusines = findViewById(R.id.rvcusines);
        rvcusines.setLayoutManager(new GridLayoutManager(this,2));


        adapter = new CuisineAdapter(options,getApplicationContext());
        rvcusines.setAdapter(adapter);



        adapter.startListening();
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
