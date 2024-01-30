package com.example.cookbook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class Favourites extends AppCompatActivity {

    private RecyclerView rvrecipies;
    private RecipeAdapter recipeAdapter;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipies);


        ImageButton ibBackArrow = findViewById(R.id.ibbackarrow);


        ibBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Favourites.this, Home.class);
                startActivity(i);
                finish();
            }
        });

        setupRecyclerView();
    }

    private void setupRecyclerView()
    {
        String selectedCategory = "categories";



        Query query = FirebaseDatabase.getInstance()
                .getReference("Users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("Recipies")
                .orderByChild("isFav")
                .equalTo("1");


        FirebaseRecyclerOptions<RecipeModel> options =
                new FirebaseRecyclerOptions.Builder<RecipeModel>()
                        .setQuery(query, RecipeModel.class)
                        .build();

        rvrecipies = findViewById(R.id.rvrecipies);
        rvrecipies.setLayoutManager(new GridLayoutManager(this, 2));

        recipeAdapter = new RecipeAdapter(options, getApplicationContext());
        rvrecipies.setAdapter(recipeAdapter);

        recipeAdapter.startListening();
    }
    @Override
    protected void onStart() {
        super.onStart();
        recipeAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        recipeAdapter.stopListening();
    }
}