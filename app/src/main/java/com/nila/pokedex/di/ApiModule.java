package com.nila.pokedex.di;

import com.nila.pokedex.model.PokemonApi;
import com.nila.pokedex.model.PokemonService;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ApiModule {
    private static final String BASE_URL = "https://pokeapi.co/api/v2/";


    @Provides
    public PokemonApi providePokemonApi(){
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(PokemonApi.class);
    }

    @Provides
    public PokemonService providePokemonService(){
        return PokemonService.getInstance();
    }
}
