package com.nila.pokedex.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.nila.pokedex.R;
import com.nila.pokedex.model.PokemonModel;
import com.nila.pokedex.viewmodel.PokemonViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.pokemon_recyclerview)
    RecyclerView pokemonsList;

    @BindView(R.id.list_error)
    TextView listError;

    @BindView(R.id.loadind_view)
    ProgressBar loadingView;

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    private PokemonViewModel pokemonViewModel;
    private PokemonListAdapter pokemonListAdapter = new PokemonListAdapter(new ArrayList<>());


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        pokemonViewModel = ViewModelProviders.of(this).get(PokemonViewModel.class);
        pokemonViewModel.refresh();

        pokemonsList.setLayoutManager(new GridLayoutManager(this,2));
        pokemonsList.setAdapter(pokemonListAdapter);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pokemonViewModel.refresh();
                swipeRefreshLayout.setRefreshing(false) ;
            }
        });


        if(Connectivity.isConnected(MainActivity.this)) {

            observerViewModel();
        }else{
            Toast.makeText(this,"Check Your Internet Connection", Toast.LENGTH_LONG).show();
        }
    }

    private void observerViewModel() {
        pokemonViewModel.pokemonList.observe(this, new Observer<List<PokemonModel>>() {
            @Override
            public void onChanged(List<PokemonModel> pokemonModels) {
                if(pokemonModels!=null){
                    pokemonsList.setVisibility(View.VISIBLE);
                    pokemonListAdapter.updatePokemons(pokemonModels);
                }
            }
        });

        pokemonViewModel.loadError.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isError) {
                if(isError !=null){
                    listError.setVisibility(isError ? View.VISIBLE : View.GONE);
                }
            }
        });

        pokemonViewModel.loading.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isLoading) {
                if (isLoading != null){
                    loadingView.setVisibility( isLoading ? View.VISIBLE : View.GONE);
                }

                if(isLoading){
                    pokemonsList.setVisibility(View.GONE);
                    listError.setVisibility(View.GONE);
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                pokemonListAdapter.getFilter().filter(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                pokemonListAdapter.getFilter().filter(newText); //sending search text to adapter's filter method
                return false;
            }
        });
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        pokemonListAdapter.notifyDataSetChanged();
    }
}