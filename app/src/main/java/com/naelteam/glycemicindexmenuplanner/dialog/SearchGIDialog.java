package com.naelteam.glycemicindexmenuplanner.dialog;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.naelteam.glycemicindexmenuplanner.R;

public class SearchGIDialog {
    private static final String TAG = SearchGIDialog.class.getSimpleName();

    public AlertDialog createAlertDialog(final Activity activity, final DialogListener dialogListener){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.AppCompatAlertDialogStyle);
        final LayoutInflater inflater = activity.getLayoutInflater();
        final View view = inflater.inflate(R.layout.dialog_gi_search, null);
        final TextInputLayout textInputLayout = (TextInputLayout) view.findViewById(R.id.gi_search_textinputlayout);
        textInputLayout.setErrorEnabled(true);
        final TextView giSearchTextView = (TextView) view.findViewById(R.id.gi_search);

        builder.setView(view)
            .setPositiveButton(R.string.search, null)
            .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialogListener.onNegativeClick(dialog);
                }
            });

        final AlertDialog alertDialog = builder.create();

        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {

                Button b = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final String searchTxt = giSearchTextView.getText().toString();
                        Log.d(TAG, "onClick - searchTxt = " + searchTxt);
                        if (searchTxt != null && (!searchTxt.isEmpty())) {
                            dialogListener.onPositiveClick(alertDialog, searchTxt);
                            alertDialog.dismiss();
                        } else {
                            textInputLayout.setError(activity.getString(R.string.error_search_gi_message));
                        }
                    }
                });
            }
        });
        return alertDialog;
    }
}
