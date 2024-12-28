package martinezruiz.javier.pmdmtarea03.ui.pokedex;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.util.ArrayList;

import io.reactivex.rxjava3.disposables.Disposable;
import martinezruiz.javier.pmdmtarea03.R;
import martinezruiz.javier.pmdmtarea03.databinding.FragmentPokedexBinding;
import martinezruiz.javier.pmdmtarea03.models.PokedexItem;

public class PokedexFragment extends Fragment implements ClickListener{


    public PokedexFragment() {
        // Required empty public constructor
        pokedexItems = new ArrayList<>();
        pokedexCapturados = new ArrayList<>();
        pokedexPotenciales = new ArrayList<>();
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pokedexViewModel = new ViewModelProvider(requireActivity()).get(PokedexViewModel.class);
    }

    @Override
    public void onStart() {
        super.onStart();

        pokedexViewModel.getPokemonsPotenciales().observe(getViewLifecycleOwner(), pokedexPotenciales ->{

            this.pokedexPotenciales = (ArrayList<PokedexItem>) pokedexPotenciales;
            adapter.setPokemonsPotenciales((ArrayList<PokedexItem>) pokedexPotenciales);
        });

        pokedexViewModel.getPokemonsCapturados().observe(getViewLifecycleOwner(), pokedexCapturados ->{

            this.pokedexCapturados = (ArrayList<PokedexItem>) pokedexCapturados;
            adapter.setPokemonsCapturados((ArrayList<PokedexItem>) pokedexCapturados);
            Log.d(TAG, "getPokemonsCapturados: "+ pokedexCapturados.size());
            adapter.notifyDataSetChanged();
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentPokedexBinding.inflate(inflater);
        RecyclerView recycler = (RecyclerView) binding.rvPokedex;
        LinearLayoutManager layout = new LinearLayoutManager(getContext());
        recycler.setLayoutManager(layout);

        adapter = new PokedexAdapter(this.pokedexItems, this.pokedexPotenciales, this.pokedexCapturados, this);
        recycler.setAdapter(adapter);



        setCaptureBtnUI(); //Adaptamos al btn de captura para que se ajuste a la pantalla

        pokedexViewModel.getPokedexItemList().observe(getViewLifecycleOwner(), pokedexItemsIncomming -> {

            if (adapter.getItemCount() == 0) {
                this.pokedexItems.addAll(pokedexItemsIncomming);
            }
            else {
                this.pokedexItems = (ArrayList<PokedexItem>) pokedexItemsIncomming;
            }
            adapter.notifyItemRangeInserted(0, this.pokedexItems.size());
        });



        Button btn = binding.btnCapture;
//
        btn.setOnClickListener(view -> {
            Log.d(TAG, "btcapturado pressionado");
            pokedexViewModel.capture();

        });

        return binding.getRoot();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, ": destruido");
    }

    private void setCaptureBtnUI(){

        BottomNavigationView navigation =
                requireActivity().findViewById(R.id.nav_view_bottom);
        navigation.post(new Runnable() {
            @Override
            public void run() {
                int height = (int) navigation.getMeasuredHeight();
                View v = binding.btnCapture;
                ViewGroup.MarginLayoutParams btnBottomMargin = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
                btnBottomMargin.bottomMargin=height;
                v.setLayoutParams(btnBottomMargin);

            }
        });
    }

    private PokedexViewModel pokedexViewModel;
    private ArrayList<PokedexItem> pokedexItems;
    private ArrayList<PokedexItem> pokedexCapturados;
    private ArrayList<PokedexItem> pokedexPotenciales;

    private FragmentPokedexBinding binding;
    private String TAG = getClass().getName();




    PokedexAdapter adapter;

    @Override
    public void onClick(PokedexItem item) {
        pokedexPotenciales.forEach(i-> System.out.println(i.getName()));
        pokedexViewModel.setPokemonPotenciales(pokedexPotenciales);
    }
}