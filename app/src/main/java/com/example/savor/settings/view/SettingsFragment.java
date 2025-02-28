package com.example.savor.settings.view;

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
        View dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.custom_dialog, null);

        TextView dialogTitle = dialogView.findViewById(R.id.dialog_title);
        TextView dialogMessage = dialogView.findViewById(R.id.dialog_message);
        Button positiveButton = dialogView.findViewById(R.id.dialog_positive_button);
        Button negativeButton = dialogView.findViewById(R.id.dialog_negative_button);

        dialogTitle.setText(R.string.warning);
        dialogMessage.setText(R.string.are_you_sure);
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setView(dialogView);
        AlertDialog dialog = builder.create();

        positiveButton.setOnClickListener(v -> {
            settingsPresenterImp.logOut();
            NavController navController = Navigation.findNavController(requireView());
            NavOptions navOptions= new NavOptions.Builder()
                    .setPopUpTo(R.id.settingsFragment,true)
                    .build();
            navController.navigate(R.id.loginFragment,null,navOptions);
            dialog.dismiss();
        });

        negativeButton.setOnClickListener(v -> {
            dialog.dismiss();
        });

        dialog.show();
    }


}
