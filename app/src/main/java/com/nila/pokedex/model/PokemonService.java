package com.nila.pokedex.model;

import com.nila.pokedex.di.DaggerApiComponent;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

public class PokemonService {

    private static PokemonService instance;

    @Inject
    public PokemonApi api;

    private PokemonService(){
        DaggerApiComponent.create().inject(this);
    }


    public static PokemonService getInstance(){
        if(instance == null){
            instance = new PokemonService();
        }
        return instance;
    }

    public Single<PokemonResponse> getPokemonResponse(){
        return api.getPokemonResponse();
    }

}

