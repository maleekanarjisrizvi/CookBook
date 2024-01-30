package com.example.cookbook;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RecipeAdapter extends FirebaseRecyclerAdapter<RecipeModel, RecipeAdapter.RecipeViewHolder> {

    Context context;
    Dialog newdialog;



    public RecipeAdapter(@NonNull FirebaseRecyclerOptions<RecipeModel> options, Context context)
    {
        super(options);
        this.context = context;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recommended, parent, false);
        return new RecipeViewHolder(view);


    }

    @Override
    protected void onBindViewHolder(@NonNull RecipeViewHolder holder, int position, @NonNull RecipeModel model) {

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v)
            {
                DatabaseReference currentUserCategoryRef = FirebaseDatabase.getInstance()
                        .getReference("Users")
                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .child("Recipies")
                        .child(getRef(position).getKey());
                currentUserCategoryRef.removeValue();

                return false;
            }
        });


        holder.tvrecipename.setText(model.getTitle());
        Glide.with(context)
                .load(model.getImageUrl())
                .placeholder(R.drawable.italian)
               .transform(new CenterCrop(), new RoundedCorners(16))
               .error(R.drawable.error)
                .into(holder.ivfood);


        if (model.getIsFav().equals("1"))
        {
            holder.ibheart.setImageResource(R.drawable.heart);
        } else {
            holder.ibheart.setImageResource(R.drawable.heart2);
        }




        holder.ibheart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (model.getIsFav().equals("0"))
                {
                    holder.ibheart.setImageResource(R.drawable.heart);
                    DatabaseReference itemRef = getRef(position);
                    itemRef.child("isFav").setValue("1");
                } else
                {
                    holder.ibheart.setImageResource(R.drawable.heart2);
                    DatabaseReference itemRef = getRef(position);
                    itemRef.child("isFav").setValue("0");
                }
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                newdialog = new Dialog(holder.itemView.getContext());
                newdialog.setContentView(R.layout.activity_recipe_page);
                newdialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
                newdialog.getWindow().setBackgroundDrawable(holder.itemView.getContext().getDrawable(R.drawable.dialog_background));
                newdialog.setCancelable(false);


                ImageView btnBack;
                TextView etentertitle;
                TextView etenteringredients;
                TextView etenterrecipe;
                ImageView imageView;
                ImageButton ibheart;

                 etentertitle = newdialog.findViewById(R.id.etentertitle);
                 etenteringredients = newdialog.findViewById(R.id.etenteringredients);
                 etenterrecipe = newdialog.findViewById(R.id.etenterrecipe);
                 imageView = newdialog.findViewById(R.id.imageView);
                btnBack = newdialog.findViewById(R.id.btnBack);

                etentertitle.setText(model.getTitle());
                etenteringredients.setText(model.getIngredients());
                etenterrecipe.setText(model.getRecipe());
                Glide.with(context)
                        .load(model.getImageUrl())
                        .placeholder(R.drawable.italian)
                        .transform(new CenterCrop(), new RoundedCorners(16))
                        .error(R.drawable.error)
                        .into(imageView);

                newdialog.show();

                btnBack.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        newdialog.dismiss();
                    }
                });


            }
        });






    }

    public static class RecipeViewHolder extends RecyclerView.ViewHolder
    {

        ImageButton ibnextarrow;
        ImageView ivfood;
        ImageButton ibheart;
        Spinner spinnerCuisine;
        TextView tvrecipename;


        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            ibnextarrow = itemView.findViewById(R.id.ibnextarrow);
            ivfood = itemView.findViewById(R.id.ivfood);
            ibheart = itemView.findViewById(R.id.ibheart);
            tvrecipename = itemView.findViewById(R.id.tvrecipename);
            spinnerCuisine = itemView.findViewById(R.id.spinnerCuisine);
        }
    }
}
