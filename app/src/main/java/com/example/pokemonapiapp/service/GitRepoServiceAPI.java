package com.example.pokemonapiapp.service;

import com.example.pokemonapiapp.model.GitPokemonResponse;
import com.example.pokemonapiapp.model.GitRepo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GitRepoServiceAPI {

    @GET("api/v2/pokemon")
    public Call<GitPokemonResponse> searchPokemons ( @Query("page") int query);

    @GET("/pokemon/{u}")
    public Call<List<GitRepo>> pokemonRepositories (@Path("u") String name);
}
