package com.nila.pokedex.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nila.pokedex.R;
import com.nila.pokedex.model.PokemonModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PokemonListAdapter extends RecyclerView.Adapter<PokemonListAdapter.PokemonViewHolder> implements Filterable {

    private List<PokemonModel> pokemons;
    private List<PokemonModel> pokemonsFilteredList;
    public MyFilter mFilter;

    public PokemonListAdapter(List<PokemonModel> pokemons){

        this.pokemons = pokemons;
        pokemonsFilteredList = new ArrayList<>(pokemons);
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

    @Override
    public Filter getFilter() {

        if (mFilter == null){
            pokemonsFilteredList.clear();
            pokemonsFilteredList.addAll(this.pokemons);
            mFilter = new PokemonListAdapter.MyFilter(this,pokemonsFilteredList);
        }
        return mFilter;
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

    //get filtered result based on search string
    private static class MyFilter extends Filter {

        private final PokemonListAdapter pokemonListAdapter;
        private final List<PokemonModel> originalList;
        private final List<PokemonModel> filteredList;

        private MyFilter(PokemonListAdapter pokemonListAdapter, List<PokemonModel> originalList) {
            this.pokemonListAdapter = pokemonListAdapter;
            this.originalList = originalList;
            this.filteredList = new ArrayList<PokemonModel>();
        }

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {

            filteredList.clear();
            final FilterResults results = new FilterResults();
            if (charSequence.length() == 0){
                filteredList.addAll(originalList);
            }else {
                final String filterPattern = charSequence.toString().toLowerCase().trim();
                for ( PokemonModel pokemonModel : originalList){
                    if (pokemonModel.getPokemonName().toLowerCase().contains(filterPattern)){
                        filteredList.add(pokemonModel);
                    }
                }
            }

            results.values = filteredList;
            results.count = filteredList.size();
            return results;

        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

            pokemonListAdapter.pokemons.clear();
            pokemonListAdapter.pokemons.addAll((ArrayList<PokemonModel>)filterResults.values);
            pokemonListAdapter.notifyDataSetChanged();

        }
    }

}
