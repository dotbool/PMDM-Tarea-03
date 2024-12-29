package martinezruiz.javier.pmdmtarea03.repository;

import martinezruiz.javier.pmdmtarea03.models.PokemonList;
import retrofit2.Callback;

public interface PokedexListRepositoryInterface {


    void getPokemonList(int offset, int limit, Callback<PokemonList> callback);


}
