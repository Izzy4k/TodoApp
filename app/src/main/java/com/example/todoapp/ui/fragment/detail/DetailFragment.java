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

import com.example.todoapp.R;
import com.example.todoapp.databinding.FragmentDetailBinding;

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
    }

    private void initListener() {
        binding.btnSave.setOnClickListener(v -> {
            String result = binding.etTxt.getText().toString().trim();
            Bundle bundle = new Bundle();
            bundle.putString("key", result);
            getParentFragmentManager().setFragmentResult("top", bundle);
            closeFragment();
        });
    }

    private void closeFragment() {
        controller = Navigation.findNavController(requireActivity(),
                R.id.nav_host_fragment_activity_main);
        controller.navigateUp();
    }
}