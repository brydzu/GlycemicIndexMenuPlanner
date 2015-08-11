package com.naelteam.glycemicindexmenuplanner.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.naelteam.glycemicindexmenuplanner.R;
import com.naelteam.glycemicindexmenuplanner.model.Ingredient;
import com.naelteam.glycemicindexmenuplanner.model.Product;
import com.naelteam.glycemicindexmenuplanner.model.Unit;

/**
 *
 */
public class AddIngredientDialog extends android.support.v4.app.DialogFragment {

    private final static String TAG = AddIngredientDialog.class.getSimpleName();

    private final static String INGREDIENT_KEY = "INGREDIENT";

    private Ingredient ingredient;
    private Listener listener;

    public static AddIngredientDialog newInstance(Ingredient ingredient) {
        AddIngredientDialog f = new AddIngredientDialog();
        Bundle bundle = new Bundle();
        bundle.putParcelable(INGREDIENT_KEY, ingredient);
        f.setArguments(bundle);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate - ");

        Bundle arguments = getArguments();
        if (arguments!=null){
            Ingredient ingredient = arguments.getParcelable(INGREDIENT_KEY);
            Log.d(TAG, "onCreate - ingredient = " + ingredient);

            if (ingredient != null){
                this.ingredient = ingredient;
            }
        }

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Log.d(TAG, "onCreateDialog - ");

        return new AlertDialog.Builder(getActivity())
                .setTitle(R.string.add_ingredient_title)
                .setCancelable(false)
                .setView(R.layout.dialog_add_ingredient)
                .setPositiveButton(R.string.ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                if (ingredient == null) {
                                    ingredient = new Ingredient();
                                }
                                ingredient.setName(((EditText) getDialog().findViewById(R.id.add_ingredient_name)).getText().toString());
                                ingredient.setAmount(((EditText) getDialog().findViewById(R.id.add_ingredient_amount)).getText().toString());
                                String unit = ((Spinner) getDialog().findViewById(R.id.add_ingredient_unit)).getSelectedItem().toString();
                                ingredient.setUnit(Unit.fromValue(unit.substring(unit.indexOf("(")+1, unit.length()-1)));
                                ingredient.setNotes(((EditText) getDialog().findViewById(R.id.add_ingredient_notes)).getText().toString());

                                AddIngredientDialog.this.listener.onOk(ingredient);
                            }
                        }
                )
                .setNegativeButton(R.string.cancel,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dialog.dismiss();
                                AddIngredientDialog.this.listener.onCancel();
                            }
                        }
                )
                .create();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Log.d(TAG, "onActivityCreated - ");
    }

    public void setListener(Listener listener){
        this.listener = listener;
    }

    public interface Listener{
        public void onOk(Ingredient ingredient);
        public void onCancel();
    }
}
