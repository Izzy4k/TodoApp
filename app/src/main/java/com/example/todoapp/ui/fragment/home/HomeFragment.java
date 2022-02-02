package com.example.todoapp.ui.fragment.home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.todoapp.R;
import com.example.todoapp.App;
import com.example.todoapp.databinding.FragmentHomeBinding;
import com.example.todoapp.models.Task;
import com.example.todoapp.ui.activities.MainActivity;

import java.util.List;

public class HomeFragment extends Fragment implements Click {

    private FragmentHomeBinding binding;
    private MainAdapter adapter;
    private NavController controller;
    private final String title = "Вы точно хотите удалить эту Запись?";
    private final String yes = "Дыа";
    private final String not = "Нет";


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new MainAdapter(this);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ((MainActivity)requireActivity()).updateStatusBar("#FFFFFFFF");
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                requireActivity().finish();
            }
        });
        controller = Navigation.findNavController(view);
        binding.rvNote.setAdapter(adapter);
        initListener();
        initFragmentResultListener();

    }

    private void initFragmentResultListener() {
        List<Task> list = App.dataBase.taskDao().getAllTasks();
        adapter.setList(list);
    }


    private void initListener() {
        binding.btnAdd.setOnClickListener(v -> {
            controller.navigate(R.id.detailFragment);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void click(Task task) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("model", task);
        controller.navigate(R.id.detailFragment, bundle);
    }

    @Override
    public void delete(Task task) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        builder.setTitle(title);
        builder.setPositiveButton(yes, (dialog, which) -> {
                    App.dataBase.taskDao().delete(task);
                    initFragmentResultListener();
                }
        );
        builder.setNegativeButton(not, (dialog, which) -> {
            Toast.makeText(requireContext(), "Да пошел ты!", Toast.LENGTH_LONG).show();
        }).show();
    }
}