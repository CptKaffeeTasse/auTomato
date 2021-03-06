package com.example.automato;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatDialogFragment;



public class ExampleDialog extends AppCompatDialogFragment {
    private EditText editTextPlantName;
    private ExampleDialogListener listener;
    private int position;

    public ExampleDialog(int position){
        this.position=position;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog_insert, null);

        builder.setView(view)
                .setTitle("New plant")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String plantname = editTextPlantName.getText().toString();
                        //MainActivity.plantName = plantname;
                        listener.applyText(plantname, position);
                    }
                });

        editTextPlantName = view.findViewById(R.id.plantName);
        return builder.create();
    }


    @Override
    public void onAttach( Context context) {
        super.onAttach(context);

        try {
            listener = (ExampleDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement ExampleDialogListener");
        }
    }


    public interface ExampleDialogListener {
        void applyText(String plantname, int position);
    }

}
