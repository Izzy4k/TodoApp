package com.example.todoapp.ui.fragment.dashboard;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.todoapp.R;
import com.example.todoapp.databinding.FragmentDashboardBinding;
import com.example.todoapp.models.Aboba;
import com.example.todoapp.ui.fragment.detail.DetailFragment;
import com.example.todoapp.ui.fragment.home.Click;
import com.example.todoapp.ui.fragment.home.MainAdapter;
import com.example.todoapp.utils.MyDialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class DashboardFragment extends Fragment implements Click {

    private FragmentDashboardBinding binding;
    private MainAdapter adapter;
    private NavController controller;
    private FirebaseFirestore db;
    private MyDialog myDialog;
    private List<Aboba> list;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        db = FirebaseFirestore.getInstance();
        myDialog = new MyDialog(requireActivity());
        adapter = new MainAdapter(this);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        controller = Navigation.findNavController(view);
        initListener();
        myDialog.show();
        initListenerData();
    }

    private void initListenerData() {
        if (list == null) {
            list = new ArrayList<>();
        }
        db.collection("users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                List<Aboba> list = task.getResult().toObjects(Aboba.class);
//                adapter.setList(list);
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot shot : task.getResult()) {
                        Aboba aboba = shot.toObject(Aboba.class);
                        aboba.setIdFire(shot.getId());
                        list.add(aboba);
                    }
                }
//                List<Aboba> list = task.getResult().toObjects(Aboba.class);
//                adapter.setList(list);
                myDialog.dismiss();
                adapter.setList(list);


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(requireContext(),
                        "Не фартануло ", Toast.LENGTH_LONG).show();
                myDialog.dismiss();

            }
        });

    }

    private void initListener() {
        binding.rvNoteDash.setAdapter(adapter);
        binding.btnAddDash.setOnClickListener(v -> {
            controller.navigate(R.id.detailFragment);
            DetailFragment.isSaveFire = true;
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void click(Aboba aboba) {

        navigate(aboba);
    }

    @Override
    public void delete(Aboba aboba) {

    }

    private void navigate(Aboba aboba) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("model", aboba);
        controller.navigate(R.id.detailFragment, bundle);
        DetailFragment.isSaveFire = true;
    }
}