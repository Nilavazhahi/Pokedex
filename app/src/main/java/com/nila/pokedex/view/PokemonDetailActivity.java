package com.nila.pokedex.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.nila.pokedex.R;
import com.nila.pokedex.model.PokemonInfoModel;
import com.nila.pokedex.model.PokemonModel;
import com.nila.pokedex.model.PokemonResponse;
import com.nila.pokedex.model.Stats;
import com.nila.pokedex.model.Type;
import com.nila.pokedex.model.TypeResponse;
import com.nila.pokedex.viewmodel.PokemonDetailViewModel;
import com.nila.pokedex.viewmodel.PokemonDetailViewModelFactory;
import com.nila.pokedex.viewmodel.PokemonViewModel;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.Provides;

public class PokemonDetailActivity extends AppCompatActivity {

    private PokemonDetailViewModel pokemonDetailViewModel;

    @BindView(R.id.index)
    AppCompatTextView indexValue;

    @BindView(R.id.name)
    AppCompatTextView pokeName;

    @BindView(R.id.image)
    AppCompatImageView pokeImage;

    @BindView(R.id.pokemon_type1)
    AppCompatTextView pokemonType1;

    @BindView(R.id.pokemon_type2)
    AppCompatTextView pokemonType2;

    @BindView(R.id.weight)
    AppCompatTextView weightText;

    @BindView(R.id.height)
    AppCompatTextView heightText;

    @BindView(R.id.progressbar)
    ProgressBar progressBar;

    @BindView(R.id.bar_hp)
    ProgressBar hpProgressBar;

    @BindView(R.id.bar_attack)
    ProgressBar attackProgressBar;

    @BindView(R.id.bar_defense)
    ProgressBar defenseProgressBar;

    @BindView(R.id.bar_speed)
    ProgressBar speedProgressBar;

    @BindView(R.id.bas_exp)
    ProgressBar basExpProgressBar;

    @BindView(R.id.toolbar)
    public Toolbar toolbar;

    @BindView(R.id.error_text)
    TextView errorText;

    @BindView(R.id.hp_value)
    TextView hpValue;

    @BindView(R.id.attack_value)
    TextView attackValue;

    @BindView(R.id.speed_value)
    TextView speedValue;

    @BindView(R.id.defense_value)
    TextView defenseValue;

    @BindView(R.id.exp_value)
    TextView expValue;


    PokemonModel pokemonBundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon_detail);
        ButterKnife.bind(this);

        if (getSupportActionBar() == null) {
            setSupportActionBar(toolbar);
        } else toolbar.setVisibility(View.GONE);
        getSupportActionBar().setTitle("Pokedex");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(0);

        Intent i = getIntent();
        Bundle bundle = i.getExtras();
        if (bundle != null) {
            pokemonBundle = (PokemonModel) bundle.getSerializable("pokemon");
            //passing pokename to view model
            pokemonDetailViewModel = ViewModelProviders.of(this, new PokemonDetailViewModelFactory(this.getApplication(), pokemonBundle.getPokemonName())).get(PokemonDetailViewModel.class);
            pokemonDetailViewModel.refresh();
        }

        //Checking internet connection
        if (Connectivity.isConnected(PokemonDetailActivity.this)) {
            //calling observer models
            observerViewModel();
        } else {
            Toast.makeText(this, "Check Your Internet Connection", Toast.LENGTH_LONG).show();
        }

    }


    private void observerViewModel() {
        pokemonDetailViewModel.pokemonDetails.observe(this, new Observer<PokemonInfoModel>() {
            @Override
            public void onChanged(PokemonInfoModel pokemonInfoModel) {
                indexValue.setText(pokemonInfoModel.getIdString());
                pokeName.setText(pokemonInfoModel.getName());
                weightText.setText(pokemonInfoModel.getWeightString());
                heightText.setText(pokemonInfoModel.getHeightString());
                basExpProgressBar.setProgress(pokemonInfoModel.getBase_experience());
                expValue.setText(String.valueOf(pokemonInfoModel.getBase_experience()));
                Util.loadImage(pokeImage, pokemonBundle.getImageUrl(), Util.getProgressDrawable(pokeImage.getContext()));

                List<Stats> stats = pokemonInfoModel.getStats();

                if (stats.size() > 4) {
                    hpProgressBar.setProgress(stats.get(0).getBase_stat());
                    hpValue.setText(String.valueOf(stats.get(0).getBase_stat()));
                    attackProgressBar.setProgress(stats.get(1).getBase_stat());
                    attackValue.setText(String.valueOf(stats.get(1).getBase_stat()));
                    defenseProgressBar.setProgress(stats.get(2).getBase_stat());
                    defenseValue.setText(String.valueOf(stats.get(2).getBase_stat()));
                    speedProgressBar.setProgress(stats.get(5).getBase_stat());
                    speedValue.setText(String.valueOf(stats.get(5).getBase_stat()));

                }

                if (pokemonInfoModel.getTypes().size() > 0) {
                    List<TypeResponse> typeResponses = pokemonInfoModel.getTypes();
                    if (pokemonInfoModel.getTypes().size() > 1) {
                        pokemonType1.setVisibility(View.VISIBLE);
                        pokemonType2.setVisibility(View.VISIBLE);

                        pokemonType1.setText(typeResponses.get(0).getType().getName());
                        pokemonType2.setText(typeResponses.get(1).getType().getName());
                    } else {
                        pokemonType1.setVisibility(View.VISIBLE);

                        pokemonType1.setText(typeResponses.get(0).getType().getName());
                        pokemonType2.setVisibility(View.GONE);
                    }
                } else {
                    pokemonType1.setVisibility(View.GONE);
                    pokemonType2.setVisibility(View.GONE);

                }

            }
        });

        pokemonDetailViewModel.loadError.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isError) {
                if (isError != null) {
                    errorText.setVisibility(isError ? View.VISIBLE : View.GONE);
                }
            }
        });

        pokemonDetailViewModel.loading.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isLoading) {
                if (isLoading != null) {
                    progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
                }

                if (isLoading) {
                    errorText.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(PokemonDetailActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


}