package com.example.cookbook;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.cookbook.RecipeAdapter;



import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.content.Intent;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Home extends AppCompatActivity {

    TextView tvliketocook,tvrecommended;
    //ImageButton ibadd,ibheart1,ibdelete;
    ImageView ivshowcusine,ivshowrecipies,ivadd,ivheart;
    RecyclerView rvrecipies;
    RecyclerView rvcusines;
    RecyclerView.LayoutManager recipemanager;
    RecyclerView.LayoutManager cusinemanager;
             RecipeAdapter adapterrecipies;
      CuisineAdapter adaptercusinies;
    Button btnaddcuisine;
    Button btnaddrecipies;
    ImageView btnLogout;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        init();
        ivheart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Home.this, Favourites.class);
                startActivity(i);
              //R.id wala kaam
            }
        });
        ivshowrecipies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Home.this, Recipies.class);
                startActivity(i);;
                finish();

            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                FirebaseAuth.getInstance().signOut();
                Intent i=new Intent(Home.this, Welcome.class);
                startActivity(i);;
                finish();
            }
        });

        ivshowcusine.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent i=new Intent(Home.this, Cusines.class);
              startActivity(i);;
              finish();

          }
      });
        ivadd.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              View view1= LayoutInflater.from(Home.this).
                      inflate(R.layout.activity_options,null);
              AlertDialog.Builder addoptionsdialouge=new AlertDialog.Builder(Home.this)
                      .setTitle("Choose Option")
                      .setView(view1);
              btnaddcuisine=view1.findViewById(R.id.btnaddcuisine);
              btnaddrecipies=view1.findViewById(R.id.btnaddrecipies);
              btnaddcuisine.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                      startActivity(new Intent(Home.this, Addcuisine.class));
                  }
              });
              btnaddrecipies.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                      startActivity(new Intent(Home.this,addrecipies.class));
                  }
              });
              addoptionsdialouge.create();
                   addoptionsdialouge.show();




          }
      });



    }

    private void setupRecyclerview()
    {
      //recipe query
        Query query = FirebaseDatabase.getInstance()
                .getReference("Users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("Recipies");
        FirebaseRecyclerOptions<RecipeModel> options =
                new FirebaseRecyclerOptions.Builder<RecipeModel>()
                        .setQuery(query, RecipeModel.class)
                        .build();

        rvrecipies = findViewById(R.id.rvrecipies);


        recipemanager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvrecipies.setLayoutManager(recipemanager);

        adapterrecipies = new RecipeAdapter(options, getApplicationContext());
        rvrecipies.setAdapter(adapterrecipies);

        adapterrecipies.startListening();
        //cusine query
        Query querycuisine = FirebaseDatabase.getInstance()
                .getReference("Users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("Cusines");


        FirebaseRecyclerOptions<CuisineModel> optionscuisine =
                new FirebaseRecyclerOptions.Builder<CuisineModel>()
                        .setQuery(querycuisine, CuisineModel.class)
                        .build();

        rvcusines = findViewById(R.id.rvcusines);

        cusinemanager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvcusines.setLayoutManager(cusinemanager);


        adaptercusinies  = new CuisineAdapter(optionscuisine,getApplicationContext());
        rvcusines.setAdapter(adaptercusinies);



        adaptercusinies.startListening();
    }

   private void init()
   {

        tvliketocook=findViewById(R.id.tvliketocook);
        tvrecommended=findViewById(R.id.tvrecommended);
        rvrecipies=findViewById(R.id.rvrecipies);
        rvcusines=findViewById(R.id.rvcusines);
       ivadd=findViewById(R.id.ivadd);
       ivshowcusine=findViewById(R.id.ivshowcusine);
       ivshowrecipies=findViewById(R.id.ivshowrecipies);
        btnLogout=findViewById(R.id.btnLogout);
        ivheart=findViewById(R.id.ivheart);

    }


    @Override
    protected void onStart() {
        super.onStart();
        setupRecyclerview();
        adaptercusinies.startListening();
        adapterrecipies.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adaptercusinies.stopListening();
        adapterrecipies.stopListening();
    }
}