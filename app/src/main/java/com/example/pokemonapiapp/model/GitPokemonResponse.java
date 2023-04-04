package com.example.pokemonapiapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class GitPokemonResponse {

    public int count;

    @SerializedName("results")
    public List<GitPokemon> pokemons = new ArrayList();
}
