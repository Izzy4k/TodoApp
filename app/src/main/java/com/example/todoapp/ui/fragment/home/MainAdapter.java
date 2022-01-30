package com.example.todoapp.ui.fragment.home;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoapp.databinding.ItemMainRvBinding;
import com.example.todoapp.models.Task;

import java.util.ArrayList;
import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {
    private List<Task> list = new ArrayList<>();
    ItemMainRvBinding binding;
    Click click ;
    public MainAdapter(Click click) {
        this.click = click;
    }
    public void setList(List<Task> list) {
        this.list = list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ItemMainRvBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.onBind(list.get(position));
        holder.binding.txtTitle.setOnClickListener(v -> {
        click.click(list.get(holder.getAdapterPosition()));
        });
     holder.binding.txtTitle.setOnLongClickListener(v -> {
         click.delete(list.get(holder.getAdapterPosition()));
         return true;
     });
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemMainRvBinding binding;
        public ViewHolder(@NonNull ItemMainRvBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        public void onBind(Task task) {
            binding.txtTitle.setText(task.getTitle());
        }
    }
}
