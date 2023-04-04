package com.example.pokemonapiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.pokemonapiapp.model.GitPokemon;
import com.example.pokemonapiapp.model.GitPokemonResponse;
import com.example.pokemonapiapp.model.PokemonsListViewModel;
import com.example.pokemonapiapp.service.GitRepoServiceAPI;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    List<GitPokemon> data=new ArrayList<>();
    public static final String POKEMON_PARAM = "pokemon.name";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        EditText editTextQuery=findViewById(R.id.editTextQuery);
        Button buttonSearch=findViewById(R.id.buttonSearch);
        ListView listViewPokemons=findViewById(R.id.listViewPokemons);

        PokemonsListViewModel listViewModel = new PokemonsListViewModel(this,R.layout.pokemons_list_view_layout,data);
         listViewPokemons.setAdapter(listViewModel);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://pokeapi.co/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

                String query = editTextQuery.getText().toString();
                Log.i("",query);
                GitRepoServiceAPI gitRepoServiceAPI = retrofit.create(GitRepoServiceAPI.class);
                Call<GitPokemonResponse> callGitPokemons = gitRepoServiceAPI.searchPokemons(1);
                callGitPokemons.enqueue(new Callback<GitPokemonResponse>() {
                    @Override
                    public void onResponse(Call<GitPokemonResponse> call, Response<GitPokemonResponse> response) {
                        Log.i("",call.request().url().toString());
                        if (!response.isSuccessful()) {
                            Log.i("infoPok", String.valueOf(response.code()));
                            return;
                        }
                        GitPokemonResponse gitPokemonResponse = response.body();
                        for (GitPokemon pokemon: gitPokemonResponse.pokemons)
                        {
                            data.add(pokemon);
                        }
                       listViewModel.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<GitPokemonResponse> call, Throwable t) {
                        Log.e("error","Error");

                    }
                });


       listViewPokemons.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
               String name = data.get(position).name;
               Log.i("name", "name ");
               Intent intent = new Intent(getApplicationContext(),RepositoryActivity.class);
               intent.putExtra(POKEMON_PARAM,name);
               startActivity(intent);

           }
       });
    }


}