package martinezruiz.javier.pmdmtarea03.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Hace peticiones a la API  y además convierte el JSON que obtiene en la respuesta en objetos
 * POJO para usarlos en la app
 */
public class PokeApiClient {

    private static String BASE_URL = "https://pokeapi.co/api/v2/";
    private static Retrofit retrofic;
    public static Retrofit getClient() {
        if(retrofic == null){
//
//            Gson gson = new GsonBuilder()
//                    .setLenient()
//                    .create();
            retrofic = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofic;
    }

}
