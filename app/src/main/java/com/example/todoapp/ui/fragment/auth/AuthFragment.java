package com.example.todoapp.ui.fragment.auth;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.todoapp.R;
import com.example.todoapp.databinding.FragmentAuthBinding;
import com.example.todoapp.ui.activities.MainActivity;
import com.example.todoapp.utils.MyDialog;
import com.example.todoapp.utils.Prefs;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;


public class AuthFragment extends Fragment {
    private FragmentAuthBinding binding ;
    private GoogleSignInClient client ;
    private NavController controller ;
    private MyDialog myDialog ;
    private ActivityResultLauncher<Intent> resultLauncher ;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
     binding = FragmentAuthBinding.inflate(inflater,container,false);
        return binding.getRoot() ;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        requireActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        controller = Navigation.findNavController(view);
        myDialog = new MyDialog(requireActivity());
        ((MainActivity)requireActivity()).updateStatusBar("#A3D1F7");
        initAuth();
        initView();
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (FirebaseAuth.getInstance().getCurrentUser() == null) {
                    requireActivity().finish();
                }
            }
        });
        resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),result -> {
            if(result.getResultCode() == Activity.RESULT_OK){
                try {
                    Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(result.getData());
                    GoogleSignInAccount account = task.getResult(ApiException.class);
                    myDialog.show();
                    singIn(account);
                } catch (ApiException e) {
                    e.printStackTrace();
                }
            }
        });
        initDefault();
        initAnimate();
    }

    private void initAnimate() {
        binding.btnAuth.animate().translationY(0).setDuration(1500).start();
    }

    private void initDefault() {
        binding.btnAuth.setTranslationY(800);
    }


    private void initAuth() {
        GoogleSignInOptions gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("834958652124-ae0abhvit1pu0l80hvr4rbg24t29u7h7.apps.googleusercontent.com")
                .requestEmail()
                .build();
        client = GoogleSignIn.getClient(requireContext(),gso);

    }
    private void initView() {
        binding.btnAuth.setOnClickListener(v -> {
            singInWithGoogle();

        });
    }
    private void singInWithGoogle(){
        Intent intentClient = client.getSignInIntent();
        resultLauncher.launch(intentClient);
    }


    private void singIn(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(),null);
        FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener(task -> {
            myDialog.dismiss();
            if(task.isSuccessful()){
                controller.navigate(R.id.action_authFragment_to_navigation_home2);
            }else {
                Toast.makeText(requireContext(),"Вход не удался!",Toast.LENGTH_LONG).show();
            }
        });
    }

}
