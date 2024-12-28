package martinezruiz.javier.pmdmtarea03.services;



import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import martinezruiz.javier.pmdmtarea03.models.PokedexList;
import martinezruiz.javier.pmdmtarea03.models.Pokemon;
import martinezruiz.javier.pmdmtarea03.network.PokeApiClient;
import martinezruiz.javier.pmdmtarea03.network.PokeApiService;
import retrofit2.Call;

public class PokedexListService {

//    PokeRepository repo = new PokeRepository();
    PokeApiService pokeApiService = PokeApiClient.getClient().create(PokeApiService.class);

//    public Observable<PokedexList> getPokedexList(int offset, int limit) {
//    public Call<PokedexList> getPokedexList(int offset, int limit) {
//
//        return pokeApiService.getPokedexList(offset, limit);
//
//    }


    public Observable<Pokemon> getPokemon(String name){
        return pokeApiService.getPokemon(name);
    }
}







