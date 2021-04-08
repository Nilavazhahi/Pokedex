package com.nila.pokedex.model;

import java.util.List;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.GET;

public interface PokemonApi {
    @GET("pokemon")
    Single<PokemonResponse> getPokemonResponse();
}
