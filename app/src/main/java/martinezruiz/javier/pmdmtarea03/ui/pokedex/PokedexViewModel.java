package martinezruiz.javier.pmdmtarea03.ui.pokedex;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import martinezruiz.javier.pmdmtarea03.MainActivity;
import martinezruiz.javier.pmdmtarea03.models.PokedexItem;
import martinezruiz.javier.pmdmtarea03.repository.PokeRepository;
import martinezruiz.javier.pmdmtarea03.services.PokedexListService;


/**
 * Esta clase majena la lógica de la app alojando los datos que ésta usa y ofreciendo la oportunidad
 * de observarlos mediante el uso de LiveData. LiveData es una clase que destruye los objetos suscritos
 * cuando se destruye su alcance de forma que no se produzcan fugas de memoria porque existan
 * suscriptores sin utilidad
 */
public class PokedexViewModel extends ViewModel {



    public PokedexViewModel() {

        this.allowDelete = new MutableLiveData<>();
        pokedexItemList = new MutableLiveData<>();
//        this.service = new PokedexListService();



                PokeRepository repo = new PokeRepository();
                repo.getPokedexList(0,5).observeForever(pokedexItems -> pokedexItemList.setValue(pokedexItems));


    }



    public LiveData<List<PokedexItem>> getPokedexItemList(){
        System.out.println(pokedexItemList+ "en el get");
        return pokedexItemList;
    }
    public LiveData<Boolean> getAllowDelete() {
        return allowDelete;
    }

    public void setPokedexItemList(List<PokedexItem> list){
        pokedexItemList.setValue(list);
    }
    public void setAllowDelete(boolean value){
        allowDelete.setValue(value);
    }

    private final MutableLiveData<Boolean> allowDelete;
    private MutableLiveData<List<PokedexItem>> pokedexItemList;
    private PokeRepository repo;
    private PokedexListService service;
}
