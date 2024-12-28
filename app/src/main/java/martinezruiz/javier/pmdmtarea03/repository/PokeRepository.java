package martinezruiz.javier.pmdmtarea03.repository;

import io.reactivex.rxjava3.core.Observable;
import martinezruiz.javier.pmdmtarea03.models.PokedexList;
import martinezruiz.javier.pmdmtarea03.models.Pokemon;
import martinezruiz.javier.pmdmtarea03.network.PokeApiClient;
import martinezruiz.javier.pmdmtarea03.network.PokeApiService;
import retrofit2.Callback;

public class PokeRepository implements PokedexListRepositoryInterface {


    public PokeRepository() {
    }
    PokeApiService pokeApiService = PokeApiClient.getClient().create(PokeApiService.class);


    @Override
    public void getPokedexList(int off, int limit, Callback<PokedexList> callback) {
        pokeApiService.getPokedexList(off, limit).enqueue(callback);
    }



    public Observable<Pokemon> getPokemon(String name){
        return pokeApiService.getPokemon(name);
    }


}
