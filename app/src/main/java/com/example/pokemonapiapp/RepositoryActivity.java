package com.example.pokemonapiapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pokemonapiapp.model.GitPokemonResponse;
import com.example.pokemonapiapp.model.GitRepo;
import com.example.pokemonapiapp.service.GitRepoServiceAPI;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RepositoryActivity extends AppCompatActivity {
    List<String> data = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.repository_layout);
        Intent intent = getIntent();
        String name = intent.getStringExtra(MainActivity.POKEMON_PARAM);
        setTitle("Repositories");
        TextView textViewName = findViewById(R.id.textViewName);
        ListView listViewRepositories = findViewById(R.id.listViewRepositories);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,data);
        listViewRepositories.setAdapter(arrayAdapter);
        textViewName.setText(name);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://pokeapi.co/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GitRepoServiceAPI gitRepoServiceAPI = retrofit.create(GitRepoServiceAPI.class);
         Call<List<GitRepo>> reposCall = gitRepoServiceAPI.pokemonRepositories(name);
         reposCall.enqueue(new Callback<List<GitRepo>>() {
             @Override
             public void onResponse(Call<List<GitRepo>> call, Response<List<GitRepo>> response) {
                 if(!response.isSuccessful()) {
                     Log.e("error",String.valueOf(response.code()) );
                     return;
                 }
                    List<GitRepo> gitRepos = response.body();
                 for(GitRepo gitRepo : gitRepos) {
                     String content = "";
                     content+= gitRepo.url+"\n";
                 }
                 arrayAdapter.notifyDataSetChanged();
             }

             @Override
             public void onFailure(Call<List<GitRepo>> call, Throwable t) {

             }
         });
        }
    }

