package martinezruiz.javier.pmdmtarea03.ui.pokedex;


import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import java.util.List;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import martinezruiz.javier.pmdmtarea03.models.Pokemon;
import martinezruiz.javier.pmdmtarea03.models.PokemonList;
import martinezruiz.javier.pmdmtarea03.repository.PokeRepository;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Esta clase majena la lógica de la app alojando los datos que ésta usa y ofreciendo la oportunidad
 * de observarlos mediante el uso de LiveData. LiveData es una clase que destruye los objetos suscritos
 * cuando se destruye su alcance de forma que no se produzcan fugas de memoria porque existan
 * suscriptores sin utilidad
 */
public class PokedexViewModel extends ViewModel {

    @Override
    protected void onCleared() {
        super.onCleared();
        disposePokemons.dispose();
    }

    public PokedexViewModel() {
        pokemonList = new MutableLiveData<>();
        repo = new PokeRepository();

        if(!pokemonList.isInitialized()){
            repo.getPokemonList(0, 5, new Callback<PokemonList>() {
                @Override
                public void onResponse(Call<PokemonList> call, Response<PokemonList> response) {
                    if(response.isSuccessful()){
                        if(response.body()!=null) {
                            pokemonList.postValue(response.body().pokemonList());
                        }
                    }
                }

                @Override
                public void onFailure(Call<PokemonList> call, Throwable throwable) {
                    System.out.println(throwable.getMessage());
                }
            });
        }
    }


    public LiveData<List<Pokemon>> getPokemons(){
        return pokemonList;
    }
    private MutableLiveData<List<Pokemon>> pokemonList;
    private PokeRepository repo;

    /**
     * Cuando se pulsa el botón de capture:
     * 1. Se obtiene el valor LiveData de los potenciales. Este valor refleja las cards yellow que
     * han sido clickadas
     * 2. Se obtiene el valor LiveData de los que hayan sido capturados anteriormente
     * 3. Se llama al repo para llamar a la API t hacer un fecth de todos los potenciales. Cuando
     * viene uno, se elimina de la lista de potenciales y se añade a los capturados.
     * 4. Cuando se completa el flujo de datos se actualizan os LiveData, de forma que el observer,
     * esto es, el podedexFragment, es notificado y éste a su vez establecerá las listas
     * que maneja el adapter y notificará el cambio en las vistas
     *
     */
    protected void capture(){

        List<Pokemon> pokemons = getPokemons().getValue();
        if(pokemons!=null){

            for(Pokemon p: pokemons){
                if(p.getState().equals(Pokemon.State.WANTED)){
                    disposePokemons = repo.getPokemon(p.getNombre())
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    onNext -> {
                                        onNext.setState(Pokemon.State.CAPTURED);
                                        pokemons.set(pokemons.indexOf(p), onNext);
                                        System.out.println(onNext.getImgUrl().getFrontDefault());
                                        System.out.println(onNext.getTypes().get(0).type().name());
                                    },
                                    onError ->{
                                        System.out.println(onError);
                                    },
                                    ()->{
                                        pokemonList.postValue(pokemons);
                                    }
                            );

                }
            }

        }

    }

    Disposable disposePokemons;
    private String TAG = getClass().getName();
}
