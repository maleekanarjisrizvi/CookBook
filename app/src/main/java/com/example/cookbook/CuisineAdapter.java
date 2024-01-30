package com.example.cookbook;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;



import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class CuisineAdapter extends FirebaseRecyclerAdapter<CuisineModel, CuisineAdapter.CuisineViewHolder> {

    Context parent;



    public CuisineAdapter(@NonNull FirebaseRecyclerOptions<CuisineModel> options, Context context) {
        super(options);
        parent = context;


    }

    @NonNull
    @Override
    public CuisineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.liketocook, parent, false);
        return new CuisineViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull CuisineViewHolder holder, int position, @NonNull CuisineModel model) {
        holder.tvcusinename.setText(model.getCategory());
        holder.ibnextarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(parent,Recipies.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                parent.startActivity(i);

            }
        });
        Glide.with(parent)
                .load(model.getImageUrl())
                .placeholder(R.drawable.italian)
                .transform(new CenterCrop(), new RoundedCorners(16))
                .error(R.drawable.error)
                .into(holder.ivcusine);



    }

    public static class CuisineViewHolder extends RecyclerView.ViewHolder {
        TextView tvcusinename;
        ImageButton ibnextarrow;
        ImageView ivcusine;

        public CuisineViewHolder(@NonNull View itemView) {
            super(itemView);
            tvcusinename = itemView.findViewById(R.id.tvcusinename);
            ibnextarrow = itemView.findViewById(R.id.ibnextarrow);
            ivcusine=itemView.findViewById(R.id.ivcusine);
        }
    }
}
