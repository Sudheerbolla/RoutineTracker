package com.routinetracker.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.routinetracker.R;

public class DialogUtils {

    private static AlertDialog alert;

    public static void showDropDownListStrings(String title, Context context, final TextView textView, final String[] categoryNames,
                                               final View.OnClickListener clickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setItems(categoryNames, (dialog, item) -> {
            alert.dismiss();
            if (textView != null) {
                textView.setText(categoryNames[item]);
                textView.setTag(categoryNames[item]);
            }
            if (clickListener != null) {
                if (textView != null)
                    clickListener.onClick(textView);
            }
        });
        alert = builder.create();
        alert.show();
    }

    public static void showDropDownListStrings(Context context, final String[] categoryNames, final View view,
                                               String heading, final View.OnClickListener clickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        if (TextUtils.isEmpty(heading))
            builder.setTitle(R.string.app_name);
        else builder.setTitle(heading);
        builder.setItems(categoryNames, (dialog, item) -> {
            alert.dismiss();
            if (view != null) view.setTag(categoryNames[item]);
            if (clickListener != null) {
                clickListener.onClick(view);
            }
        });
        alert = builder.create();
        alert.setCancelable(true);
        alert.setCanceledOnTouchOutside(true);
        alert.show();
    }

    public static void showDropDownListBT(Context context, final String[] categoryNames, final View view,
                                          String heading, final View.OnClickListener clickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        if (TextUtils.isEmpty(heading))
            builder.setTitle(R.string.app_name);
        else builder.setTitle(heading);
        builder.setItems(categoryNames, (dialog, item) -> {
            alert.dismiss();
            if (view != null) view.setTag(categoryNames[item]);
            if (clickListener != null) {
                clickListener.onClick(view);
            }
        });
        alert = builder.create();
        alert.setCancelable(true);
        alert.setCanceledOnTouchOutside(true);
        alert.show();
    }

    public static void showTwoButtonDialog(Context context, final String message, String pMessage, String nMessage, final DialogInterface.OnClickListener clickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message);
        builder.setTitle(context.getString(R.string.app_name));
        builder.setCancelable(false);
        builder.setPositiveButton(pMessage, (dialog, which) -> {
            clickListener.onClick(dialog, which);
            dialog.dismiss();
        });
        builder.setNeutralButton(nMessage, (dialog, which) -> dialog.dismiss());
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

}
