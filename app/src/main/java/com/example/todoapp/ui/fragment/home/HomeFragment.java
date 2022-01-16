package com.example.todoapp.ui.fragment.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.todoapp.R;
import com.example.todoapp.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initListener();
        initFragmentResultListener();
    }

    private void initFragmentResultListener() {
        getParentFragmentManager().setFragmentResultListener("top",
                getViewLifecycleOwner(), (requestKey, result) -> {
                    if (requestKey.equals("top")) {
                        Toast.makeText(requireContext(), result.getString("key"),
                                Toast.LENGTH_LONG)
                                .show();
                    }

                });
    }

    private void initListener() {
        binding.btnAdd.setOnClickListener(v -> {
            openFragment();
        });
    }

    private void openFragment() {
        NavController controller = Navigation.findNavController(requireActivity(),
                R.id.nav_host_fragment_activity_main);
        controller.navigate(R.id.detailFragment);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}