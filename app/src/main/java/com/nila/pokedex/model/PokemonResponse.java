package com.nila.pokedex.model;

import java.util.List;

public class PokemonResponse {

    private String count;
    private String next;
    private String previous;
    private List<PokemonModel> results;




    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }

    public List<PokemonModel> getResults() {
        return results;
    }


}
