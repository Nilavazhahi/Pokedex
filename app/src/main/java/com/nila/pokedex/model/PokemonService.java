package com.nila.pokedex.model;

import com.nila.pokedex.di.DaggerApiComponent;
import com.nila.pokedex.view.Constant;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

public class PokemonService {

    private static PokemonService instance;

    @Inject
    public PokemonApi api;

    @Inject
    public String name;

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

    public Single<PokemonInfoModel> getPokemonInfo(){
        return api.getPokemonInfo(Constant.getPokedexName());
    }

}

