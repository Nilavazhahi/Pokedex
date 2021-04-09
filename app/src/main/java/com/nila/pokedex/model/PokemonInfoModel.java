package com.nila.pokedex.model;

import java.util.List;
import java.util.Random;

public class PokemonInfoModel {
    private int id;
    private String name;
    private int height;
    private int weight;
    private int base_experience;
    private List<TypeResponse> types;

    public List<Stats> getStats() {
        return stats;
    }

    private List<Stats> stats;


    public String getIdString() {
        return String.format("#%03d", id);
    }

    public String getWeightString() {
        return String.format("%.1f KG", ((float) weight) / 10);
    }

    public String getHeightString() {
        return String.format("%.1f M", ((float) height) / 10);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public int getBase_experience() {
        return base_experience;
    }


    public List<TypeResponse> getTypes() {
        return types;
    }


}
