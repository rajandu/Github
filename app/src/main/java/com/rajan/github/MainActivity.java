package com.rajan.github;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.rajan.github.R.id;
import com.rajan.github.adapter.RepoAdapter;
import com.rajan.github.database.DatabaseHelper;
import com.rajan.github.model.RepoModel;
import com.rajan.github.rest.APIClient;
import com.rajan.github.rest.RepoEndPoint;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    TextView addFileText;
    ImageView addFileImage;
    RecyclerView mRecyclerView;
    ArrayList<RepoModel> repoModels = new ArrayList<>();
    RecyclerView.Adapter myAdapter;
    DatabaseHelper databaseHelper;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addFileImage = findViewById(id.addFilePanaImage);
        addFileText = findViewById(id.addFileText);


        mRecyclerView =  findViewById(id.repoRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        myAdapter = new RepoAdapter(repoModels,
                this);

        databaseHelper = DatabaseHelper.getDB(this);
        context = this;

        mRecyclerView.setAdapter(myAdapter);

        repoModels.addAll(databaseHelper.repoDao().getAllRepo());

        if(repoModels.size()!=0){
            addFileText.setVisibility(View.GONE);
            addFileImage.setVisibility(View.GONE);
        }

        myAdapter.notifyDataSetChanged();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        MenuItem menuItem = menu.findItem(R.id.action_add);
        return super.onCreateOptionsMenu(menu);
    }

    

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.action_add){
            chooseAction(this);
        }
        return super.onOptionsItemSelected(item);
    }

    public void chooseAction(Context context){

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setTitle("Choose the action");
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setContentView(R.layout.dialog_action);

        Button createRepository = dialog.findViewById(id.create_new_button);
        Button trackExisting = dialog.findViewById(id.track_existing_button);

        dialog.show();

        createRepository.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCustomDialog(context,true);
                dialog.dismiss();
            }
        });

        trackExisting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCustomDialog(context,false);
                dialog.dismiss();
            }
        });


    }

    public void showCustomDialog(Context context, boolean repoCreation){
        final Dialog dialog =new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.custom_dialog);

        Button cancelButton = dialog.findViewById(id.cancel_button);
        Button okButton = dialog.findViewById(id.ok_button);
        EditText userNameEditText = dialog.findViewById(id.userNameEditText);
        EditText repoEditText = dialog.findViewById(id.repoNameEditText);
        TextView dialogTitle = dialog.findViewById(id.Repo);

        if(repoCreation){
            userNameEditText.setHint("Name of Repository");
            repoEditText.setHint("Description of Repository");
            dialogTitle.setText("Create Repository");

        }

        dialog.show();
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String owner = userNameEditText.getText().toString().trim();
                String repo = repoEditText.getText().toString().trim();
                if(owner.isEmpty() || repo.isEmpty()){
                    Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show();
                }else {
                    if(repoCreation){
                        loadRepositories(owner,repo, dialog,true);
                    }else{
                        loadRepositories(owner,repo, dialog,false);
                    }
                    dialog.dismiss();
                }
            }
        });

    }

    public void loadRepositories(String repoOwner, String repoName, Dialog dialog, boolean repoCreation){

        RepoEndPoint apiService =
                APIClient.getClient().create(RepoEndPoint.class);

        Call<RepoModel> call;
        if(repoCreation){
            // here repoOwner denotes organization repo name and repoName denotes repo descri.
            call = apiService.createOrgRepo("GharPeShikshaa",new RepoModel(repoOwner,repoName,getOrgRepoUrl(repoOwner)));
        }else{
            call = apiService.getUserRepo(repoOwner,repoName);
        }

        call.enqueue(new Callback<RepoModel>() {
            @Override
            public void onResponse(Call<RepoModel> call, Response<RepoModel> response) {
                if(response.body()!=null){
                    repoModels.add(response.body());
                    databaseHelper.repoDao().insert(response.body());
                    myAdapter.notifyDataSetChanged();
                }else{
                    Toast.makeText(getApplicationContext(), "Please enter a valid pubic repository details", Toast.LENGTH_SHORT).show();
                }

            }
            @Override
            public void onFailure(Call<RepoModel> call, Throwable t) {
                // Log error here since request failed
                dialog.dismiss();
                Log.e("Repos", t.toString());
            }

        });
    }
    public String getOrgRepoUrl(String name){
        return "https://github.com/gharpeshikshaa"+name;
    }
}