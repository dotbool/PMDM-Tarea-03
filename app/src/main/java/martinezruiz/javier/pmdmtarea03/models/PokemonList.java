package martinezruiz.javier.pmdmtarea03.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public record PokemonList(@SerializedName("results")ArrayList<Pokemon> pokemonList){ }
