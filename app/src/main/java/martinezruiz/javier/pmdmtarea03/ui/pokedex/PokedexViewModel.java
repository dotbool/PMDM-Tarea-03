package martinezruiz.javier.pmdmtarea03.ui.pokedex;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import java.util.ArrayList;
import java.util.List;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import martinezruiz.javier.pmdmtarea03.models.PokedexItem;
import martinezruiz.javier.pmdmtarea03.models.PokedexList;
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
        pokemon.dispose();
    }

    public PokedexViewModel() {

        pokedexItemList = new MutableLiveData<>();
        pokemonsCapturados = new MutableLiveData<>();
        pokemonsPotenciales = new MutableLiveData<>();
        repo = new PokeRepository();
//        PokedexListService service = new PokedexListService();




//
//        pokemon = service.getPokemon("pikachu")
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(
//
//                    next->{
//                        System.out.println(next.getImgUrl().getFrontDefault());
//                        System.out.println(next.getTypes().get(0).type().name());
//
//                    },
//                    error -> {
//                        System.out.println(error);
//                    }
//
//
//        );


        if(!pokedexItemList.isInitialized()) {

            repo.getPokedexList(0, 5, new Callback<>() {
                @Override
                public void onResponse(@NonNull Call<PokedexList> call, @NonNull Response<PokedexList> response) {
                    if (response.isSuccessful()) {

                        PokedexList body = response.body();
                        if (body != null) {
                            List<PokedexItem> list = body.results();
                            pokedexItemList.postValue(list);

                        }
                    }
                }

                @Override
                public void onFailure(Call<PokedexList> call, Throwable throwable) {

                }
            });
        }



    }



    public LiveData<List<PokedexItem>> getPokedexItemList(){
        return pokedexItemList;
    }

    public LiveData<List<PokedexItem>> getPokemonsCapturados(){
        return pokemonsCapturados;
    }
    public LiveData<List<PokedexItem>> getPokemonsPotenciales(){
        return pokemonsPotenciales;
    }

    public void setPokemonCapturados(List<PokedexItem> capturados){
        pokemonsCapturados.setValue(capturados);
    }
    public void setPokemonPotenciales(List<PokedexItem> potenciales){
        pokemonsPotenciales.setValue(potenciales);
    }


//    public void setPokedexItemList(List<PokedexItem> list){
//        pokedexItemList.setValue(list);
//    }




    private MutableLiveData<List<PokedexItem>> pokedexItemList;
    private MutableLiveData<List<PokedexItem>> pokemonsCapturados;
    private MutableLiveData<List<PokedexItem>> pokemonsPotenciales;
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

        List<PokedexItem> potenciales = getPokemonsPotenciales().getValue(); //obtenemos el current value para trabajar localmente
        List<PokedexItem> capturados;
        if(getPokemonsCapturados().getValue()!=null){
            capturados = getPokemonsCapturados().getValue(); //obtenemos el current value para trabajar localmente
        }
        else {
            capturados = new ArrayList<>();
        }
        Log.d(TAG, ": "+ capturados);

            if(potenciales!=null && !potenciales.isEmpty()){
                for(PokedexItem item: potenciales){  //por cada item en potenciales llamamos a la API
                    pokemon = repo.getPokemon(item.getName())
                            .subscribeOn(Schedulers.io()) //los request en otro hilo
                            .observeOn(AndroidSchedulers.mainThread()) //la función ejecutada cuando los datos vienen, en le hilo ppal
                            .subscribe(
                                    onNext -> {
                                        potenciales.remove(item); //eliminamos potencial de la lista local
                                        Log.d(TAG, ": "+ item);
                                        Log.d(TAG, ": "+ capturados);
                                        capturados.add(item); //añadimos capturado a la lista local
                                    },
                                    onError -> {
                                        System.out.println(onError);
                                    },
                                    () -> {
                                        //cuando se han hecho todos los fetch actualizamos los LiveData
                                        //Éstos notifican al Fragment que aloja la vista, los cambios
                                        pokemonsPotenciales.postValue(potenciales);
                                        pokemonsCapturados.postValue(capturados);
                                    }

                            );
                }
        };
    }

    Disposable pokemon;
    private String TAG = getClass().getName();
}
