package com.example.todoapp;

import android.os.Bundle;
import android.view.View;

import com.example.todoapp.app.App;
import com.example.todoapp.database.sharedpreferences.Prefs;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.todoapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private NavController controller ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        controller = Navigation.findNavController(this,R.id.nav_host_fragment_activity_main);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications , R.id.navigation_profile)
                .build();
        NavController navController = Navigation.findNavController(this,
                R.id.nav_host_fragment_activity_main);
//        NavigationUI.setupActionBarWithNavController(this, navController,
//                appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
                if(!Prefs.getPrefs().isBoardShow()){
                    navController.navigate(R.id.boardFragment);
                    Prefs.getPrefs().saveState();
                } 


        controller.addOnDestinationChangedListener((controller, destination, arguments) -> {
            if (destination.getId() == R.id.boardFragment) {
                binding.navView.setVisibility(View.GONE);
            } else {
                binding.navView.setVisibility(View.VISIBLE);
            }
        });
    }

}