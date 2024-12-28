package martinezruiz.javier.pmdmtarea03.ui.pokedex;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import martinezruiz.javier.pmdmtarea03.R;
import martinezruiz.javier.pmdmtarea03.databinding.PokedexItemHolderBinding;
import martinezruiz.javier.pmdmtarea03.models.PokedexItem;
import martinezruiz.javier.pmdmtarea03.models.Pokemon;

public class PokedexAdapter extends RecyclerView.Adapter<PokedexAdapter.PokeViewHolder> implements ClickListener {

    public PokedexAdapter(ArrayList<PokedexItem> items,
                          ArrayList<PokedexItem> pokemonsPotenciales,
                          ArrayList<PokedexItem> pokemonsCapturados,
                          ClickListener onClickListener
                          ) {
        this.items = items;
        this.pokemonsPotenciales = pokemonsPotenciales;
        this.pokemonsCapturados= pokemonsCapturados;
        this.onClickListener = onClickListener;




    }

    @Override
    public void onClick(PokedexItem item) {

        if(isInList(pokemonsPotenciales, item)){
            pokemonsPotenciales.remove(item);
        }
        else{
            pokemonsPotenciales.add(item);
        }
        onClickListener.onClick(item);

    }

      class PokeViewHolder extends RecyclerView.ViewHolder  {

        PokedexItemHolderBinding binding;

        public PokeViewHolder(PokedexItemHolderBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }

          /**
           *
           * @param item
           * @param listener
           * @param position
           */
        public void bind(PokedexItem item, ClickListener listener, int position){
            binding.listItemText.setText(item.getName());
            boolean isCaptured = isInList(pokemonsCapturados, item);
//            if(isCaptured){
//                Pokemon pokemon =
//                Glide.with(binding.getRoot()).load(item.getUrl()).into(binding.imgPokedex);
//            }
//            else{
//                binding.imgPokedex.setImageResource(R.drawable.wanted);
//            }
            View view = binding.getRoot();
            //si está en la lista de ptenciales se colorea de yellow
            view.setSelected(isInList(pokemonsPotenciales, item));
            //si está en la lista de capturados se torna no enabled
            view.setEnabled(!isInList(pokemonsCapturados, item));

            //añadimos a la vista un listener que al ser clickada: si selected => yellow, si no, white
            //ejecutamos el listener del Adapter onClick para meter o sacar una vista clikada de una lista de potenciales
            //ejecutamos el listener del fragment para establecer el valor del mutableLIveData, ya que es
            //el valor que va a persistir en los cambios de configuración
            binding.getRoot().setOnClickListener(e-> {
                view.setSelected(!view.isSelected());
                listener.onClick(item);


            });
        }

    }




    @NonNull
    @Override
    public PokedexAdapter.PokeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        binding = PokedexItemHolderBinding.inflate(LayoutInflater.from(parent.getContext())
                , parent, false);
        return new PokeViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PokedexAdapter.PokeViewHolder holder, int position) {

        PokedexItem currentItem = this.items.get(position);
        holder.bind(currentItem, this, position);



    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public ArrayList<PokedexItem> getItems() {
        return items;
    }

    /**
     *
     * @param list
     * @param item
     * @return
     */
    private boolean isInList(ArrayList<PokedexItem> list, PokedexItem item){

        return list.stream().anyMatch(i-> i.equals(item));
    }

    public void setPokemonsPotenciales(ArrayList<PokedexItem> pokemonsPotenciales) {
        this.pokemonsPotenciales = pokemonsPotenciales;
    }

    public void setPokemonsCapturados(ArrayList<PokedexItem> pokemonsCapturados) {
        this.pokemonsCapturados = pokemonsCapturados;
    }

    private ArrayList<PokedexItem> items;
    private ArrayList<PokedexItem> pokemonsCapturados;
    private ArrayList<PokedexItem> pokemonsPotenciales;
    private
    PokedexItemHolderBinding binding;
    ClickListener onClickListener;




}

