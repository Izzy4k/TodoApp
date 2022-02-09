package com.example.todoapp.ui.fragment.board;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.todoapp.R;
import com.example.todoapp.databinding.FragmentBoardBinding;
import com.example.todoapp.ui.fragment.board.adapter.PagerAdapterBob;
import com.example.todoapp.utils.Prefs;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.auth.FirebaseAuth;


public class BoardFragment extends Fragment {
    private FragmentBoardBinding binding;
    private PagerAdapterBob adapterBob;
    private NavController controller;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentBoardBinding.inflate(inflater);
        initDefault();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        controller = Navigation.findNavController(view);
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                requireActivity().finish();
            }
        });
        initPager();
        initListener();
        initBtn();
        initAnimate();
    }

    private void initDefault() {
        binding.txtSkip.setTranslationX(-100);
        binding.txtFinish.setEnabled(false);
    }

    private void initAnimate() {
        binding.txtSkip.animate().translationX(0).setDuration(2000).start();
    }


    private void initBtn() {
        binding.txtFinish.setOnClickListener(v -> {
            Prefs.getPrefs().saveState();
            navigateFragment();
        });
        binding.txtSkip.setOnClickListener(v -> {
            binding.viewPagerBoard.setCurrentItem(2);
        });
    }


    private void navigateFragment() {
        controller.navigate(R.id.action_boardFragment_to_authFragment3);

    }

    private void initListener() {
        new TabLayoutMediator(binding.tabBoard, binding.viewPagerBoard, (tab, position) -> {
            if (position == 0) {
                tab.setIcon(R.drawable.ic_primitivedot_106373);
            } else {
                tab.setIcon(R.drawable.ic_click);
            }
        }).attach();
        binding.tabBoard.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tab.setIcon(R.drawable.ic_primitivedot_106373);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.setIcon(R.drawable.ic_click);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        binding.viewPagerBoard.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                if (position == 2) {
                    binding.txtFinish.animate().alpha(1).setDuration(2000).start();
                    binding.txtFinish.setEnabled(true);
                } else {
                    binding.txtFinish.animate().alpha(0).setDuration(1000).start();
                    binding.txtFinish.setEnabled(false);
                }
            }
        });
    }

    private void initPager() {
        adapterBob = new PagerAdapterBob();
        binding.viewPagerBoard.setAdapter(adapterBob);
    }
}