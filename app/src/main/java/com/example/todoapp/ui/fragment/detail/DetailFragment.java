package com.example.todoapp.ui.fragment.detail;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.todoapp.App;
import com.example.todoapp.R;
import com.example.todoapp.databinding.FragmentDetailBinding;
import com.example.todoapp.models.Task;

public class DetailFragment extends Fragment {
    private FragmentDetailBinding binding;
    private NavController controller;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDetailBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initListener();
        initArgumentsListener();
    }

    private void initArgumentsListener() {
        if (getArguments() != null) {
            Task task = (Task) getArguments().getSerializable("model");
            binding.etTxt.setText(task.getTitle());
            binding.btnSave.setText("Edit");
            binding.btnSave.setOnClickListener(v -> {
                String result = binding.etTxt.getText().toString().trim();
                if(!result.equals("")){
                    task.setTitle(result);
                    App.dataBase.taskDao().update(task);
                    closeFragment();
                }else {
                    Toast.makeText(requireContext(),"Пусто бля",Toast.LENGTH_LONG).show();
                }
            });
        }
    }
    private void initListener() {
        binding.btnSave.setText("Save");
        binding.btnSave.setOnClickListener(v -> {
            String result = binding.etTxt.getText().toString().trim();
            if(!result.equals("")){
                closeFragment();
                saveTask(result);
            } else {
                Toast.makeText(requireContext(),"Пусто бля",Toast.LENGTH_LONG).show();
            }
        });
    }

    private void saveTask(String result) {
        Task task = new Task(result);
        App.dataBase.taskDao().addTask(task);
    }

    private void closeFragment() {
        controller = Navigation.findNavController(requireActivity(),
                R.id.nav_host_fragment_activity_main);
        controller.navigateUp();
    }
}