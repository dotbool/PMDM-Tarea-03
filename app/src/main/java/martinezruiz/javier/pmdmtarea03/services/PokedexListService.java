package martinezruiz.javier.pmdmtarea03.services;

import androidx.lifecycle.LiveData;

import java.util.List;

import martinezruiz.javier.pmdmtarea03.models.PokedexItem;
import martinezruiz.javier.pmdmtarea03.repository.PokeRepository;

public class PokedexListService {

    PokeRepository repo = new PokeRepository();

    public LiveData<List<PokedexItem>> getPokedexList(int offset, int limit){

        LiveData<List<PokedexItem>> list = repo.getPokedexList(offset, limit);
        return list;
    }
}
