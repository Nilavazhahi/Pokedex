package com.nila.pokedex.model;

import java.util.List;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PokemonApi {
    @GET("pokemon?limit=151")
    Single<PokemonResponse> getPokemonResponse();

    @GET("pokemon/{name}")
    Single<PokemonInfoModel> getPokemonInfo(@Path(value="name") String name);

}
