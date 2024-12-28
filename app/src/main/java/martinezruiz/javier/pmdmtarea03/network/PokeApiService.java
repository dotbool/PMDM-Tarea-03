package martinezruiz.javier.pmdmtarea03.network;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import martinezruiz.javier.pmdmtarea03.models.PokedexList;
import martinezruiz.javier.pmdmtarea03.models.Pokemon;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


/**
 * Aloja las llamadas que se harán a la API a través del servicio que ofrece Retrofit
 * EL parámetro de Call hace referencia al tipo de dato que la llamada espera
 */
public interface PokeApiService {


    @GET("pokemon")
//    Observable<PokedexList> getPokedexList(@Query("offset") int offset, @Query("limit") int limit);
        Call<PokedexList> getPokedexList(@Query("offset") int offset, @Query("limit") int limit);

    @GET("pokemon/{name}")
    Observable<Pokemon> getPokemon(@Path("name") String name);
//    Call<Pokemon> getPokemons(@Path("name") String name);




}
