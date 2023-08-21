package com.rajan.github;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
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
import com.rajan.github.model.RepoModel;
import com.rajan.github.rest.APIClient;
import com.rajan.github.rest.RepoEndPoint;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements CustomDialog.CustomDialogListener{


    private  String userName="rajandu", repoName="eoto";
    TextView addFileText;
    ImageView addFileImage;
    RecyclerView mRecyclerView;
    ArrayList<RepoModel> repoModels = new ArrayList<>();
    RecyclerView.Adapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addFileImage = findViewById(id.addFilePanaImage);
        addFileText = findViewById(id.addFileText);

        addFileImage.setVisibility(View.GONE);
        addFileText.setVisibility(View.GONE);

        //addFileText.setText(userName+" "+repoName);

        mRecyclerView =  findViewById(id.repoRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        myAdapter = new RepoAdapter(repoModels,
                this);

        mRecyclerView.setAdapter(myAdapter);

        loadRepositories();
    }

    public void loadRepositories(){

        RepoEndPoint apiService =
                APIClient.getClient().create(RepoEndPoint.class);

        Call<List<RepoModel>> call = apiService.getRepo(userName);
        call.enqueue(new Callback<List<RepoModel>>() {
            @Override
            public void onResponse(Call<List<RepoModel>> call, Response<List<RepoModel>> response) {

                repoModels.clear();
                repoModels.addAll(response.body());
                myAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<List<RepoModel>> call, Throwable t) {
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
    public void applyText(String userNamee, String repoNamee) {
        this.userName = userNamee;
        this.repoName = repoNamee;
    }
}