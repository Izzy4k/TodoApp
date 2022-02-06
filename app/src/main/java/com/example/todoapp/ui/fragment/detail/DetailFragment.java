package com.example.todoapp.ui.fragment.detail;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.todoapp.App;
import com.example.todoapp.R;
import com.example.todoapp.databinding.FragmentDetailBinding;
import com.example.todoapp.models.Aboba;
import com.example.todoapp.utils.MyDialog;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.datepicker.SingleDateSelector;
import com.google.firebase.firestore.FirebaseFirestore;

public class DetailFragment extends Fragment {
    private FragmentDetailBinding binding;
    private NavController controller;
    public static boolean isSaveFire = false;
    private FirebaseFirestore db;
    private MyDialog myDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDetailBinding.inflate(inflater, container, false);
        db = FirebaseFirestore.getInstance();
        myDialog = new MyDialog(requireActivity());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initListener();
        initArgumentsListener();

        initListenerFire();
        initArgumentsFireListener();

    }

    private void initArgumentsFireListener() {
        if (isSaveFire) {
            if (getArguments() != null) {
                Aboba aboba = (Aboba) getArguments().getSerializable("model");
                binding.btnSave.setText("Edit");
                binding.etTxt.setText(aboba.getTitle());
                binding.btnSave.setOnClickListener(v -> {
                    String title = binding.etTxt.getText().toString().trim();
                    if (!title.equals("")) {
                        aboba.setTitle(title);
//                        Toast.makeText(requireContext(), aboba.getIdFire(), Toast.LENGTH_LONG).show();
                        myDialog.show();
                        db.collection("users").document(aboba.getIdFire()).set(aboba).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                myDialog.dismiss();
                                closeFragment(R.id.action_detailFragment_to_navigation_dashboard);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(requireContext(), "Пошел ты !", Toast.LENGTH_SHORT).show();
                                myDialog.dismiss();
                            }
                        });
                    }

                });
            }
        }
    }

    private void initListenerFire() {
        if (isSaveFire) {
            binding.btnSave.setText("Save");
            binding.btnSave.setOnClickListener(v -> {
                String result = binding.etTxt.getText().toString().trim();
                if (!result.equals("")) {
                    saveData(result);
                    myDialog.show();
                }

            });
        }
    }

    private void saveData(String result) {
        Aboba aboba = new Aboba(result);
        db.collection("users").add(aboba)
                .addOnSuccessListener(documentReference -> {
                    closeFragment(R.id.action_detailFragment_to_navigation_dashboard);
//                    aboba.setIdFire(documentReference.getId());
                    myDialog.dismiss();
                })
                .addOnFailureListener(e -> {
                    myDialog.dismiss();
                    Toast.makeText(requireContext(),
                            "Не фартануло ", Toast.LENGTH_LONG).show();
                });

    }

    private void initArgumentsListener() {
        if (!isSaveFire) {
            if (getArguments() != null) {
                Aboba aboba = (Aboba) getArguments().getSerializable("model");
                binding.etTxt.setText(aboba.getTitle());
                binding.btnSave.setText("Edit");
                binding.btnSave.setOnClickListener(v -> {
                    String result = binding.etTxt.getText().toString().trim();
                    if (!result.equals("")) {
                        aboba.setTitle(result);
                        App.dataBase.taskDao().update(aboba);
                        closeFragment(R.id.action_detailFragment_to_navigation_home);
                    } else {
                        Toast.makeText(requireContext(), "Пусто", Toast.LENGTH_LONG).show();
                    }
                });
            }
        }
    }

    private void initListener() {
        if (!isSaveFire) {
            binding.btnSave.setText("Save");
            binding.btnSave.setOnClickListener(v -> {
                String result = binding.etTxt.getText().toString().trim();
                if (!result.equals("")) {
                    closeFragment(R.id.action_detailFragment_to_navigation_home);
                    saveTask(result);
                } else {
                    Toast.makeText(requireContext(), "Пусто", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    private void saveTask(String result) {
        Aboba aboba = new Aboba(result);
        App.dataBase.taskDao().addTask(aboba);
    }

    private void closeFragment(int id) {
        controller = Navigation.findNavController(requireActivity(),
                R.id.nav_host_fragment_activity_main);
        controller.navigate(id);
    }
}