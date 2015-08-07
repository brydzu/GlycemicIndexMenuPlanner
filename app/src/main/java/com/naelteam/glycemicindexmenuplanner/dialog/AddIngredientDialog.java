package com.naelteam.glycemicindexmenuplanner.dialog;

import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.naelteam.glycemicindexmenuplanner.R;

/**
 * Created by fg on 07/08/15.
 */
public class AddIngredientDialog extends DialogFragment {

    private final static String TAG = AddIngredientDialog.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate - ");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView - ");

        View v = inflater.inflate(R.layout.dialog_add_ingredient, container, false);

        return v;
    }

}
