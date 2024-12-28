package martinezruiz.javier.pmdmtarea03.repository;

import androidx.lifecycle.LiveData;

import java.util.List;

import martinezruiz.javier.pmdmtarea03.models.PokedexItem;
import martinezruiz.javier.pmdmtarea03.models.PokedexList;
import retrofit2.Callback;

public interface PokedexListRepositoryInterface {

    public void getPokedexList(int off, int limit, Callback<PokedexList> callback);

}
