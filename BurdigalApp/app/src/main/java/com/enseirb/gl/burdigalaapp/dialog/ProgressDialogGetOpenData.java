package com.enseirb.gl.burdigalaapp.dialog;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by rchabot on 29/10/15.
 */
public class ProgressDialogGetOpenData extends ProgressDialog {
    private String dataType;

    public ProgressDialogGetOpenData(Context context, String dataType) {
        super(context);
        this.dataType = dataType;
    }

    public void showProgressDialog() {
        setTitle("Récupération des données ");
        setMessage("Récupération des données en cours");
        setCancelable(false);
        show();
    }
}
