package com.example.todoapp.ui.fragment.profile;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.todoapp.App;
import com.example.todoapp.R;
import com.example.todoapp.databinding.FragmentProfileBinding;
import com.example.todoapp.utils.Prefs;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;


public class ProfileFragment extends Fragment {
    private FragmentProfileBinding binding;
    private Uri uri ;

    private ActivityResultLauncher<Intent> resultContracts;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initListener();
        initBtn();
        initText();
        initImageListener();
    }

    private void initImageListener() {
        if (!Prefs.getPrefs().getImage().equals("")) {
            Uri uri = Uri.parse(Prefs.getPrefs().getImage());
            Glide.with(binding.imageScreen).load(uri).circleCrop().into(binding.imageScreen);
        }
    }

    private void initText() {
        binding.txtFirstName.setText(Prefs.getPrefs().firstName());
        binding.txtLastName.setText(Prefs.getPrefs().lastName());
    }

    private void initBtn() {
        binding.btnSave.setOnClickListener(v -> {
            saveData();
            initText();
            clear();
            initImageListener();
        });
    }

    private void clear() {
        binding.editFirstName.setText("");
        binding.editLastName.setText("");
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(binding.getRoot().getWindowToken(), 0);
    }

    private void saveData() {
        String firstName = binding.editFirstName.getText().toString();
        String lastName = binding.editLastName.getText().toString();
        if (!firstName.equals("")) {
            Prefs.getPrefs().saveFirstName(firstName);
        }
        if (!lastName.equals("")) {
            Prefs.getPrefs().saveLastName(lastName);
        }
        if(uri != null){
            Prefs.getPrefs().saveImage(uri);
            Toast.makeText(requireContext(),"Вы успешно сохранились",Toast.LENGTH_LONG).show();
        }
    }

    private void initListener() {
        binding.imageScreen.setOnClickListener(v -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (requireActivity().checkSelfPermission(
                        Manifest.permission.READ_EXTERNAL_STORAGE) !=
                        PackageManager.PERMISSION_GRANTED) {
                    requireActivity().requestPermissions(new
                                    String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            1);
                } else {
                    getGallery();
                }
            }
        });
        resultContracts = registerForActivityResult(new
                        ActivityResultContracts.StartActivityForResult(),
                result -> {
                    Intent intent = result.getData();
                    if (intent != null) {
                         uri = intent.getData();
                        Glide.with(binding.imageScreen).load(uri).circleCrop().into(binding.imageScreen);
                    }
                });
    }


    private void getGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        resultContracts.launch(intent);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            getGallery();
        }
    }



}