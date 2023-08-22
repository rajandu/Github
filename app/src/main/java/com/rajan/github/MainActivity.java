package com.rajan.github;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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

public class MainActivity extends AppCompatActivity implements CustomDialog.CustomDialogListener{


    public   String userName="rajandu", repoName="EOTO";
    TextView addFileText;
    ImageView addFileImage;
    RecyclerView mRecyclerView;
    ArrayList<RepoModel> repoModels = new ArrayList<>();
    RecyclerView.Adapter myAdapter;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addFileImage = findViewById(id.addFilePanaImage);
        addFileText = findViewById(id.addFileText);

        addFileText.setVisibility(View.GONE);
        addFileImage.setVisibility(View.GONE);


        mRecyclerView =  findViewById(id.repoRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        myAdapter = new RepoAdapter(repoModels,
                this);

        databaseHelper = DatabaseHelper.getDB(this);

        mRecyclerView.setAdapter(myAdapter);

        loadRepositories();
    }



    public void loadRepositories(){

        RepoEndPoint apiService =
                APIClient.getClient().create(RepoEndPoint.class);

        Call<RepoModel> call = apiService.getRepo(userName,repoName);
        call.enqueue(new Callback<RepoModel>() {
            @Override
            public void onResponse(Call<RepoModel> call, Response<RepoModel> response) {

                repoModels.clear();

                databaseHelper.repoDao().insert(new RepoModel(
                        response.body().getName(),
                        response.body().getDescription(),
                        response.body().getHtml_url()
                        ));

                for (int i = 0; i<databaseHelper.repoDao().getAllRepo().size(); i++){
                    repoModels.add(databaseHelper.repoDao().getAllRepo().get(i));
                }

                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<RepoModel> call, Throwable t) {
                // Log error here since request failed
                Log.e("Repos", t.toString());
            }

        });
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
            openDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    private void openDialog() {
        CustomDialog customDialog = new CustomDialog();
        customDialog.show(getSupportFragmentManager(),"Custom Dialog");
    }

    @Override
    public void applyText(String userName, String repoName) {
        this.userName = userName;
        this.repoName = repoName;
    }
}