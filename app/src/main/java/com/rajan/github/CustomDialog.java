package com.rajan.github;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class CustomDialog extends AppCompatDialogFragment {

    private EditText repoEditText, userEditText;
    private CustomDialogListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.custom_dialog,null);

        builder.setView(view)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String userName = userEditText.getText().toString();
                        String repoName = repoEditText.getText().toString();

                        listener.applyText(userName,repoName);
                    }
                });

        repoEditText = view.findViewById(R.id.repoNameEditText);
        userEditText = view.findViewById(R.id.userNameEditText);

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (CustomDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()+
                    "Must implement CustomDialogListener ");
        }
    }

    public interface CustomDialogListener{
        void applyText(String userName, String repoName);
    }
}
