package com.example.cookbook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import android.webkit.MimeTypeMap;
import androidx.annotation.Nullable;
import com.squareup.picasso.Picasso;




import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.FirebaseStorage;


public class Addcuisine extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    EditText etentercategory;
    ImageButton ibuploadimage;
    Button btnadd;
    ImageButton ibbackarrow;
    ImageView ivimage;
    private Uri imageUri;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addcuisine);

        init();



        ibuploadimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();


            }
        });

        ibbackarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Addcuisine.this, Home.class);
                startActivity(i);
                finish();
            }
        });

        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Category = etentercategory.getText().toString().trim();
                if (!Category.isEmpty()) {

                   uploadCuisineInfo(Category);
                } else {
                    Toast.makeText(Addcuisine.this, "Enter cuisine category", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    public void init() {
        etentercategory = findViewById(R.id.etentercategory);
        ibuploadimage = findViewById(R.id.ibuploadimage);
        btnadd = findViewById(R.id.btnadd);
        ibbackarrow = findViewById(R.id.ibbackarrow);

        databaseReference = FirebaseDatabase.getInstance().getReference("Cusines");
        storageReference = FirebaseStorage.getInstance().getReference("Cusines_image");
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
    private void uploadCuisineInfo(String category) {
        if (imageUri != null) {
            StorageReference fileReference = storageReference.child(System.currentTimeMillis() + "." + getFileExtension(imageUri));
            fileReference.putFile(imageUri)
                    .addOnSuccessListener(taskSnapshot ->
                    {
                        fileReference.getDownloadUrl().addOnSuccessListener(uri ->
                        {
                            String imageUrl = uri.toString();
                            String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

                            CuisineModel cuisineModel = new CuisineModel(category,imageUrl,userId);

                            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
                            reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .child("Cusines").push().setValue(cuisineModel);

                            Toast.makeText(Addcuisine.this, "Cuisine info uploaded successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Addcuisine.this, Cusines.class);
                            finish();
                        });
                    })
                    .addOnFailureListener(e -> Toast.makeText(Addcuisine.this, "Upload failed", Toast.LENGTH_SHORT).show());
        } else {
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
        }
    }
    private String getFileExtension(Uri uri) {
        return MimeTypeMap.getSingleton().getExtensionFromMimeType(getContentResolver().getType(uri));
    }

}

