package com.nila.pokedex.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.nila.pokedex.di.DaggerApiComponent;
import com.nila.pokedex.model.PokemonModel;
import com.nila.pokedex.model.PokemonResponse;
import com.nila.pokedex.model.PokemonService;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class PokemonViewModel extends ViewModel {
    public MutableLiveData<List<PokemonModel>> pokemonList = new MutableLiveData<List<PokemonModel>>();
    public MutableLiveData<Boolean> loadError = new MutableLiveData<Boolean>();
    public MutableLiveData<Boolean> loading = new MutableLiveData<Boolean>();

    @Inject
    public PokemonService pokemonService;

    private CompositeDisposable disposable = new CompositeDisposable();

    public PokemonViewModel(){
        super();
        DaggerApiComponent.create().inject(this);
    }


    public void refresh(){
        fetchPokemons();
    }

    private void fetchPokemons(){

        loading.setValue(true);
        disposable.add(
                pokemonService.getPokemonResponse()
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<PokemonResponse>() {
                            @Override
                            public void onSuccess(PokemonResponse pokemonResponse) {

                                pokemonList.setValue(pokemonResponse.getResults());
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
