package martinezruiz.javier.pmdmtarea03.network;

import androidx.lifecycle.LiveData;

import java.util.List;

import martinezruiz.javier.pmdmtarea03.models.PokedexItem;
import martinezruiz.javier.pmdmtarea03.models.PokedexList;
import martinezruiz.javier.pmdmtarea03.models.Pokemon;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


/**
 * Aloja las llamadas que se harán a la API a través del servicio que ofrece Retrofit
 */
public interface PokeApiService {

    @GET("pokemon")
    Call <PokedexList> getPokedexList(@Query("offset") int offset, @Query("limit") int limit);

    @GET("pokemon/{id}")
    Call<List<Pokemon>> getPokemonsList(@Path("id") int id);
}
