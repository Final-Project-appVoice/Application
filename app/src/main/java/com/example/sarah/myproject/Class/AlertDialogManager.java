package com.example.sarah.myproject.Class;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.example.sarah.myproject.R;

/**
 * Created by Sarah on 07-Jan-15.
 */
public class AlertDialogManager
{
    /**
     * Function to display simple Alert Dialog
     * @param context - application context
     * @param title - alert dialog title
     * @param message - alert message
     * @param status - success/failure (used to set icon)
     *               - pass null if you don't want icon
     * */
    public void showAlertDialog(Context context, String title, String message, Boolean status)
    {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();

        // Setting Dialog Title
        alertDialog.setTitle(title);

        // Setting Dialog Message
        alertDialog.setMessage(message);

        if(status == true)
            // Setting alert dialog icon
            alertDialog.setIcon(R.drawable.ic_error);

        // Setting OK Button
        alertDialog.setButton(alertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }
}

