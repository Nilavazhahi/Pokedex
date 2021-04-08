package com.nila.pokedex.model;

import com.google.gson.annotations.SerializedName;

public class PokemonModel {

    @SerializedName("name")
    String pokemonName;

    @SerializedName("url")
    String pokemonUrl;

    public PokemonModel(String pokemonName, String pokemonUrl) {
        this.pokemonName = pokemonName;
        this.pokemonUrl = pokemonUrl;
    }

    public String getPokemonName() {
        return pokemonName;
    }

    public void setPokemonName(String pokemonName) {
        this.pokemonName = pokemonName;
    }

    public String getPokemonUrl() {
        return pokemonUrl;
    }

    public void setPokemonUrl(String pokemonUrl) {
        this.pokemonUrl = pokemonUrl;
    }

    public String getImageUrl(){

        String s = pokemonUrl;
        String url = s.substring(0,s.lastIndexOf("/"));
        String idStr = url.substring(url.lastIndexOf('/') + 1);
        int index = Integer.parseInt(idStr);

        return "https://pokeres.bastionbot.org/images/pokemon/"+index+".png";

    }

}
