package com.example.todoapp.ui.fragment.board.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoapp.R;
import com.example.todoapp.databinding.ItemBoardBinding;

public class PagerAdapterBob extends RecyclerView.Adapter<PagerAdapterBob.PagerViewHolder> {
    ItemBoardBinding binding;
    private final int[] images = {R.drawable.ic_board_first, R.drawable.ic_board_second,
            R.drawable.ic_board_third};
    private final String[] titles = {"Что на умном", "Cделай вид что читаешь",
            "Долгожданый конец "};
    private final String[] descriptions = {"аывфафалывфафлоафылоафдваоофлыаф", "вфаолафыоафылоафло",
            "урааааааааааа конец"};

    @NonNull
    @Override
    public PagerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ItemBoardBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent,
                false);
        return new PagerViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PagerViewHolder holder, int position) {
        holder.onBind(images[position], titles[position], descriptions[position]);
    }

    @Override
    public int getItemCount() {
        return images.length;
    }

    public class PagerViewHolder extends RecyclerView.ViewHolder {

        public PagerViewHolder(@NonNull ItemBoardBinding binding) {
            super(binding.getRoot());

        }

        public void onBind(int image, String title, String description) {
            binding.imageBoard.setImageResource(image);
            binding.txtTitleBoard.setText(title);
            binding.txtDescriptionBoard.setText(description);
        }
    }
}
