package com.example.savor.settings.presenter.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import com.example.savor.R;
import com.example.savor.settings.presenter.SettingsFragmentContract;
import com.example.savor.settings.presenter.SettingsPresenterImp;


public class SettingsFragment extends Fragment implements SettingsFragmentContract {
    Button btnBackup;
    TextView txtUserName;
    SettingsPresenterImp settingsPresenterImp;

    //FireStore fireStore ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // fireStore=new FireStore(requireContext());
        settingsPresenterImp = new SettingsPresenterImp(requireContext(), this,requireActivity());
        txtUserName = view.findViewById(R.id.txtUseNameSettings);
        btnBackup = view.findViewById(R.id.btnBackup);
        settingsPresenterImp.getUserName();
        btnBackup.setOnClickListener(view1 -> {
            //settingsPresenterImp.logOut();
            showDialog();
        });
    }

    @Override
    public void showUseName(String name) {
        txtUserName.setText(name);
    }

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Attention")
                .setMessage("Are You Sure")
                .setPositiveButton("yes", (dialog, i) -> {
                    settingsPresenterImp.logOut();
                    NavController navController = Navigation.findNavController(requireView());
                    NavOptions navOptions= new NavOptions.Builder()
                            .setPopUpTo(R.id.settingsFragment,true)
                            .build();
                    navController.navigate(R.id.loginFragment,null,navOptions);
                    //Toast.makeText(requireContext(), "OK Logout", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                })
                .setNegativeButton("No", (dialogInterface, i) -> {
                    Toast.makeText(requireContext(), "Right", Toast.LENGTH_SHORT).show();
                })
                .setCancelable(true);
        builder.create().show();
    }
}
