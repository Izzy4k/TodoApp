package com.example.todoapp.ui.activities;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.todoapp.R;
import com.example.todoapp.utils.Prefs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.todoapp.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private NavController controller;
    private FirebaseUser user;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        controller = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        if (!Prefs.getPrefs().isBoardShow()) {
            controller.navigate(R.id.boardFragment);

        } else if (user == null) {
            controller.navigate(R.id.authFragment);
        }
        NavController navController = Navigation.findNavController(this,
                R.id.nav_host_fragment_activity_main);
        NavigationUI.setupWithNavController(binding.navView, navController);


        controller.addOnDestinationChangedListener((controller, destination, arguments) -> {
            switch (destination.getId()) {
                case R.id.boardFragment:
                    binding.navView.setVisibility(View.GONE);
                    break;
                case R.id.authFragment:
                    binding.navView.setVisibility(View.GONE);
                    break;
                default:
                    binding.navView.setVisibility(View.VISIBLE);
            }

        });
    }

    //Запомнить зяабал , возвращает в исходный фрагмент ,вверхний бар
//    @Override
//    public boolean onSupportNavigateUp() {
//        return NavigationUI.navigateUp(controller,appBarConfiguration ) || super.onSupportNavigateUp();
//
//    }
    public void updateStatusBar(String color){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor(color));
        }
    }

}