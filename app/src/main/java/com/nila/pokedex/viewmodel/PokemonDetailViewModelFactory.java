package com.nila.pokedex.viewmodel;

import android.app.Application;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class PokemonDetailViewModelFactory implements ViewModelProvider.Factory {
    private Application mApplication;
    private String name;


    public PokemonDetailViewModelFactory(Application application, String name) {
        mApplication = application;
        this.name = name;
    }


    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new PokemonDetailViewModel(mApplication, name);
    }
}
