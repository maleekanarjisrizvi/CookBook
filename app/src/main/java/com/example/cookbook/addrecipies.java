package com.example.cookbook;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class addrecipies extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    EditText etentertitle, etenteringredients, etenterrecipe;
    ImageButton ibbackarrow, ibuploadimage;
    Button btnadd;
    private Uri imageUri;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;
    private Spinner spinnerCuisine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addrecipies);
        init();


        String[] category = {"Italian", "French", "Asian", "Japanese", "Pakistani"};


        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, category);
        spinnerCuisine.setAdapter(adapter);

        ibuploadimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        ibbackarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(addrecipies.this, Home.class);
                startActivity(i);
                finish();
            }
        });

        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UploadRecipeinfo();
            }
        });
    }

    public void init() {
        etentertitle = findViewById(R.id.etentertitle);
        ibuploadimage = findViewById(R.id.ibuploadimage);
        btnadd = findViewById(R.id.btnadd);
        ibbackarrow = findViewById(R.id.ibbackarrow);
        etenteringredients = findViewById(R.id.etenteringredients);
        etenterrecipe = findViewById(R.id.etenterrecipe);
        spinnerCuisine = findViewById(R.id.spinnerCuisine);
        databaseReference = FirebaseDatabase.getInstance().getReference("Recipies");
        storageReference = FirebaseStorage.getInstance().getReference("Recipies_image");
    }

    private void openFileChooser() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();

        }
    }


    private void UploadRecipeinfo()
    {
        String category = spinnerCuisine.getSelectedItem().toString();
        String title = etentertitle.getText().toString().trim();
        String ingredients = etenteringredients.getText().toString().trim();
        String recipe = etenterrecipe.getText().toString().trim();
        String fav = "0";

        if (imageUri != null && !title.isEmpty() && !ingredients.isEmpty() && !recipe.isEmpty())
        {
            StorageReference fileReference = storageReference.child(System.currentTimeMillis() + "." + getFileExtension(imageUri));
            fileReference.putFile(imageUri)
                    .addOnSuccessListener(taskSnapshot -> {
                        fileReference.getDownloadUrl().addOnSuccessListener(uri ->
                        {
                            String imageUrl = uri.toString();
                            String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

                            RecipeModel recipeModel = new RecipeModel(fav,category, title, ingredients, recipe, imageUrl);

                            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
                            reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .child("Recipies").push().setValue(recipeModel);



                            //what the hell.. where is firebase push wala logic
                            showImageUploadedMsg();
                            Intent intent = new Intent(addrecipies.this, Recipies.class);
                            finish();
                        });
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(addrecipies.this, "Image upload failed", Toast.LENGTH_SHORT).show();
                    });
        } else {
            Toast.makeText(this, "Please fill in all fields and select an image", Toast.LENGTH_SHORT).show();
        }
    }

    private void showImageUploadedMsg() {
        Toast.makeText(addrecipies.this, "Image uploaded!", Toast.LENGTH_SHORT).show();
    }

    private String getFileExtension(Uri uri) {
        return MimeTypeMap.getSingleton().getExtensionFromMimeType(getContentResolver().getType(uri));
    }
}
