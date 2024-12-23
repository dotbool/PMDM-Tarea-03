package martinezruiz.javier.pmdmtarea03.ui.pokedex;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import martinezruiz.javier.pmdmtarea03.R;
import martinezruiz.javier.pmdmtarea03.databinding.FragmentPokedexBinding;
import martinezruiz.javier.pmdmtarea03.databinding.FragmentSettingsBinding;
import martinezruiz.javier.pmdmtarea03.ui.settings.SettingsViewModel;

public class PokedexFragment extends Fragment {


    public PokedexFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pokedexViewModel= new ViewModelProvider(requireActivity()).get(PokedexViewModel.class);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentPokedexBinding.inflate(inflater, container, false);
        pokedexViewModel.getPokedexItemList().observe(getViewLifecycleOwner(), pokedexItems -> {
                    pokedexItems.forEach(i-> System.out.println(i.getName()));
            System.out.println("En el fragment");

                }
        );

        //añadimos un listener a la pokedelist para que cuando cambie




        return binding.getRoot();
    }

    private FragmentPokedexBinding binding;
    private PokedexViewModel pokedexViewModel;
}