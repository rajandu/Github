package com.rajan.github.adapter;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rajan.github.R;
import com.rajan.github.model.RepoModel;

import java.util.ArrayList;
import java.util.Random;

public class RepoAdapter extends RecyclerView.Adapter<RepoAdapter.ViewHolder> {

    private ArrayList<RepoModel> repoModels;
    private static Context context;
    private OnClickListener onClickListener;

    public RepoAdapter(ArrayList<RepoModel> repoModels, Context context){
       this.repoModels = repoModels;
       this.context = context;
    }

    public ArrayList<RepoModel> getRepoModels() {
        return repoModels;
    }

    public void setRepoModels(ArrayList<RepoModel> repoModels) {
        this.repoModels = repoModels;
    }

    public Context getContext() {
        return context;
    }



    @NonNull
    @Override
    public RepoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.repo_recycler2,parent,false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull RepoAdapter.ViewHolder holder, int position) {

            holder.RepoTextView.setText(repoModels.get(position).getName());
            holder.RepoDescriptionTextView.setText(repoModels.get(position).getDescription());
            holder.recyclerStyle.setBackgroundColor(randomColor());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData((Uri.parse(repoModels.get(position).getHtml_url())));
                    context.startActivity(i);
                }
            });

            holder.shareButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.putExtra(Intent.EXTRA_TEXT, "Repository Name : "+repoModels.get(position).getName()+
                            "\n\nDiscription : "+repoModels.get(position).getDescription()+
                            "\n\nClick here to view the repository : "+repoModels.get(position).getHtml_url());
                    intent.setType("text/plain");
                    context.startActivity(intent);
                }
            });
    }

    @Override
    public int getItemCount() {
        return repoModels.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

       TextView RepoTextView, RepoDescriptionTextView;
       ImageView shareButton, recyclerStyle;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            RepoTextView = itemView.findViewById(R.id.repoTV);
            RepoDescriptionTextView = itemView.findViewById(R.id.RepoDescriptionTV);
            shareButton = itemView.findViewById(R.id.shareButton);
            recyclerStyle = itemView.findViewById(R.id.recyclerStyleImageView);

        }
    }

    public interface OnClickListener {
        void onClick(int position, RepoModel model);
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public int randomColor(){
        Random random = new Random();
        int color = Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256));
        return color;
    }
}
