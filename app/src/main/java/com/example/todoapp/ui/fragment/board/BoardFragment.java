package com.example.todoapp.ui.fragment.board;

import android.os.Bundle;

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
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;


public class BoardFragment extends Fragment {
    private FragmentBoardBinding binding;
    private PagerAdapterBob adapterBob;
    private NavController controller;

    public BoardFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentBoardBinding.inflate(inflater);


        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initPager();
        initListener();
        initBtn();
    }

    private void initBtn() {
        binding.txtFinish.setOnClickListener(v -> {
            navigateFragment();
        });
        binding.txtSkip.setOnClickListener(v -> {
        binding.viewPagerBoard.setCurrentItem(2);
        });
    }



    private void navigateFragment() {
        controller = Navigation.findNavController(requireActivity(),R.id.nav_host_fragment_activity_main);
        controller.navigate(R.id.navigation_home);
    }

    private void initListener() {
        new TabLayoutMediator(binding.tabBoard, binding.viewPagerBoard, (tab, position) -> {
            if(position == 0){
                tab.setIcon(R.drawable.ic_click);
            }else {
                tab.setIcon(R.drawable.ic_primitivedot_106373);
            }

        }).attach();
        binding.tabBoard.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tab.setIcon(R.drawable.ic_click);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.setIcon(R.drawable.ic_primitivedot_106373);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
            binding.viewPagerBoard.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                @Override
                public void onPageSelected(int position) {
                    super.onPageSelected(position);
                    if(position == 2){
                        binding.txtFinish.setVisibility(View.VISIBLE);
                    }else {
                        binding.txtFinish.setVisibility(View.GONE);
                    }
                }
            });
    }

    private void initPager() {
        adapterBob = new PagerAdapterBob();
        binding.viewPagerBoard.setAdapter(adapterBob);
    }
}