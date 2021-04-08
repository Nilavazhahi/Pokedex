package com.nila.pokedex.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nila.pokedex.R;
import com.nila.pokedex.model.PokemonModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PokemonListAdapter extends RecyclerView.Adapter<PokemonListAdapter.PokemonViewHolder>{

    private List<PokemonModel> pokemons;
    public PokemonListAdapter(List<PokemonModel> pokemons){
        this.pokemons = pokemons;
    }

    public void updatePokemons(List<PokemonModel> pokemonModels){
        pokemons.clear();
        pokemons.addAll(pokemonModels);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PokemonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pokemon, parent, false);
        return new PokemonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PokemonViewHolder holder, int position) {
        holder.bind(pokemons.get(position));

    }

    @Override
    public int getItemCount() {
        return pokemons.size();
    }

    public class PokemonViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.pokemon_name)
        TextView pokemonName;

        @BindView(R.id.pokemon_image)
        ImageView pokemonImage;


        public PokemonViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }

        public void bind(PokemonModel pokemonModel) {

            pokemonName.setText(pokemonModel.getPokemonName());
            Util.loadImage(pokemonImage, pokemonModel.getImageUrl(), Util.getProgressDrawable(pokemonImage.getContext()));

        }
    }
}
