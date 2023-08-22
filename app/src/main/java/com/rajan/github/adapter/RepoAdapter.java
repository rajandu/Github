package com.rajan.github.adapter;

import android.content.Context;
import android.content.Intent;
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

public class RepoAdapter extends RecyclerView.Adapter<RepoAdapter.ViewHolder> {

    private ArrayList<RepoModel> repoModels;
    private Context context;
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

    public void setContext(Context context) {
        this.context = context;
    }


    @NonNull
    @Override
    public RepoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.repo_recycler,parent,false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull RepoAdapter.ViewHolder holder, int position) {

            holder.RepoTextView.setText(repoModels.get(position).getName());
            holder.RepoDescriptionTextView.setText(repoModels.get(position).getDescription());

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
       ImageView shareButton;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            RepoTextView = itemView.findViewById(R.id.RepoTV);
            RepoDescriptionTextView = itemView.findViewById(R.id.RepoDescriptionTV);
            shareButton = itemView.findViewById(R.id.shareButton);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    
                }
            });
        }
    }

    public interface OnClickListener {
        void onClick(int position, RepoModel model);
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
}
