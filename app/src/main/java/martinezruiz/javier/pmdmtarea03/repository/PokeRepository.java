package martinezruiz.javier.pmdmtarea03.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import martinezruiz.javier.pmdmtarea03.models.PokedexItem;
import martinezruiz.javier.pmdmtarea03.models.PokedexList;
import martinezruiz.javier.pmdmtarea03.models.Pokemon;
import martinezruiz.javier.pmdmtarea03.network.PokeApiClient;
import martinezruiz.javier.pmdmtarea03.network.PokeApiService;
import martinezruiz.javier.pmdmtarea03.ui.pokedex.PokedexViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PokeRepository implements PokedexListRepositoryInterface {


    public PokeRepository() {
    }
    PokeApiService pokeApiService = PokeApiClient.getClient().create(PokeApiService.class);

    MutableLiveData<List<PokedexItem>> pokedexItemList =  new MutableLiveData<>();
    @Override
    public LiveData<List<PokedexItem>> getPokedexList(int off, int limit) {

        pokeApiService.getPokedexList(off, limit).enqueue(new Callback<PokedexList>() {
            @Override
            public void onResponse(@NonNull Call<PokedexList> call, @NonNull Response<PokedexList> response) {

                if(response.isSuccessful()){

                    PokedexList body = response.body();
                    if(body!=null){
                        List<PokedexItem> list = body.results();
                        pokedexItemList.setValue(list);
                    }
                    //el dato vive aquí
//                    pvm.setPokedexItemList(pokedexItemList); // esto es lo que no quiero hacer
                    //pvm es la instancia del viewModel para establecer el valor de una lista
                    System.out.println("succes crquest");
                }
            }

            @Override
            public void onFailure(Call<PokedexList> call, Throwable throwable) {

            }
        });

        System.out.println(pokedexItemList+ "al final");
        return pokedexItemList;
    }



}
