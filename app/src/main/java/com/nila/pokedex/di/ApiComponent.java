package com.nila.pokedex.di;

import com.nila.pokedex.model.PokemonService;
import com.nila.pokedex.viewmodel.PokemonViewModel;

import dagger.Component;

@Component(modules = {ApiModule.class})
public interface ApiComponent {

    void inject (PokemonService pokemonService);

    void inject (PokemonViewModel pokemonViewModel);

}
