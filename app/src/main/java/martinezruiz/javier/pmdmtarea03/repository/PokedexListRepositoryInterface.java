package martinezruiz.javier.pmdmtarea03.repository;

import androidx.lifecycle.LiveData;

import java.util.List;

import martinezruiz.javier.pmdmtarea03.models.PokedexItem;
import martinezruiz.javier.pmdmtarea03.models.PokedexList;

public interface PokedexListRepositoryInterface {

    public LiveData<List<PokedexItem>> getPokedexList(int off, int limit);
}
