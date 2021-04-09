package com.nila.pokedex.viewmodel;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.nila.pokedex.di.DaggerApiComponent;
import com.nila.pokedex.model.PokemonInfoModel;
import com.nila.pokedex.model.PokemonModel;
import com.nila.pokedex.model.PokemonResponse;
import com.nila.pokedex.model.PokemonService;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class PokemonDetailViewModel extends ViewModel {
    public MutableLiveData<PokemonInfoModel> pokemonDetails = new MutableLiveData<PokemonInfoModel>();
    public MutableLiveData<Boolean> loadError = new MutableLiveData<Boolean>();
    public MutableLiveData<Boolean> loading = new MutableLiveData<Boolean>();

    @Inject
    public PokemonService pokemonService;

    private CompositeDisposable disposable = new CompositeDisposable();
    Application application;
    String pokename;

    public PokemonDetailViewModel(Application application, String name){
        super();
        this.application = application;
        this.pokename = name;
        DaggerApiComponent.create().inject(this);
    }


    public void refresh(){
        fetchPokemonDetails();
    }

    private void fetchPokemonDetails(){

        loading.setValue(true);
        disposable.add(
                pokemonService.getPokemonInfo()
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<PokemonInfoModel>() {
                            @Override
                            public void onSuccess(PokemonInfoModel pokemonInfoModel) {
                                pokemonDetails.setValue(pokemonInfoModel);
                                loading.setValue(false);
                                loadError.setValue(false);
                            }

                            @Override
                            public void onError(Throwable e) {
                                loading.setValue(false);
                                loadError.setValue(true);
                                e.printStackTrace();

                            }
                        })
        );

    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }

}

